package com.example.passlin

import android.Manifest
import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

class LoginActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")

    private var fpm: FingerprintManager? = null
    private var kgm: KeyguardManager? = null
    private var keyStore: KeyStore? = null
    private var keyGenerator: KeyGenerator? = null
    private val KEY_NAME = "example_key"
    private var cipher: Cipher? = null
    private var cryptoObject: FingerprintManager.CryptoObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val prefs = PreferenceManager.getDefaultSharedPreferences(baseContext)
        val previouslyStarted = prefs.getBoolean("previouslyStarted", false)
        if (!previouslyStarted){
            val edit = prefs.edit()
            edit.putBoolean("previouslyStarted", true)
            edit.apply()
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }


        kgm = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        fpm = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager


        if (!kgm!!.isKeyguardSecure) {
            Toast.makeText(this,
                "Lock screen security not enabled in Settings",
                Toast.LENGTH_LONG).show()
            return
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,
                "Fingerprint authentication permission not enabled",
                Toast.LENGTH_LONG).show()

            return
        }
        if (!fpm!!.hasEnrolledFingerprints()) {

            // This happens when no fingerprints are registered.
            Toast.makeText(this,
                "Register at least one fingerprint in Settings",
                Toast.LENGTH_LONG).show()
            return
        }

        generateKey()

        if (cipherInit()) {
            cryptoObject = FingerprintManager.CryptoObject(cipher!!)
            val helper = FingerPrintHandler(this)
            helper.startAuth(fpm!!, cryptoObject!!)
        }



        val button = findViewById<Button>(R.id.goButton)
        val greeting = findViewById<TextView>(R.id.greeting)
        val passwordET = findViewById<EditText>(R.id.password)
        val fPassword = findViewById<TextView>(R.id.fPassword)

        val user = UserLocalStore(this).returnUser()
        val userName = user.name
        val userPassword = user.password

        greeting.text = "Hello, $userName"

        button.setOnClickListener {
            val password = passwordET.text.toString()

            if(password == userPassword){
                logUserIn(user)
            }
            if(password == ""){
                Toast.makeText(this,"Password can't be empty", Toast.LENGTH_SHORT).show()
            }
            if (password != userPassword){
                Toast.makeText(this,"Wrong password, try again", Toast.LENGTH_SHORT).show()
            }
        }

        fPassword.setOnClickListener {
            startActivity(Intent(this, QuestionAndAnswerActivity::class.java))
        }
    }

    override fun onResume() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(baseContext)
        val previouslyStarted = prefs.getBoolean("previouslyStarted", false)
        if (!previouslyStarted){
            val edit = prefs.edit()
            edit.putBoolean("previouslyStarted", true)
            edit.apply()
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
        super.onResume()
    }


    private fun logUserIn(user: User) {
        UserLocalStore(this).storeUserData(user)
        UserLocalStore(this).setUserLoggedIn(true)
        startActivity(Intent(this, CardsActivity::class.java))
    }


    protected fun generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                "AndroidKeyStore")
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(
                "Failed to get KeyGenerator instance", e)
        } catch (e: NoSuchProviderException) {
            throw RuntimeException("Failed to get KeyGenerator instance", e)
        }
        try {
            keyStore!!.load(
                null)
            keyGenerator!!.init(
                KeyGenParameterSpec.Builder(KEY_NAME,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setUserAuthenticationRequired(true)
                .setEncryptionPaddings(
                    KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .build())
            keyGenerator!!.generateKey()
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidAlgorithmParameterException) {
            throw RuntimeException(e)
        } catch (e: CertificateException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun cipherInit(): Boolean {
        try {
            cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }
        try {
            keyStore!!.load(null)
            val key = keyStore!!.getKey(KEY_NAME, null) as SecretKey
            cipher!!.init(Cipher.ENCRYPT_MODE, key)
            return true
        } catch (e: KeyPermanentlyInvalidatedException) {
            return false
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }
    }
}
