package com.example.testfragment.upload;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.testfragment.MainActivity;
import com.example.testfragment.R;
import com.example.testfragment.data.models.Post;
import com.example.testfragment.data.models.User;
import com.example.testfragment.home.HomeFragment;

public class UploadFragment extends Fragment {
    private ActivityResultLauncher<Intent> launcherIntentPhotos;
    private MainActivity mainActivity;
    private ImageView ivPhoto;
    private EditText etCaption;
    private Button btnUpload;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        launcherIntentPhotos = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent resultIntent = result.getData();

                        if (resultIntent != null) {
                            Uri selectedImageUri = resultIntent.getData();

                            user.getPost().setImageUrl(selectedImageUri);

                            Glide.with(container.getRootView().getContext())
                                    .load(user.getPost().getImageUrl())
                                    .into(ivPhoto);

                            // ivPhoto.setImageURI(selectedImageUri);
                        }
                    }
                });

        return inflater.inflate(R.layout.fragment_upload, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivPhoto = view.findViewById(R.id.iv_photo);
        etCaption = view.findViewById(R.id.et_caption);
        btnUpload = view.findViewById(R.id.btn_upload);

        // Mengakses main activity
        mainActivity = ((MainActivity) getActivity());

        // Membuat object user
        user = new User(getString(R.string.full_name), getString(R.string.username), R.drawable.avatar_profile, new Post());

        // Handle onClick photo
        ivPhoto.setOnClickListener(v -> {
            Intent intentPickPhotos = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            launcherIntentPhotos.launch(Intent.createChooser(intentPickPhotos, "Choose a photo"));
        });

        // Handle onClick tombol upload
        btnUpload.setOnClickListener(v -> upload());
    }

    private void upload() {
        // Cek apakah gambar tak kosong
        if (user.getPost().getImageUrl() == null) {
            Toast.makeText(getActivity(), "Please pick a photo first", Toast.LENGTH_SHORT).show();

            return;
        }

        // Memberikan caption ke post user
        String caption = etCaption.getText().toString().trim();
        user.getPost().setCaption(caption);

        // Clear tampilan
        etCaption.setText("");
        ivPhoto.setImageURI(null);

        // Menggunakan static data source dan menambahkan item ke list user
        HomeFragment.dataSource.addUser(user);

        // Navigasi ke home
        mainActivity.navigateFragment(new HomeFragment());

        Toast.makeText(mainActivity, "Post success", Toast.LENGTH_SHORT).show();
    }
}