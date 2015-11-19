package com.randomappsinc.berniesanderssoundboard.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.randomappsinc.berniesanderssoundboard.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 11/18/15.
 */
public class SourcesAdapter extends BaseAdapter {
    private Context context;
    private String[] titles;
    private int[] thumbnails;

    public SourcesAdapter(Context context) {
        this.context = context;
        this.titles = context.getResources().getStringArray(R.array.sources_titles);
        this.thumbnails = new int[]{R.drawable.president_we_need, R.drawable.in_180_seconds, R.drawable.establishment};
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public String getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class SourceViewHolder {
        @Bind(R.id.source_title) TextView title;
        @Bind(R.id.source_thumbnail) ImageView thumbnail;

        public SourceViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        SourceViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.source_item_cell, parent, false);
            holder = new SourceViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (SourceViewHolder) view.getTag();
        }

        holder.title.setText(titles[position]);
        holder.thumbnail.setImageResource(thumbnails[position]);

        return view;
    }
}
