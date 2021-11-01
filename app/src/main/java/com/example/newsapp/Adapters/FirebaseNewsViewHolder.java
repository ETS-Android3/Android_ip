package com.example.newsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Constants;
import com.example.newsapp.Models.Article;
import com.example.newsapp.R;
import com.example.newsapp.ui.NewDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;


public class FirebaseNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View view;
    Context context;


    public FirebaseNewsViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        context = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindNewsList(Article newsList){
        ImageView mImageLabel = (ImageView) view.findViewById(R.id.imageView);
        TextView mNameTextView = (TextView) view.findViewById(R.id.nameTextView);
        TextView mTitleTextView = (TextView) view.findViewById(R.id.nameTextView);
        TextView mAuthorTextView = (TextView) view.findViewById(R.id.authorTextView);
        TextView mUrlTextView = (TextView) view.findViewById(R.id.urlTextView);
        TextView mContentTextView = (TextView) view.findViewById(R.id.contentTextView);


        Picasso.get().load(newsList.getUrlToImage()).into(mImageLabel);

        mNameTextView.setText(newsList.getSource().getName());
        mTitleTextView.setText(newsList.getTitle());
        mAuthorTextView.setText(newsList.getAuthor());
        mUrlTextView.setText(newsList.getUrl());
        mContentTextView.setText(newsList.getContent());
    }

    @Override
    public void onClick(View view) {

        final ArrayList<Article> newsList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SEARCHED_SOURCE);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    newsList.add(snapshot.getValue(Article.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(context, NewDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("restaurants", Parcels.wrap(newsList));

                context.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
