package com.example.contact

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.contact.Adapters.UserAdapter
import com.example.contact.Db.MyDbHelper
import com.example.contact.Models.User
import com.example.contact.databinding.ActivityMainBinding
import com.example.contact.databinding.ItemDialogBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var userAdapter: UserAdapter
    lateinit var myDbHelper: MyDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDbHelper = MyDbHelper(this)

        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString()
            val number = binding.edtNumber.text.toString()

            binding.edtName.text.clear()
            binding.edtNumber.text.clear()

            val user = User(name, number)
            myDbHelper.addUser(user)
            Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()
            onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        val list = ArrayList<User>()
        list.addAll(myDbHelper.getAllUser())

        userAdapter = UserAdapter(list, object : UserAdapter.RvClick{
            override fun delete(user: User, position:Int) {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle("${user.name} ni o'chirishga rozimisiz?")
                dialog.setPositiveButton("Ha", object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        myDbHelper.deleteUser(user)
                        Toast.makeText(this@MainActivity, "Delete", Toast.LENGTH_SHORT).show()
                        list.removeAt(position)
                        userAdapter.notifyItemRemoved(position)
                        userAdapter.notifyItemChanged(position, list.size)
                    }
                })
                dialog.setNegativeButton("Yo'q", object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {

                    }
                })
                dialog.show()
            }

            override fun edit(user: User, position: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity).create()
                val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
                dialog.setView(itemDialogBinding.root)

                itemDialogBinding.edtNameDialog.setText(user.name)
                itemDialogBinding.edtNumberDialog.setText(user.number)
                itemDialogBinding.btnSaveDialog.setOnClickListener {
                    user.name = itemDialogBinding.edtNameDialog.text.toString()
                    user.number = itemDialogBinding.edtNumberDialog.text.toString()

                    myDbHelper.editUser(user)
                    list[position] = user
                    Toast.makeText(this@MainActivity, "Edited", Toast.LENGTH_SHORT).show()
                    userAdapter.notifyItemChanged(position)
                    dialog.hide()
                }

                dialog.show()
            }
        })
        binding.rv.adapter = userAdapter
    }
}