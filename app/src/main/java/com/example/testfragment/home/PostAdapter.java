package com.example.testfragment.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testfragment.profile.ProfileActivity;
import com.example.testfragment.R;
import com.example.testfragment.data.models.User;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private final ArrayList<User> users;

    public PostAdapter(ArrayList<User> users) {
        this.users = users;
    }

//    public void addItem(User user) {
//        users.add(user);
//
//        notifyItemInserted(users.size() - 1);
//    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        User user = users.get(position);

        holder.tvUsername.setText(user.getUsername());
        holder.tvFullName.setText(user.getFullName());
        Glide.with(holder.itemView.getContext()).load(user.getPhotoRes()).into(holder.ivUserPhoto);

        holder.tvCaption.setText(user.getPost().getCaption());
        Glide.with(holder.itemView.getContext()).load(user.getPost().getImageUrl()).into(holder.ivPostPhoto);

        holder.ivUserPhoto.setOnClickListener(v -> {
            Intent toProfile = new Intent(holder.itemView.getContext(), ProfileActivity.class);

            toProfile.putExtra(ProfileActivity.EXTRA_USER, user);

            holder.itemView.getContext().startActivity(toProfile);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView ivUserPhoto;
        ImageView ivPostPhoto;
        TextView tvFullName, tvUsername, tvCaption;

        ViewHolder(View view) {
            super(view);

            ivUserPhoto = view.findViewById(R.id.iv_user_photo);
            ivPostPhoto = view.findViewById(R.id.iv_post_photo);
            tvUsername = view.findViewById(R.id.tv_username);
            tvFullName = view.findViewById(R.id.tv_fullname);
            tvCaption = view.findViewById(R.id.tv_caption);
        }
    }
}
