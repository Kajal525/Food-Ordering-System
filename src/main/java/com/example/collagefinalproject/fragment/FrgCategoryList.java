package com.example.collagefinalproject.fragment;

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
import com.example.collagefinalproject.adapter.CategoryListAdapter;
import com.example.collagefinalproject.databinding.FrgCategoryBinding;
import com.example.collagefinalproject.db.DBHelper;
import com.example.collagefinalproject.model.Category;
import com.example.utils.InputValidation;
import com.google.android.material.snackbar.Snackbar;

public class FrgCategoryList extends Fragment implements CategoryListAdapter.CategoryClickListener {
    private FrgCategoryBinding mBinding;
    DBHelper dbHelper;
    InputValidation validation;
    private CategoryListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FrgCategoryBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getActivity());
        validation = new InputValidation(getActivity());

        mBinding.btnAdd.setOnClickListener(v -> {
            if (!validation.isInputEditTextFilled(mBinding.etCategory, mBinding.inputCategory, getString(R.string.error_message_category))) {
                return;
            }
            Snackbar.make(mBinding.getRoot(), "Category created", Snackbar.LENGTH_LONG).show();
            Category category = new Category();
            category.setName(mBinding.etCategory.getText().toString());
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                dbHelper.addCategory(category);
                if (adapter != null) {
                    adapter.updateList(dbHelper.getAllCategory());
                } else {
                    ((ActHome) getActivity()).navigate(R.id.nav_category, null);
                }
                mBinding.etCategory.setText(null);
            }, 500);
        });

        if (!dbHelper.getAllCategory().isEmpty() && dbHelper.getAllCategory().size() > 0) {
            adapter = new CategoryListAdapter(dbHelper.getAllCategory(), this);
            mBinding.rvCategory.setAdapter(adapter);
        } else {
            mBinding.rvCategory.setVisibility(View.GONE);
            mBinding.tvEmpty.setVisibility(View.VISIBLE);
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
    public void onClick(Category category) {
        Toast.makeText(mBinding.getRoot().getContext(), "" + category.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelete(Category category) {
        dbHelper.deleteCategory(category.getId());
        Toast.makeText(mBinding.getRoot().getContext(), category.getName() + " deleted", Toast.LENGTH_SHORT).show();
    }
}