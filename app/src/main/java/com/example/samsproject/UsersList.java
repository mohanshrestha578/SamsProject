package com.example.samsproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class UsersList extends AppCompatActivity {


    RecyclerView recyclerView;
    UsersAdapter usersAdapter;

    List<User> userList;

    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        userList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList.add(
                new User(1, R.drawable.one, "Mohan Shrestha")
        );

        userList.add(
                new User(2, R.drawable.two, "Ram Shrestha")
        );

        userList.add(
                new User(3, R.drawable.three, "Shyam Shrestha")
        );

        userList.add(
                new User(1, R.drawable.one, "Mohan Shrestha")
        );

        userList.add(
                new User(2, R.drawable.two, "Ram Shrestha")
        );

        userList.add(
                new User(3, R.drawable.three, "Shyam Shrestha")
        );

        userList.add(
                new User(1, R.drawable.one, "Mohan Shrestha")
        );

        userList.add(
                new User(2, R.drawable.two, "Ram Shrestha")
        );

        userList.add(
                new User(3, R.drawable.three, "Shyam Shrestha")
        );

        userList.add(
                new User(1, R.drawable.one, "Mohan Shrestha")
        );

        userList.add(
                new User(2, R.drawable.two, "Ram Shrestha")
        );

        userList.add(
                new User(3, R.drawable.three, "Shyam Shrestha")
        );

        usersAdapter = new UsersAdapter(this, userList);
         recyclerView.setAdapter(usersAdapter);
    }


}
