package com.example.collagefinalproject.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.collagefinalproject.R;
import com.example.collagefinalproject.databinding.FrgProductDetailBinding;
import com.example.collagefinalproject.db.DBHelper;
import com.example.collagefinalproject.model.Product;

public class FrgProductDetail extends Fragment {

    private FrgProductDetailBinding mBinding;
    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DBHelper(getActivity());
        mBinding = FrgProductDetailBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Product product = dbHelper.getProduct(getArguments().getInt("id"));

        mBinding.tvProductName.setText("Name : "+product.getName());
        mBinding.tvProductPrice.setText("Price : "+getString(R.string.price_symbol)+product.getPrice());
        mBinding.tvProductDesc.setText("Description:\n"+product.getDesc());
    }
}