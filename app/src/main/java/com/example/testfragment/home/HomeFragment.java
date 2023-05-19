package com.example.testfragment.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testfragment.R;
import com.example.testfragment.data.DataSource;


public class HomeFragment extends Fragment {
    // Static datasource
    public static DataSource dataSource = new DataSource();

    // Static adapter
    public static PostAdapter adapter = new PostAdapter(dataSource.getUsers());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvEmptyPosts = view.findViewById(R.id.tv_empty_posts);
        RecyclerView rvPost = view.findViewById(R.id.rv_post);

        // Setup recycler view
        rvPost.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPost.setAdapter(adapter);

        // Saat item tidak kosong maka sembunyikan textview post
//        if (adapter.getItemCount() > 0) {
//            tvEmptyPosts.setVisibility(View.GONE);
//        }
    }
}