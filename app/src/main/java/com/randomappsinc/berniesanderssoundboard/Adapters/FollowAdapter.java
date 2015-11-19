package com.randomappsinc.berniesanderssoundboard.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.randomappsinc.berniesanderssoundboard.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 11/18/15.
 */
public class FollowAdapter extends BaseAdapter {
    private Context context;
    private String[] titles;
    private String[] icons;
    private int[] colors;

    public FollowAdapter(Context context) {
        this.context = context;
        this.titles = context.getResources().getStringArray(R.array.follow_titles);
        this.icons = context.getResources().getStringArray(R.array.follow_icons);
        this.colors = context.getResources().getIntArray(R.array.follow_icon_colors);
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

    public class FollowViewHolder {
        @Bind(R.id.follow_icon) IconTextView icon;
        @Bind(R.id.follow_title) TextView title;

        public FollowViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        FollowViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.follow_item_cell, parent, false);
            holder = new FollowViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (FollowViewHolder) view.getTag();
        }

        holder.title.setText(titles[position]);
        holder.icon.setText(icons[position]);
        holder.icon.setTextColor(colors[position]);

        return view;
    }
}
