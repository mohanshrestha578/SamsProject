package com.example.samsproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {


    private Context mctx;           //to inflate usrviewholder
    private List<User> userList;

    public UsersAdapter(Context mctx, List<User> userList) {
        this.mctx = mctx;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.users_layout, null);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position) {

        User user = userList.get(position);

        userViewHolder.textView.setText(user.getName());
        userViewHolder.imageView.setImageDrawable(mctx.getResources().getDrawable(user.getImage()));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.userImage);
            textView = itemView.findViewById(R.id.userName);
        }
    }
}

