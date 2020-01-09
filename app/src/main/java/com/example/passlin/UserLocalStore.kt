package com.example.passlin

import android.content.SharedPreferences
import android.content.Context


/**
 * Created by pashabred on 27/02/17.
 */

class UserLocalStore(context: Context) {

    internal var userLocalDatabase: SharedPreferences

    init {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0)
    }


    fun storePasswordLen(length: Int) {
        val userLocalDatabaseEditor = userLocalDatabase.edit()
        userLocalDatabaseEditor.putString("length", length.toString())
        userLocalDatabaseEditor.apply()
    }

    fun returnPasswordLen(): Int {
        return userLocalDatabase.getString("length","8")!!.toInt()
    }

    fun storeUserData(user: User) {
        val userLocalDatabaseEditor = userLocalDatabase.edit()
        userLocalDatabaseEditor.putString("name", user.name)
        userLocalDatabaseEditor.putString("password", user.password)
        userLocalDatabaseEditor.apply()
    }

    fun setUserLoggedIn(loggedIn: Boolean) {
        val userLocalDatabaseEditor = userLocalDatabase.edit()
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn)
        userLocalDatabaseEditor.apply()
    }

    fun clearUserData() {
        val userLocalDatabaseEditor = userLocalDatabase.edit()
        userLocalDatabaseEditor.clear()
        userLocalDatabaseEditor.apply()
    }



    fun returnUser(): User {

        val user = User(userLocalDatabase.getString("name", "").toString(), userLocalDatabase.getString("password", "").toString())
        return user
    }

    fun storeQuestion(question:String) {
        val userLocalDatabaseEditor = userLocalDatabase.edit()
        userLocalDatabaseEditor.putString("question", question)
        userLocalDatabaseEditor.apply()
    }

    fun storeAnswer(answer: String) {
        val userLocalDatabaseEditor = userLocalDatabase.edit()
        userLocalDatabaseEditor.putString("answer", answer)
        userLocalDatabaseEditor.apply()
    }

    fun returnQ():String? {
        return userLocalDatabase.getString("question","")
    }
    fun returnA():String? {
        return userLocalDatabase.getString("answer","")
    }

    companion object {
        val SP_NAME = "userDetails"

    }
}