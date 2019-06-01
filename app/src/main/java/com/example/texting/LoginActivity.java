package com.example.texting;

//import android.annotation.SuppressLint;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class LoginActivity extends AppCompatActivity {
    private static final String USER_NAME = "USER_NAME";
    String HAS_NAME = "HAS_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final EditText username = (EditText) findViewById(R.id.username);
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sp.getBoolean(HAS_NAME, false)){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            this.finish();
            startActivity(intent);
        }

        setContentView(R.layout.activity_login);
        final Button continueButton = (Button) findViewById(R.id.name);
        continueButton.setVisibility(View.GONE);
        continueButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText username_input = (EditText) findViewById(R.id.username);

                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                editor.putBoolean(HAS_NAME, true);
                editor.putString(USER_NAME, username_input.getText().toString());
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.finish();
                startActivity(intent);

            }
        });
//        @SuppressLint("CutPasteId")
        EditText user_input = (EditText) findViewById(R.id.username);
        user_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    continueButton.setVisibility(View.VISIBLE);
                } else {
                    continueButton.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        Button skip = (Button) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.finish();

                startActivity(intent);
            }
        });

    }

}
