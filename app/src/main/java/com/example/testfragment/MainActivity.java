package com.example.testfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testfragment.data.models.Post;
import com.example.testfragment.data.models.User;
import com.example.testfragment.home.HomeFragment;
import com.example.testfragment.profile.ProfileFragment;
import com.example.testfragment.search.SearchFragment;
import com.example.testfragment.upload.UploadFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // User instance
        user = new User(
                getString(R.string.full_name),
                getString(R.string.username),
                R.drawable.avatar_profile,
                new Post()
        );

        // Membuat Fragment
        fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());

        if (!(fragment instanceof HomeFragment)) {
            navigateFragment(new HomeFragment());
        }

        navigationListener();
        keyboardVisibleListener();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame_container);

        if (currentFragment instanceof HomeFragment) {
            super.onBackPressed();
        } else {
            navigateFragment(new HomeFragment());
        }
    }


    private void navigationListener() {
        ImageView navHome = findViewById(R.id.nav_home);
        ImageView navAdd = findViewById(R.id.nav_add);
        ImageView navProfile = findViewById(R.id.nav_profile);
        ImageView navSearch = findViewById(R.id.nav_search);

        // Navigasi berdasarkan tab yang di-click
        navHome.setOnClickListener(v -> navigateFragment(new HomeFragment()));
        navAdd.setOnClickListener(v -> navigateFragment(new UploadFragment()));
        navSearch.setOnClickListener(v -> navigateFragment(new SearchFragment()));
        navProfile.setOnClickListener(v -> {
            ProfileFragment profileFragment = new ProfileFragment();

            profileFragment.setUser(user);

            navigateFragment(profileFragment);
        });

    }

    public void navigateFragment(Fragment fragment) {
        // Menimpa fragment
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName())
                .commit();

        // Merubah text toolbar
        TextView tvToolbar = findViewById(R.id.tv_toolbar);

        if (fragment instanceof HomeFragment) {
            tvToolbar.setText(getString(R.string.inigaram));
        } else if (fragment instanceof ProfileFragment) {
            tvToolbar.setText(getString(R.string.profile));
        } else if (fragment instanceof UploadFragment) {
            tvToolbar.setText(getString(R.string.upload));
        } else if (fragment instanceof SearchFragment) {
            tvToolbar.setText(getString(R.string.search));
        }
    }

    private void keyboardVisibleListener() {
        // Mendapatkan root dan bottom navbar layout
        ConstraintLayout rootLayout = findViewById(R.id.root_layout);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        rootLayout.addOnLayoutChangeListener((view, i, i1, i2, i3, i4, i5, i6, i7) -> {
            Rect r = new Rect();

            view.getWindowVisibleDisplayFrame(r);

            int screenHeight = view.getRootView().getHeight();
            int keyboardHeight = screenHeight - r.bottom;

            boolean isKeyboardVisible = keyboardHeight > screenHeight * 0.15;

            if (isKeyboardVisible) {
                linearLayout.setVisibility(View.GONE);
            } else {
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}