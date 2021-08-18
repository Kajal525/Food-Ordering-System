package com.example.collagefinalproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagefinalproject.R;
import com.example.collagefinalproject.databinding.RawProductBinding;
import com.example.collagefinalproject.model.Product;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private RawProductBinding binding;
    private ArrayList<Product> list;
    private ProductClickListener clickListener;
    private Context context;

    public ProductListAdapter(Context context, ArrayList<Product> list, ProductClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RawProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvProductName.setText(list.get(position).getName());
        holder.binding.tvProductPrice.setText(context.getString(R.string.price_symbol)+" "+list.get(position).getPrice());
        holder.binding.ivDelete.setOnClickListener(v -> {
            if (position >= 0) {
                clickListener.onDelete(list.get(position));
                list.remove(position);
                notifyItemRemoved(position);
            }
        });
        Log.d("TAG", "onBindViewHolder: " + list.get(position).getName());
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
        RawProductBinding binding;

        public ViewHolder(@NonNull RawProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ProductClickListener {
        void onDelete(Product category);
    }
}
