package com.dwinovirhmwt.mobiledeveloperinternsuitmedia.thirdscreen

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.databinding.ItemUserBinding
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.network.User

class UserAdapter(private val onItemClick: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val userList: MutableList<User> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(users: List<User>) {
        userList.addAll(users)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        userList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            Log.d("UserAdapter", "Item clicked: ${user.firstName} ${user.lastName}")
            onItemClick(user)
        }
    }


    override fun getItemCount(): Int {
        val itemCount = userList.size
        Log.d("UserAdapter", "ItemCount: $itemCount")
        return itemCount
    }


    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            binding.apply {
                nameUser.text = "${user.firstName} ${user.lastName}"
                emailUser.text = user.email
                Glide.with(root.context)
                    .load(user.avatar)
                    .circleCrop()
                    .into(ivUser)
            }
        }
    }
}
