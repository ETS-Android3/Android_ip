package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class NewsActivity extends AppCompatActivity {
@BindView(R.id.headlines) ListView mHeadlines;
@BindView(R.id.moreNewsbutton) Button moreNewsButton;
private String[] headlines = new String[] {"Corona Virus 19 Pandemic ,Vaccine and treatment", "16 die as plane crashes carrying parachutists crashes", "Austrian Chancellor quits after prosecutors raid his office","With some 'more open to conspiracy theories,'vaccination rates of English Premier League players remain shrouded in mystery"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

//        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,headlines);
//        mHeadlines.setAdapter(adapter);
        Intent intent = getIntent();
        String country = intent.getStringExtra("country");

        NewsApi client = NewsClient.getClient();
        Call<NewsSearchResponse> call = client.getNews(country,"news");
        moreNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(NewsActivity.this, "Wait for More in the next morning", Toast.LENGTH_SHORT).show();
            }
        });
    }
}