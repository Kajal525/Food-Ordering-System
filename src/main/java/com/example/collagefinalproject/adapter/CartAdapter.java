package com.example.collagefinalproject.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagefinalproject.R;
import com.example.collagefinalproject.databinding.RawCartBinding;
import com.example.collagefinalproject.model.Cart;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private RawCartBinding binding;
    private ArrayList<Cart> list;
    private final CartClickListener clickListener;

    public CartAdapter(ArrayList<Cart> list, CartClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RawCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvCartItemName.setText(list.get(position).getName());
        holder.binding.tvCartItemPrice.setText(holder.itemView.getContext().getString(R.string.price_symbol)+list.get(position).getPrice());
        holder.binding.tvCartItemQty.setText(String.valueOf(list.get(position).getQty()));

        holder.binding.ivDelete.setOnClickListener(v -> {
            if (holder.getAdapterPosition() >= 0) {
                clickListener.onDelete(list.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                list.get(holder.getAdapterPosition()).setQty(1);
                list.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

            }
        });
        holder.binding.ivPlus.setOnClickListener(v -> {
            int value = Integer.parseInt(holder.binding.tvCartItemQty.getText().toString());
            if (value <= 0)
                value = 1;
            else {
                value++;
            }
            holder.binding.tvCartItemQty.setText(String.valueOf(value));
            clickListener.onPlus(list.get(position), value);
        });

        holder.binding.ivMinus.setOnClickListener(v -> {
            int value = Integer.parseInt(holder.binding.tvCartItemQty.getText().toString());
            if (value > 1)
                value--;
            else {
                value = 1;
            }
            holder.binding.tvCartItemQty.setText(String.valueOf(value));
            clickListener.onMinus(list.get(position), value);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RawCartBinding binding;

        public ViewHolder(@NonNull RawCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface CartClickListener {
        void onMinus(Cart cart, int position);

        void onPlus(Cart cart, int position);

        void onDelete(Cart cart, int position);
    }
}
