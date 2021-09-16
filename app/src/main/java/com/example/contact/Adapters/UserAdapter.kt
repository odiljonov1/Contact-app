package com.example.contact.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.Models.User
import com.example.contact.databinding.ItemRvBinding

class UserAdapter(val list: List<User>, var rvClick: RvClick):RecyclerView.Adapter<UserAdapter.Vh>() {

    inner class Vh(var itemRv: ItemRvBinding):RecyclerView.ViewHolder(itemRv.root){
        fun onBind(user: User, position: Int){
            itemRv.txtName.text = user.name
            itemRv.txtNumber.text = user.number
            itemRv.root.setOnLongClickListener {
                rvClick.delete(user, position)
                true
            }
            itemRv.root.setOnClickListener {
                rvClick.edit(user, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface RvClick{
        fun delete(user: User, position: Int)
        fun edit(user: User, position: Int)
    }
}