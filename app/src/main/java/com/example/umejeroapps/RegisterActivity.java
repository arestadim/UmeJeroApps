package com.example.umejeroapps;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText mUsername, mPassword, mNama, mTelepon;
    Button mRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUsername = findViewById(R.id.etUsernameRegister);
        mPassword = findViewById(R.id.etPasswordRegister);
        mNama = findViewById(R.id.etNamaRegister);
        mTelepon = findViewById(R.id.etTeleponRegister);
        mRegister = findViewById(R.id.btnRegister);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

}
