package com.example.contact.Db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.contact.Models.User

class MyDbHelper(context: Context) :
    SQLiteOpenHelper(context, Constant.DB_NAME, null, Constant.DB_VERSION), DbServiceInterface {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "create table ${Constant.TABLE_NAME} (${Constant.ID} integer not null primary key autoincrement unique, ${Constant.NAME} text not null, ${Constant.NUMBER} text not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    override fun addUser(user: User) {
        val dataBase = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(Constant.NAME, user.name)
        contentValue.put(Constant.NUMBER, user.number)
        dataBase.insert(Constant.TABLE_NAME, null, contentValue)
        dataBase.close()
    }

    override fun deleteUser(user: User) {
        val database = this.writableDatabase
        database.delete(Constant.TABLE_NAME, "${Constant.ID} =?", arrayOf(user.id.toString()))
        database.close()
    }

    override fun editUser(user: User): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.ID, user.id)
        contentValues.put(Constant.NAME, user.name)
        contentValues.put(Constant.NUMBER, user.number)

        return database.update(
            Constant.TABLE_NAME,
            contentValues,
            "${Constant.ID} = ?",
            arrayOf(user.id.toString())
        )
    }

    override fun getAllUser(): List<User> {
        val list = ArrayList<User>()
        val query = "select * from ${Constant.TABLE_NAME}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst())
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val number = cursor.getString(2)

                val user = User(id, name, number)
                list.add(user)

            } while (cursor.moveToNext())
        return list
    }
}