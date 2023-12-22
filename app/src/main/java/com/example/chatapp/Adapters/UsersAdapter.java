package com.example.chatapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.Activities.chatActivity;
import com.example.chatapp.Models.myUser;
import com.example.chatapp.R;
import com.example.chatapp.databinding.ChatsRowBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.usersViewHolder> {
    Context context;
    ArrayList<myUser> users;

    public UsersAdapter(Context context, ArrayList<myUser> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public usersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chats_row, parent, false);
        return new usersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull usersViewHolder holder, int position) {
        myUser user = users.get(position);

        String senderId = FirebaseAuth.getInstance().getUid();
        String senderRoom = senderId + user.getUid();

        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String lastMsg = snapshot.child("lastMsg").getValue(String.class);

                            Long time = snapshot.child("lastMsgTime").getValue(Long.class);

                            if ((time != null) && (time != -1L)) {
                                String formattedTime = formatDate(time);
                                holder.binding.chatTime.setText(formattedTime);
                                holder.binding.lastChat.setText(lastMsg);
                            } else {
                                holder.binding.lastChat.setText("Tap to chat");
                                holder.binding.chatTime.setText("");

                            }
                        } else {
                            holder.binding.lastChat.setText("Tap to chat");
                            holder.binding.chatTime.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        holder.binding.chatName.setText(user.getName());
        Glide.with(context)
                .load(user.getProfileImage())
                .placeholder(R.drawable.avatar)
                .into(holder.binding.chatImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), chatActivity.class);
                i.putExtra("name", user.getName());
                i.putExtra("uid", user.getUid());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class usersViewHolder extends RecyclerView.ViewHolder {
        ChatsRowBinding binding;

        public usersViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ChatsRowBinding.bind(itemView);
        }
    }
    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(" hh:mm a", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}
