package com.example.collagefinalproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagefinalproject.R;
import com.example.collagefinalproject.callback.ProductClickListener;
import com.example.collagefinalproject.databinding.RawProductCardBinding;
import com.example.collagefinalproject.model.Cart;
import com.example.collagefinalproject.model.Product;

import java.util.ArrayList;

public class ProductListCardAdapter extends RecyclerView.Adapter<ProductListCardAdapter.ViewHolder> {
    private RawProductCardBinding binding;
    private ArrayList<Product> list;
    private ProductClickListener clickListener;
    private Context context;
    ArrayList<Cart> cartList;

    public ProductListCardAdapter(Context context, ArrayList<Product> list, ArrayList<Cart> cartList, ProductClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RawProductCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvProductName.setText(list.get(position).getName());
        holder.binding.tvProductPrice.setText(context.getString(R.string.price_symbol) + list.get(position).getPrice());

        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getId() == list.get(position).getId()) {
                holder.binding.btnAddToCart.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.black));
                holder.binding.btnAddToCart.setEnabled(false);
                holder.binding.btnAddToCart.setText(context.getString(R.string.added_cart));
            }
        }
        holder.binding.btnAddToCart.setOnClickListener(v -> {
            clickListener.addToCartItem(list.get(position), 1);
            holder.binding.btnAddToCart.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.black));
            holder.binding.btnAddToCart.setEnabled(false);
            holder.binding.btnAddToCart.setText(context.getString(R.string.added_cart));
        });

        holder.itemView.setOnClickListener(v -> {clickListener.showDetail(list.get(position).getId());});
    }

    public void updateList(ArrayList<Product> itemList) {
        list.clear();
        this.list.addAll(itemList);
        notifyItemInserted(list.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RawProductCardBinding binding;

        public ViewHolder(@NonNull RawProductCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
