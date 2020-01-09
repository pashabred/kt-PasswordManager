package com.example.passlin

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import java.text.SimpleDateFormat
import java.util.*


class RVAdapter (var dataList: MutableList<Model>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView? = itemView.findViewById(R.id.title)
        var icon: ImageView = itemView.findViewById(R.id.icon)

        init {

            itemView.setOnClickListener {

                var position: Int = adapterPosition
                val password = dataList[position].password
                val clipboard =
                    itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                val clip = ClipData.newPlainText("password", password)
                clipboard!!.setPrimaryClip(clip)
                Toast.makeText(itemView.context,"Copied to clipboard",Toast.LENGTH_SHORT).show()
            }


            itemView.setOnLongClickListener {

                var position: Int = adapterPosition
                val dialog = AlertDialog.Builder(itemView.context)

                dialog.setTitle("Select action")
                dialog.setPositiveButton("Delete", DialogInterface.OnClickListener { dialog, which ->

                    val helper = DBHelper(itemView.context)
                    helper.deleteARow(title?.text.toString(),itemView.context)
                    dataList.removeAt(position)
                    notifyDataSetChanged()

                })
                dialog.setNegativeButton("Open",DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(itemView.context,"WOW", Toast.LENGTH_LONG).show()

//                    val inflater = LayoutInflater.from(itemView.context)
//                    val view: View = inflater.inflate(R.layout.info_dialog, null)
//                    val dialog: AlertDialog = AlertDialog.Builder(itemView.context)
//                        .setTitle(dataList[position].title)
//                        .setView(view)
//                        .setPositiveButton("Ok", null)
//                        .setNegativeButton("Cancel", null)
//                        .show()
//
//                    val titleET: EditText? = view.findViewById(R.id.added_title)
//                    titleET?.setText(dataList[position].title)
//                        //val is used to delete it later
//                    val lastTitle = dataList[position].title
//
//                    val loginET: EditText? = view.findViewById(R.id.added_login)
//                    loginET?.setText(dataList[position].login)
//
//                    val passwordET: EditText? = view.findViewById(R.id.added_password)
//                    passwordET?.setText(dataList[position].password)
//
//                    val noteET: EditText? = view.findViewById(R.id.added_note)
//                    noteET?.setText(dataList[position].note)
//
//
//                    val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
//                    positiveButton.setOnClickListener {
//
//                        val newTitle = titleET?.text.toString()
//                        val newLogin = loginET?.text.toString()
//                        val newPassword = passwordET?.text.toString()
//                        val newNote = noteET?.text.toString()
//                        val newDate = Date()
//                        val formatter = SimpleDateFormat("dd-MM-yyyy")
//                        val dateTime = formatter.format(newDate)
//
//                        if (newTitle == "" || newPassword == "" || newLogin == ""){
//                            Toast.makeText(itemView.context,"Please fill all the fields",Toast.LENGTH_SHORT).show()
//                        }
//                        else {
//                            //instead of updating a row we delete and insert a new one
//                            //will fix it later
//                            val helper = DBHelper(itemView.context)
//                            helper.deleteARow(lastTitle, itemView.context)
//                            helper.insertIntoDB(newTitle, newLogin, newPassword, newNote, dateTime, itemView.context)
//                            dataList.clear()
//                            dataList.addAll(helper.getDataFromDB(itemView.context))
//                            notifyDataSetChanged()
//                            dialog.dismiss()
//                        }
//                    }
//
//
                })

                dialog.show()
                true
                }
        }

    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    //The onBindViewHolder method connects the data to the view holder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title?.text = dataList[position].title
        holder.icon.setImageDrawable(TextDrawable.builder().buildRound(dataList[position].title[0].toString(),Color.BLUE))
    }


    //The onCreateViewHolder method is similar to the onCreate method. It inflates the item layout, and returns a view holder with the layout and the adapter.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(v)
    }


}