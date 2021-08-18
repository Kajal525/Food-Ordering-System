package com.example.collagefinalproject.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagefinalproject.R;
import com.example.collagefinalproject.activity.ActHome;
import com.example.collagefinalproject.adapter.ProductListAdapter;
import com.example.collagefinalproject.databinding.FrgProductBinding;
import com.example.collagefinalproject.db.DBHelper;
import com.example.collagefinalproject.model.Product;
import com.example.utils.InputValidation;

public class FrgProductList extends Fragment implements FrgAddProductDialog.AddProductListener, ProductListAdapter.ProductClickListener {
    private FrgProductBinding mBinding;
    DBHelper dbHelper;
    InputValidation validation;
    private ProductListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FrgProductBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getActivity());
        validation = new InputValidation(getActivity());

        mBinding.btnAdd.setOnClickListener(v -> {
            FrgAddProductDialog dialog = new FrgAddProductDialog(this);
            dialog.show(getActivity().getSupportFragmentManager(), FrgAddProductDialog.class.getSimpleName());
        });

        if (dbHelper.getAllCategory().isEmpty() && dbHelper.getAllCategory().size() == 0) {
            mBinding.btnAdd.setVisibility(View.GONE);
            mBinding.tvEmptyCategory.setVisibility(View.VISIBLE);
        } else {
            if (!dbHelper.getAllProducts().isEmpty() && dbHelper.getAllProducts().size() > 0) {
                adapter = new ProductListAdapter(getActivity(),dbHelper.getAllProducts(), this);
                mBinding.rvProduct.setAdapter(adapter);
            } else {
                mBinding.rvProduct.setVisibility(View.GONE);
                mBinding.tvEmpty.setVisibility(View.VISIBLE);
            }
        }
        if (adapter != null) {
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    mBinding.tvEmpty.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    mBinding.tvEmpty.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    mBinding.tvEmpty.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    @Override
    public void addProductCallBack(Product product, Dialog dialog) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            dbHelper.addProduct(product);
            dialog.dismiss();
            if (adapter != null) {
                adapter.updateList(dbHelper.getAllProducts());
            } else {
                ((ActHome) getActivity()).navigate(R.id.nav_product, null);
            }
        }, 500);
    }

    @Override
    public void onDelete(Product product) {
        dbHelper.deleteProduct(product.getId());
        Toast.makeText(getActivity(), product.getName() + " deleted", Toast.LENGTH_SHORT).show();
    }
}