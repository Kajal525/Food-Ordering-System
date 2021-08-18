package com.example.collagefinalproject.callback;

import com.example.collagefinalproject.model.Product;

public interface ProductClickListener {
    void addToCartItem(Product product, int value);

    void showDetail(int id);
}
