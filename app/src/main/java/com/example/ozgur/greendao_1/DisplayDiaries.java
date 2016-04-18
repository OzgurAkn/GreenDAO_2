package com.example.ozgur.greendao_1;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.daoexample.DaoMaster;
import de.greenrobot.daoexample.DaoSession;
import de.greenrobot.daoexample.Entry;
import de.greenrobot.daoexample.EntryDao;

public class DisplayDiaries extends ListActivity
{
    @Bind(R.id.list_count)
    TextView textView;

    private DiaryAdapter adapter;

    private SQLiteDatabase database;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private EntryDao entryDao;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_diaries);

        ButterKnife.bind(this);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        this.database = helper.getWritableDatabase();
        daoMaster = new DaoMaster(this.database);
        daoSession = this.daoMaster.newSession();
        entryDao = this.daoSession.getEntryDao();

        this.cursor = this.database.query(this.entryDao.getTablename(), this.entryDao.getAllColumns(), null, null, null, null, null);

        this.adapter = new DiaryAdapter(this.database, this);
        this.adapter.setData(this.cursor);
        this.setListAdapter(this.adapter);

        textView.setText("Count: " + this.adapter.getCount());

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Entry entry = (Entry) adapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), DisplayEntry.class);
                intent.putExtra(Constants.TITLE_NAME, entry.getTitle());
                intent.putExtra(Constants.DATE_NAME, entry.getDate());
                intent.putExtra(Constants.CONTENT_NAME, entry.getContent());
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DisplayDiaries.this.addEntries();
            }
        });
    }

    private void addEntries()
    {
        for (int i = 0; i < 1000; i++)
        {
            Entry entry = new Entry(null, Constants.SAMPLE_TITLE, System.currentTimeMillis(), Constants.SAMPLE_CONTENT);
            this.entryDao.insert(entry);
        }

        this.cursor = this.database.query(this.entryDao.getTablename(), this.entryDao.getAllColumns(), null, null, null, null, null);

        this.adapter.setData(this.cursor);

        CharSequence text = "Added 50 entries";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();

        startActivity(new Intent(this, DisplayDiaries.class));
        finish();
    }
}
