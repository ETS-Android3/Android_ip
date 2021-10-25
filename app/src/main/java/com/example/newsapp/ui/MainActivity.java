package com.example.newsapp.ui;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsapp.Constants;
import com.example.newsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

//    private SharedPreferences mSharedPreferences;
//    private SharedPreferences.Editor mEditor;
//
private DatabaseReference mSearchedSourceReference;
    @BindView(R.id.exploreNewsButton) Button mExploreNewsButton;
   @BindView(R.id.countryEditText) EditText mCountryEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSearchedSourceReference = FirebaseDatabase
                .getInstance()
        .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_SOURCE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();

        mExploreNewsButton.setOnClickListener(this);
    }
            @Override
            public void onClick(View view) {
                if (view == mExploreNewsButton){
                String source = mCountryEditText.getText().toString();
                saveLocationToFirebase(source);
//                if (!(source).equals("")){
//                    addToSharedPreferences(source);
//                }
                Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
                intent.putExtra("source", source);
                startActivity(intent);
//                Toast.makeText(MainActivity.this, source, Toast.LENGTH_SHORT).show();
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id== R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    public void saveLocationToFirebase(String source) {
        mSearchedSourceReference.push().setValue(source);
    }

//    private void addToSharedPreferences(String source) {
//        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY,source).apply();
//    }
    }
