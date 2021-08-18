package com.example.collagefinalproject.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagefinalproject.databinding.RawCategoryBinding;
import com.example.collagefinalproject.model.Category;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private RawCategoryBinding binding;
    private ArrayList<Category> list;
    private CategoryClickListener clickListener;

    public CategoryListAdapter(ArrayList<Category> list, CategoryClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RawCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvCategory.setText(list.get(position).getName());
        holder.binding.getRoot().setOnClickListener(v -> clickListener.onClick(list.get(position)));
        holder.binding.ivDelete.setOnClickListener(v -> {
            if (position >= 0) {
                clickListener.onDelete(list.get(position));
                list.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    public void updateList(ArrayList<Category> itemList) {
        this.list.clear();
        this.list.addAll(itemList);
        notifyItemInserted(list.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RawCategoryBinding binding;

        public ViewHolder(@NonNull RawCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface CategoryClickListener {
        void onClick(Category category);

        void onDelete(Category category);
    }
}
