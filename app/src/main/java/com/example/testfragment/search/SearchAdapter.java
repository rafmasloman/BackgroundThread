package com.example.testfragment.search;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testfragment.R;
import com.example.testfragment.data.models.User;
import com.example.testfragment.profile.ProfileActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private final ArrayList<User> users = new ArrayList<>();

    public void setUsers(ArrayList<User> users) {
        // Hapus users sebelumnya jika ada
        if (this.users.size() > 0) {
            this.users.clear();
        }

        // Tambahkan users baru
        this.users.addAll(users);
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        User user = users.get(position);

        holder.tvFullName.setText(user.getFullName());
        holder.tvUsername.setText(user.getUsername());
        Glide.with(holder.itemView.getContext()).load(user.getPhotoRes()).into(holder.ivUserPhoto);

        holder.itemView.setOnClickListener(v -> {
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
        TextView tvFullName, tvUsername;

        ViewHolder(View view) {
            super(view);

            ivUserPhoto = view.findViewById(R.id.iv_user_photo);
            tvFullName = view.findViewById(R.id.tv_fullname);
            tvUsername = view.findViewById(R.id.tv_username);
        }
    }
}
