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
import com.randomappsinc.berniesanderssoundboard.Utils.SoundbitesManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alex Chiou on 11/17/15.
 */
public class SoundbitesAdapter extends BaseAdapter {
    private Context context;
    private List<String> soundbites;
    private View noSoundbites;
    private boolean favoritesMode;
    private int yellow;
    private int darkGray;

    public SoundbitesAdapter(Context context, View noSoundbites) {
        this.context = context;
        this.soundbites = SoundbitesManager.get().getAllSoundbites();
        this.noSoundbites = noSoundbites;
        this.favoritesMode = false;
        this.yellow = context.getResources().getColor(R.color.yellow);
        this.darkGray = context.getResources().getColor(R.color.dark_gray);
        toggleNoSoundbites();
    }

    public void toggleNoSoundbites() {
        if (soundbites.isEmpty()) {
            noSoundbites.setVisibility(View.VISIBLE);
        }
        else {
            noSoundbites.setVisibility(View.GONE);
        }
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

    public void filterSoundbites(String searchInput, boolean favoritesMode) {
        this.favoritesMode = favoritesMode;
        soundbites = SoundbitesManager.get().getSoundbiteMatches(searchInput, favoritesMode);
        toggleNoSoundbites();
        notifyDataSetChanged();
    }

    public void removeSoundbite(int position) {
        soundbites.remove(position);
        notifyDataSetChanged();
        toggleNoSoundbites();
    }

    public class SoundbiteViewHolder {
        @Bind(R.id.soundbite_name) TextView soundbiteName;
        @Bind(R.id.favorite_icon) IconTextView favoriteIcon;

        public SoundbiteViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final SoundbiteViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.soundbite_cell, parent, false);
            holder = new SoundbiteViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (SoundbiteViewHolder) view.getTag();
        }

        final String soundbite = soundbites.get(position);
        holder.soundbiteName.setText(soundbite);
        if (PreferencesManager.get().isSoundbiteFavorited(soundbite)) {
            holder.favoriteIcon.setTextColor(yellow);
        }
        else {
            holder.favoriteIcon.setTextColor(darkGray);
        }
        holder.favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferencesManager.get().isSoundbiteFavorited(soundbite)) {
                    holder.favoriteIcon.setTextColor(darkGray);
                    PreferencesManager.get().unfavoriteSoundbite(soundbite);
                    if (favoritesMode) {
                        removeSoundbite(position);
                    }
                }
                else {
                    holder.favoriteIcon.setTextColor(yellow);
                    PreferencesManager.get().favoriteSoundbite(soundbite);
                }
            }
        });
        return view;
    }
}
