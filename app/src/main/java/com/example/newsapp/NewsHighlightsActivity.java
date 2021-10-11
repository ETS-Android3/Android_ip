package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsHighlightsActivity extends AppCompatActivity {

@BindView(R.id.newsButton) Button newsButton;
@BindView(R.id.channelView) ListView mListView;
private String[] channels = new String[] {"BBC News", "Bloomberg", "CBC", "ABC News", "ABC NEWS (AU)","Ary News","Ars Technica","ANSA.it","Al Jazeera English","Breitbart-news","Business-Insider","CBS News","CNN","Associated Press","Axios"};
private String[] highlights = new String[] {"Use BBC News for up-to-the-minute news, breaking news, video, audio and feature stories. BBC News provides trusted World and UK news as well as local and regional perspectives. Also entertainment, business, science, technology and health news.","Bloomberg delivers business and markets news, data, analysis, and video to the world, featuring stories from Businessweek and Bloomberg News.","CBC News is the division of the Canadian Broadcasting Corporation responsible for the news gathering and production of news programs on the corporation's English-language operations, namely CBC Television, CBC Radio, CBC News Network, and CBC.ca.","Your trusted source for breaking news, analysis, exclusive interviews, headlines, and videos at ABCNews.com.","ARY News is a Pakistani news channel committed to bring you up-to-the minute Pakistan news and featured stories from around Pakistan and all over the world.","The PC enthusiast's resource. Power users and the tools they love, without computing religion.","Australia's most trusted source of local, national and world news. Comprehensive, independent, in-depth analysis, the latest business, sport, weather and more.","Agenzia ANSA: ultime notizie, foto, video e approfondimenti su: cronaca, politica, economia, regioni, mondo, sport, calcio, cultura e tecnologia.","News, analysis from the Middle East and worldwide, multimedia and interactives, opinions, documentaries, podcasts, long reads and broadcast schedule.","Syndicated news and opinion website providing continuously updated headlines to top news and analysis sources.","Business Insider is a fast-growing business site with deep financial, media, tech, and other industry verticals. Launched in 2007, the site is now the largest business news site on the web.","CBS News: dedicated to providing the best in journalism under standards it pioneered at the dawn of radio and television and continue in the digital age.","View the latest news and breaking news today for U.S., world, weather, entertainment, politics and health at CNN","The AP delivers in-depth coverage on the international, politics, lifestyle, business, and entertainment news.","Axios are a new media company delivering vital, trustworthy news and analysis in the most efficient, illuminating and shareable ways possible."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newshighlights);
        ButterKnife.bind(this);

        NewsHighlightsArrayAdapter adapter = new NewsHighlightsArrayAdapter(this, android.R.layout.simple_list_item_1, channels, highlights);
        mListView.setAdapter(adapter);

        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsHighlightsActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });
    }
}