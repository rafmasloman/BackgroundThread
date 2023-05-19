package com.example.testfragment.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.testfragment.R;
import com.example.testfragment.data.models.User;
import com.google.android.material.imageview.ShapeableImageView;

public class ProfileFragment extends Fragment {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ShapeableImageView ivUserPhoto = view.findViewById(R.id.circleImageView);
        TextView tvFullname = view.findViewById(R.id.textView);
        TextView tvUsername = view.findViewById(R.id.textView2);

        tvFullname.setText(user.getFullName());
        tvUsername.setText(user.getUsername());
        Glide.with(view.getContext()).load(user.getPhotoRes()).into(ivUserPhoto);
    }
}