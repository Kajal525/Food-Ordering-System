package com.example.collagefinalproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.collagefinalproject.R;
import com.example.collagefinalproject.activity.ActHome;
import com.example.collagefinalproject.adapter.CategoryTabListAdapter;
import com.example.collagefinalproject.adapter.ProductListCardAdapter;
import com.example.collagefinalproject.callback.ProductClickListener;
import com.example.collagefinalproject.databinding.FrgHomeBinding;
import com.example.collagefinalproject.db.DBHelper;
import com.example.collagefinalproject.model.Cart;
import com.example.collagefinalproject.model.Category;
import com.example.collagefinalproject.model.Product;

import java.util.ArrayList;
import java.util.Collections;

public class FrgHome extends Fragment implements CategoryTabListAdapter.CategoryClickListener, ProductClickListener {
    private FrgHomeBinding mBinding;
    DBHelper dbHelper;
    private ProductListCardAdapter productListCardAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FrgHomeBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(getActivity());
        if (!dbHelper.getAllProducts().isEmpty() && dbHelper.getAllProducts().size() > 0) {
            ArrayList<Category> list = dbHelper.getAllCategory();
            Category category = new Category();
            category.setId(-1);
            category.setName("All");
            list.add(category);
            Collections.reverse(list);
            CategoryTabListAdapter categoryTabListAdapter = new CategoryTabListAdapter(getActivity(), list, this);
            mBinding.rvCategory.setAdapter(categoryTabListAdapter);
        } else
            mBinding.tvEmpty.setVisibility(View.VISIBLE);


        //First time We will display  all category wise product listing
        productListCardAdapter = new ProductListCardAdapter(getActivity(), dbHelper.getAllProducts(), dbHelper.getAllCartItems(), this);
        mBinding.rvProduct.setAdapter(productListCardAdapter);
    }

    //Category click listener
    @Override
    public void onClick(Category category) {
        if (category.getId() == -1) {
            productListCardAdapter.updateList(dbHelper.getAllProducts());
        } else {
            productListCardAdapter.updateList(dbHelper.getAllProductsById(category.getId()));
        }
    }

    @Override
    public void addToCartItem(Product product, int value) {
        Cart cart = new Cart();
        cart.setQty(value);
        cart.setName(product.getName());
        cart.setPrice(product.getPrice());
        cart.setId(product.getId());
        dbHelper.addToCart(cart);
        ((ActHome) getActivity()).setupCartBadge();
    }

    @Override
    public void showDetail(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        ((ActHome) getActivity()).navigate(R.id.nav_product_detail,bundle);
    }
}