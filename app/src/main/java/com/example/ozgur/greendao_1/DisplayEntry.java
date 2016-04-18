package com.example.ozgur.greendao_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DisplayEntry extends AppCompatActivity
{
    @Bind(R.id.diaryShowTitle)
    TextView title;
    @Bind(R.id.diaryShowDate)
    TextView date;
    @Bind(R.id.diaryShowContent)
    TextView content;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_entry);

        ButterKnife.bind(this);

        setSupportActionBar(this.toolbar);

        Intent intent = getIntent();
        if(intent != null){
            String title = intent.getStringExtra(Constants.TITLE_NAME);
            long date = intent.getLongExtra(Constants.DATE_NAME, 0L);
            String content = intent.getStringExtra(Constants.CONTENT_NAME);

            this.title.setText(title);

            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            Date resultdate = new Date(date);
            this.date.setText(sdf.format(resultdate));

            this.content.setText(content);
        }
    }

}
