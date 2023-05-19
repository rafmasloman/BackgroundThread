package com.example.testfragment.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testfragment.R;
import com.example.testfragment.home.HomeFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SuppressLint("NotifyDataSetChanged")
public class SearchFragment extends Fragment {

    private SearchAdapter adapter;
    private RecyclerView rvSearch;
    private TextInputEditText etSearch;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find widgets
        rvSearch = view.findViewById(R.id.rv_search);
        etSearch = view.findViewById(R.id.et_search);
        progressBar = view.findViewById(R.id.progress_bar);

        // Setup adapter
        adapter = new SearchAdapter();

        // Setup recycler view
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearch.setAdapter(adapter);

        // Setup searching
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    private void searchUser(CharSequence querySearch) {
        showProgressBar(true);

        // Background thread implementation
        Executor executor = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            handler.post(() -> {
                if (TextUtils.isEmpty(querySearch)) {
                    adapter.setUsers(new ArrayList<>());
                } else {
                    adapter.setUsers(HomeFragment.dataSource.getUsersByQuery(querySearch.toString()));
                }

                adapter.notifyDataSetChanged();

                showProgressBar(false);
            });
        });
    }

    private void showProgressBar(boolean isShow) {
        if (isShow) {
            progressBar.setVisibility(View.VISIBLE);
            rvSearch.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            rvSearch.setVisibility(View.VISIBLE);
        }
    }
}