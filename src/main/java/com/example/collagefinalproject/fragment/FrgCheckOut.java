package com.example.collagefinalproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.collagefinalproject.R;
import com.example.collagefinalproject.activity.ActHome;
import com.example.collagefinalproject.databinding.FrgCheckoutBinding;
import com.example.collagefinalproject.db.DBHelper;

public class FrgCheckOut extends Fragment {
    private FrgCheckoutBinding mBinding;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FrgCheckoutBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(getActivity());
        ((ActHome) getActivity()).hideToolbar();
        mBinding.btnHome.setOnClickListener(v -> {
            dbHelper.deleteCartAllItem();
            ((ActHome) getActivity()).navigate(R.id.nav_home, null);
            ((ActHome) getActivity()).visibleToolbar();
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.actionCart).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}