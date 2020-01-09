package com.example.passlin

import android.provider.BaseColumns

object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "mytable"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_LOGIN = "login"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_NAME_NOTE = "note"
        const val COLUMN_NAME_DATE_TIME = "datetime"
    }
}