package com.example.collagefinalproject.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.collagefinalproject.R;
import com.example.collagefinalproject.databinding.FrgAddProductDialogBinding;
import com.example.collagefinalproject.db.DBHelper;
import com.example.collagefinalproject.model.Category;
import com.example.collagefinalproject.model.Product;
import com.example.utils.InputValidation;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FrgAddProductDialog extends DialogFragment {
    private FrgAddProductDialogBinding mBinding;
    private DBHelper dbHelper;
    private InputValidation validation;
    private AddProductListener addProductListener;
    private int productId;

    public FrgAddProductDialog(FrgAddProductDialog.AddProductListener addProductListener) {
        this.addProductListener = addProductListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FrgAddProductDialogBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(mBinding.getRoot().getContext());
        validation = new InputValidation(mBinding.getRoot().getContext());

        ArrayList<Category> categoryList = dbHelper.getAllCategory();
        ArrayList<String> spinnerList = new ArrayList<>();
        for (Category category : categoryList) {
            spinnerList.add(category.getName());
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), R.layout.dropdown_item, spinnerList);
        mBinding.autoComplete.setAdapter(adapter1);

        mBinding.autoComplete.setOnItemClickListener((parent, view1, position, id) -> {
            productId = categoryList.get(position).getId();
            Snackbar.make(mBinding.getRoot(), "" + categoryList.get(position).getName(), Snackbar.LENGTH_LONG).show();

        });

        mBinding.btnAdd.setOnClickListener(v -> {
            if (!validation.isInputEditTextFilled(mBinding.autoComplete, mBinding.textinputSpinner, getString(R.string.error_message_select_category))) {
                return;
            }
            if (!validation.isInputEditTextFilled(mBinding.textInputEditTextProductName, mBinding.textInputLayoutProductName, getString(R.string.error_message_product_name))) {
                return;
            }
            if (!validation.isInputEditTextFilled(mBinding.textInputEditTextPrice, mBinding.textInputLayoutPrice, getString(R.string.error_message_product_price))) {
                return;
            }
            if (!validation.isInputEditTextFilled(mBinding.textInputEditTextDesc, mBinding.textInputLayoutPrice, getString(R.string.error_message_product_desc))) {
                return;
            }
            Snackbar.make(mBinding.getRoot(), "Product created", Snackbar.LENGTH_LONG).show();
            Product product = new Product();
            product.setCategoryId(productId);
            product.setName(mBinding.textInputEditTextProductName.getText().toString());
            product.setPrice(mBinding.textInputEditTextPrice.getText().toString());
            product.setDesc(mBinding.textInputEditTextDesc.getText().toString());
            addProductListener.addProductCallBack(product, getDialog());
        });
    }

    public interface AddProductListener {
        void addProductCallBack(Product product, Dialog dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().closeOptionsMenu();
    }
}