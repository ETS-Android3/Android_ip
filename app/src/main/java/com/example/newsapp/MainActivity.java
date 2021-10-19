package com.example.newsapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

   @BindView(R.id.exploreNewsButton) Button mExploreNewsButton;
   @BindView(R.id.countryEditText) EditText mCountryEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mExploreNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String country = mCountryEditText.getText().toString();
                Log.d(TAG, country);
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("country", country);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Country", Toast.LENGTH_SHORT).show();
            }
        });
    }
}