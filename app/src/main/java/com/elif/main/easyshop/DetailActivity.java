package com.elif.main.easyshop;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    TextView detailDesc, detailTitle, detailLang;
    ImageView detailImage;
    Button btnAddToCart;
    String key = "";
    String imageUrl = "";

    CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailDesc = findViewById(R.id.detailDesc);
        detailImage = findViewById(R.id.detailImage);
        detailTitle = findViewById(R.id.detailTitle);
        detailLang = findViewById(R.id.detailLang);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailTitle.setText(bundle.getString("Title"));
            detailDesc.setText(bundle.getString("Description"));
            detailLang.setText(bundle.getString("Language"));
            imageUrl = bundle.getString("Image");
            key = bundle.getString("Key");

            Glide.with(this).load(imageUrl).into(detailImage);
        }

        cartManager = new CartManager(this);

        btnAddToCart.setOnClickListener(view -> {
            DataClass currentProduct = new DataClass(
                    detailTitle.getText().toString(),
                    detailDesc.getText().toString(),
                    detailLang.getText().toString(),
                    imageUrl
            );
            currentProduct.setKey(key);

            cartManager.addToCart(currentProduct);

            Toast.makeText(DetailActivity.this, "Ürün sepete eklendi!", Toast.LENGTH_SHORT).show();
        });
    }
}