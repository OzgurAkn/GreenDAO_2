package com.example.ozgur.greendao_1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.greenrobot.daoexample.Entry;

/**
 * Created by Ozgur on 4/04/2016.
 */
public class DiaryAdapter extends BaseAdapter
{
    private SQLiteDatabase database;
    private LayoutInflater inflater;
    private ArrayList<Entry> entries;

    public DiaryAdapter(SQLiteDatabase database, Context context)
    {
        this.database = database;
        inflater = LayoutInflater.from(context);
        entries = new ArrayList<Entry>();
    }

    public void setData(Cursor cursor)
    {
        if (cursor.moveToFirst())
        {
            do
            {
                String title = cursor.getString(cursor.getColumnIndex(Constants.TITLE_NAME));
                String content = cursor.getString(cursor.getColumnIndex(Constants.CONTENT_NAME));

                long rawDate = cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME));

                Entry entry = new Entry(null, title, rawDate, content);

                this.entries.add(entry);
            }
            while (cursor.moveToNext());
        }
    }

    public void setDataToNull()
    {
        this.entries.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return this.entries.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.entries.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        View view = convertView;
        if ((view == null) || view.getTag() == null)
        {
            //The ViewHolder has not yet been made and must be made
            view = this.inflater.inflate(R.layout.listitem_main, null);

            holder = new ViewHolder();
            holder.setTitle((TextView) view.findViewById(R.id.name));
            holder.setDate((TextView) view.findViewById(R.id.datetext));

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        //Set the required information
        holder.setEntry((Entry) getItem(position));
        holder.getTitle().setText(holder.getEntry().getTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(holder.getEntry().getDate());
        holder.getDate().setText(sdf.format(resultdate));

        view.setTag(holder);
        return view;
    }

    private class ViewHolder
    {
        private Entry entry;
        private TextView title;
        private TextView date;

        public Entry getEntry()
        {
            return entry;
        }

        public void setEntry(Entry entry)
        {
            this.entry = entry;
        }

        public TextView getDate()
        {
            return date;
        }

        public void setDate(TextView date)
        {
            this.date = date;
        }

        public TextView getTitle()
        {
            return title;
        }

        public void setTitle(TextView title)
        {
            this.title = title;
        }
    }
}
