package com.elif.main.easyshop;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String PREF_NAME = "EasyShopCart";
    private static final String CART_KEY = "cart_items";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public CartManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public List<DataClass> getCartItems() {
        String json = sharedPreferences.getString(CART_KEY, null);
        Type type = new TypeToken<List<DataClass>>() {}.getType();
        if (json == null) {
            return new ArrayList<>();
        }
        return gson.fromJson(json, type);
    }

    public void addToCart(DataClass item) {
        List<DataClass> cartList = getCartItems();
        cartList.add(item);
        saveCart(cartList);
    }

    private void saveCart(List<DataClass> cartList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(cartList);
        editor.putString(CART_KEY, json);
        editor.apply();
    }

    public void clearCart() {
        sharedPreferences.edit().remove(CART_KEY).apply();
    }
}