package com.example.contact.Models

class User {
    var id:Int? = null
    var name:String? = null
    var number:String? = null

    constructor(name: String?, number: String?){
        this.name = name
        this.number = number
    }

    constructor()
    constructor(id: Int?, name: String?,number: String?){
        this.id = id
        this.name = name
        this.number = number
    }

    override fun toString(): String{
        return "User(id=$id, name=$name, number=$number)"
    }
}