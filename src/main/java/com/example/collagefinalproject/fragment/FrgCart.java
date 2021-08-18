package com.example.collagefinalproject.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagefinalproject.R;
import com.example.collagefinalproject.activity.ActHome;
import com.example.collagefinalproject.adapter.CartAdapter;
import com.example.collagefinalproject.databinding.FrgCartBinding;
import com.example.collagefinalproject.db.DBHelper;
import com.example.collagefinalproject.model.Cart;

public class FrgCart extends Fragment implements CartAdapter.CartClickListener {
    private FrgCartBinding mBinding;
    DBHelper dbHelper;
    private CartAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FrgCartBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(getActivity());

        if (!dbHelper.getAllCartItems().isEmpty() && dbHelper.getAllCartItems().size() > 0) {
            adapter = new CartAdapter(dbHelper.getAllCartItems(), this);
            mBinding.rvCart.setAdapter(adapter);
            mBinding.llBottom.setVisibility(View.VISIBLE);
        } else {
            mBinding.rvCart.setVisibility(View.GONE);
            mBinding.tvEmpty.setVisibility(View.VISIBLE);
            mBinding.llBottom.setVisibility(View.GONE);
        }

        if (adapter != null) {
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    mBinding.tvEmpty.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                    mBinding.llBottom.setVisibility(adapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    mBinding.tvEmpty.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                    mBinding.llBottom.setVisibility(adapter.getItemCount() == 0 ? View.GONE : View.GONE);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    mBinding.tvEmpty.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                    mBinding.llBottom.setVisibility(adapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
                }
            });
        }

        calculateTotal();

        mBinding.btnNext.setOnClickListener(v -> ((ActHome) getActivity()).navigate(R.id.nav_checkout, null));
    }

    private void calculateTotal() {
        double total = 0;
        for (Cart model : dbHelper.getAllCartItems()) {
            total = total + Double.parseDouble(model.getPrice()) * model.getQty();
        }
        mBinding.tvCartTotalPrice.setText(String.format(getString(R.string.total_price), String.valueOf(total)));
    }

    @Override
    public void onMinus(Cart cart, int qty) {
        dbHelper.updateCart(cart.getId(), qty);
        new Handler(Looper.getMainLooper()).postDelayed(this::calculateTotal, 200);
    }

    @Override
    public void onPlus(Cart cart, int qty) {
        dbHelper.updateCart(cart.getId(), qty);
        new Handler(Looper.getMainLooper()).postDelayed(this::calculateTotal, 200);

    }

    @Override
    public void onDelete(Cart cart, int position) {
        dbHelper.deleteCart(cart.getId());
        new Handler(Looper.getMainLooper()).postDelayed(this::calculateTotal, 200);
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