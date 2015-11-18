package com.randomappsinc.berniesanderssoundboard.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.randomappsinc.berniesanderssoundboard.R;
import com.randomappsinc.berniesanderssoundboard.Utils.PreferencesManager;
import com.randomappsinc.berniesanderssoundboard.Utils.SoundbiteManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alex Chiou on 11/17/15.
 */
public class SoundbitesAdapter extends BaseAdapter {
    private int yellow;
    private int darkGray;
    private Context context;
    private List<String> soundbites;

    public SoundbitesAdapter(Context context) {
        this.context = context;
        this.soundbites = SoundbiteManager.get().getAllSoundbites();
        this.yellow = context.getResources().getColor(R.color.yellow);
        this.darkGray = context.getResources().getColor(R.color.dark_gray);
    }

    @Override
    public int getCount() {
        return soundbites.size();
    }

    @Override
    public String getItem(int position) {
        return soundbites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class SoundbiteViewHolder {
        @Bind(R.id.soundbite_name) TextView soundbiteName;
        @Bind(R.id.favorite_icon) IconTextView favoriteIcon;

        public SoundbiteViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        SoundbiteViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.soundbite_cell, parent, false);
            holder = new SoundbiteViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (SoundbiteViewHolder) view.getTag();
        }

        holder.soundbiteName.setText(soundbites.get(position));
        if (PreferencesManager.get().isSoundbiteFavorited(soundbites.get(position))) {
            holder.favoriteIcon.setTextColor(yellow);
        }
        else {
            holder.favoriteIcon.setTextColor(darkGray);
        }

        return view;
    }
}
