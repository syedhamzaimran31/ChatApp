package com.example.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.databinding.ChatsRowBinding;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.usersViewHolder>{
    Context context;
    ArrayList<myUser> users;

    public UsersAdapter(Context context, ArrayList<myUser> users) {
        this.context = context;
        this.users = users;
    }





    @NonNull
    @Override
    public usersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.chats_row, parent, false);
        return new usersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull usersViewHolder holder, int position) {
            myUser user=users.get(position);
            holder.binding.chatName.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class usersViewHolder extends RecyclerView.ViewHolder{
            ChatsRowBinding binding;
        public usersViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ChatsRowBinding.bind(itemView);
        }
    }
}
