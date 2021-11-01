package com.example.newsapp.ui;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
private DatabaseReference mSearchedSourceReference;
    private ValueEventListener mSearchedSourceReferenceListener;

    @BindView(R.id.exploreNewsButton) Button mExploreNewsButton;
   @BindView(R.id.countryEditText) EditText mCountryEditText;
   @BindView(R.id.savedNewsButton) Button mSavedNewsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSearchedSourceReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_SOURCE);


        mSearchedSourceReference.addValueEventListener(new ValueEventListener() { //attach listener

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //something changed!
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue().toString();
                    Log.d("Locations updated", "location: " + location); //log
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { //update UI here if error occurred.

            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mSavedNewsButton.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user !=null){
                    getSupportActionBar().setTitle("Welcome," + user.getDisplayName() +"!");
                } else {

                }

            }
        };



        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mExploreNewsButton.setOnClickListener(this);
    }
            @Override
            public void onClick(View view) {
                if (view == mExploreNewsButton){
                String source = mCountryEditText.getText().toString();
                saveLocationToFirebase(source);
                if (!(source).equals("")){
                    addToSharedPreferences(source);
                }
                Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
                intent.putExtra("source", source);
                startActivity(intent);
//                Toast.makeText(MainActivity.this, source, Toast.LENGTH_SHORT).show();
            }

                if (view ==mSavedNewsButton) {
                    Intent intent = new Intent(MainActivity.this, SavedNewsListActivity.class);
                    startActivity(intent);
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

    @Override
    public void onStart(){
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
    @Override
    public void  onStop(){
        super.onStop();
        if (authStateListener !=null){
            auth.removeAuthStateListener(authStateListener);
        }
    }

    private void addToSharedPreferences(String source) {
        mEditor.putString(Constants.PREFERENCES_SOURCE_KEY,source).apply();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSearchedSourceReference.removeEventListener(mSearchedSourceReferenceListener);
    }
    }
