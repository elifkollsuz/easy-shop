package com.elif.main.easyshop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter adapter;
    CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.rvCart);
        cartManager = new CartManager(this);

        Button btnCheckout = findViewById(R.id.btnCheckout);

        List<DataClass> cartList = cartManager.getCartItems();

        adapter = new MyAdapter(this, cartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnCheckout.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, PaymentActivity.class));
        });
    }
}