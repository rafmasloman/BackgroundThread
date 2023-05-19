package com.example.testfragment.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import com.example.testfragment.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "extra_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Background thread implementation
        Executor executor = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            handler.post(this::attachFragmentProfile);
        });
    }

    private void attachFragmentProfile() {
        // Set progress bar visibility
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        // Instance of fragment
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Find profile fragment by tag name
        Fragment fragment = fragmentManager.findFragmentByTag(ProfileFragment.class.getSimpleName());

        if (!(fragment instanceof ProfileFragment)) {
            // Instance of profile fragment
            ProfileFragment profileFragment = new ProfileFragment();

            // Set user
            profileFragment.setUser(getIntent().getParcelableExtra(EXTRA_USER));

            // Replace profile activity layout with ProfileFragment
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, profileFragment, ProfileFragment.class.getSimpleName())
                    .commit();
        }
    }
}