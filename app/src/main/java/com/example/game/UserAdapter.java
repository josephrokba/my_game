package com.example.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;





public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsersHolder> {
    ArrayList<Users> users;
    Context context;

    class UsersHolder extends RecyclerView.ViewHolder{

        TextView full_name,date,score;

        public UsersHolder(@NonNull View itemView) {
            super(itemView);

            score = itemView.findViewById(R.id.score);
            full_name = itemView.findViewById(R.id.full_name);
            date = itemView.findViewById(R.id.date);

        }
    }


    public  UserAdapter( Context context,ArrayList<Users> users) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(context).inflate(R.layout.activity_item_user,parent,false);
       return new UsersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersHolder holder, int position) {

        Users u = users.get(position);
        holder.full_name.setText(u.getFullName());
        holder.date.setText(u.getDate());
        holder.score.setText(String.valueOf(u.getScore()));
    }



    @Override
    public int getItemCount() {
        return users.size();
    }


}
