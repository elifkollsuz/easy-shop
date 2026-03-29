package com.elif.main.easyshop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ActivityProfile extends AppCompatActivity {

    private TextView profileAvatar, profileEmail, tvChangePassword, tvChangeEmail, tvLogout;
    private EditText etProfileName;
    private Button btnUpdateProfile;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        profileAvatar = findViewById(R.id.profileAvatar);
        profileEmail = findViewById(R.id.profileEmail);
        etProfileName = findViewById(R.id.etProfileName);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        tvChangePassword = findViewById(R.id.tvChangePassword);
        tvChangeEmail = findViewById(R.id.tvChangeEmail);
        tvLogout = findViewById(R.id.tvLogout);

        if (user != null) {
            profileEmail.setText(user.getEmail());
            etProfileName.setText(user.getDisplayName());

            String initial = user.getEmail().substring(0, 1).toUpperCase();
            profileAvatar.setText(initial);
        }

        btnUpdateProfile.setOnClickListener(v -> {
            String newName = etProfileName.getText().toString();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newName)
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Profil güncellendi!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        tvChangePassword.setOnClickListener(v -> {
            mAuth.sendPasswordResetEmail(user.getEmail()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Şifre sıfırlama maili gönderildi!", Toast.LENGTH_LONG).show();
                }
            });
        });

        tvChangeEmail.setOnClickListener(v -> {
            Toast.makeText(this, "Email değişikliği için lütfen destek ile iletişime geçin veya yeni hesap açın.", Toast.LENGTH_LONG).show();
        });

        tvLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(ActivityProfile.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}