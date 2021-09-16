package com.example.contact.Db

import com.example.contact.Models.User

interface DbServiceInterface {

    fun addUser(user:User)
    fun deleteUser(user: User)
    fun editUser(user: User):Int
    fun getAllUser():List<User>
}