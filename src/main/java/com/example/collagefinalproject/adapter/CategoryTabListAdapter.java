package com.example.collagefinalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagefinalproject.R;
import com.example.collagefinalproject.databinding.RawCategoryTabBinding;
import com.example.collagefinalproject.model.Category;

import java.util.ArrayList;

public class CategoryTabListAdapter extends RecyclerView.Adapter<CategoryTabListAdapter.ViewHolder> {
    private RawCategoryTabBinding binding;
    private ArrayList<Category> list;
    private CategoryClickListener clickListener;
    int selectedPos = 0;
    Context context;

    public CategoryTabListAdapter(Context context, ArrayList<Category> list, CategoryClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RawCategoryTabBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvCategory.setText(list.get(position).getName());
        holder.binding.getRoot().setOnClickListener(v -> {
            clickListener.onClick(list.get(position));
            selectedPos = position;
            notifyDataSetChanged();
        });
        if (position == selectedPos) {
            holder.binding.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.binding.tvCategory.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.binding.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.binding.tvCategory.setTextColor(ContextCompat.getColor(context, R.color.colorBackground));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RawCategoryTabBinding binding;

        public ViewHolder(@NonNull RawCategoryTabBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface CategoryClickListener {
        void onClick(Category category);
    }
}
