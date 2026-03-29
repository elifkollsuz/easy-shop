package com.elif.main.easyshop;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private RelativeLayout cardFront, cardBack;
    private AnimatorSet frontOut, backIn, backOut, frontIn;
    private boolean isBackVisible = false;

    private EditText etCardNumber, etCardHolder, etCardExpiry, etCardCvv;
    private TextView tvCardNumber, tvCardHolder, tvCardExpiry, tvCardCvv;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        findViews();
        loadAnimations();
        setupTextWatchers();

        float scale = getResources().getDisplayMetrics().density;
        cardFront.setCameraDistance(8000 * scale);
        cardBack.setCameraDistance(8000 * scale);

        etCardCvv.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                flipToBack();
            } else {
                flipToFront();
            }
        });

        btnPay.setOnClickListener(v -> {
            Toast.makeText(this, "Siparişiniz başarıyla alındı! Teşekkürler.", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void loadAnimations() {
        frontOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_right_out);
        backIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_right_in);
        backOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_left_out);
        frontIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_left_in);
    }

    private void flipToBack() {
        if (!isBackVisible) {
            frontOut.setTarget(cardFront);
            backIn.setTarget(cardBack);
            frontOut.start();
            backIn.start();
            cardBack.setVisibility(View.VISIBLE);
            isBackVisible = true;
        }
    }

    private void flipToFront() {
        if (isBackVisible) {
            backOut.setTarget(cardBack);
            frontIn.setTarget(cardFront);
            backOut.start();
            frontIn.start();
            isBackVisible = false;
        }
    }

    private void setupTextWatchers() {
        etCardNumber.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) tvCardNumber.setText("**** **** **** ****");
                else tvCardNumber.setText(formatCardNumber(s.toString()));
            }
        });

        etCardHolder.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) tvCardHolder.setText("AD SOYAD");
                else tvCardHolder.setText(s.toString().toUpperCase());
            }
        });

        etCardExpiry.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) tvCardExpiry.setText("MM/YY");
                else tvCardExpiry.setText(s.toString());
            }
        });

        etCardCvv.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) tvCardCvv.setText("***");
                else tvCardCvv.setText(s.toString());
            }
        });
    }

    private String formatCardNumber(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && i % 4 == 0) result.append(" ");
            result.append(s.charAt(i));
        }
        return result.toString();
    }

    private void findViews() {
        cardFront = findViewById(R.id.cardFront);
        cardBack = findViewById(R.id.cardBack);
        etCardNumber = findViewById(R.id.etCardNumber);
        etCardHolder = findViewById(R.id.etCardHolder);
        etCardExpiry = findViewById(R.id.etCardExpiry);
        etCardCvv = findViewById(R.id.etCardCvv);
        tvCardNumber = findViewById(R.id.tvCardNumberResult);
        tvCardHolder = findViewById(R.id.tvCardHolderResult);
        tvCardExpiry = findViewById(R.id.tvCardExpiryResult);
        tvCardCvv = findViewById(R.id.tvCardCvvResult);
        btnPay = findViewById(R.id.btnPay);
    }

    static class SimpleTextWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override public void afterTextChanged(Editable s) {}
    }
}