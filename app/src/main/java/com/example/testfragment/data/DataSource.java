package com.example.testfragment.data;

import android.net.Uri;

import com.example.testfragment.R;
import com.example.testfragment.data.models.Post;
import com.example.testfragment.data.models.User;

import java.util.ArrayList;

public class DataSource {
    // com.example.testfragment adalah nama package project (sesuaikan dengan project kalian)
    private static final String URI_CONST = "android.resource://com.example.testfragment/drawable/";

    private final ArrayList<User> users = new ArrayList<>();

    public DataSource() {
        this.users.addAll(generateDummyUsers());
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<User> getUsersByQuery(String q) {
        ArrayList<User> filteredUsers = new ArrayList<>();

        User tempUser = users.get(0);

        for (int i = 0; i < users.size(); i++) {
            final User user = users.get(i);

            // Pokoknya untuk handle supaya pas nge-search tidak ada user duplikat
            if (i > 0) {
                if (tempUser.getUsername().equals(user.getUsername()) || tempUser.getFullName().equals(user.getFullName())) {
                    continue;
                }
            }

            String query = q.toLowerCase();

            String fullName = user.getFullName().toLowerCase();
            String username = user.getUsername().toLowerCase();

            if (fullName.startsWith(query) || username.startsWith(query)) {
                filteredUsers.add(user);
            }

            tempUser = user;
        }

        return filteredUsers;
    }

    public void addUser(User user) {
        this.users.add(0, user);
    }

    private ArrayList<User> generateDummyUsers() {
        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            User user = new User(
                    names[i][0],
                    names[i][1],
                    photoRes[i],
                    new Post(
                            Uri.parse(URI_CONST + photoRes[i]),
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In gravida risus sit amet felis ornare, nec dignissim ante ultrices."
                    )
            );

            users.add(user);
        }

        return users;
    }

    private final String[][] names = new String[][]{
            {"Monkey D. Luffy", "Luffy"},
            {"Roronoa Zoro", "Zoro"},
            {"Siti Nami", "Nami"},
            {"Ahmad Usopp", "Usopp"},
            {"Muhammad Sanji", "Sanji"}
    };

    private final int[] photoRes = new int[]{
            R.drawable.user_1,
            R.drawable.user_2,
            R.drawable.user_3,
            R.drawable.user_4,
            R.drawable.user_5,
    };
}
