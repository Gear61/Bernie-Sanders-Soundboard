package com.randomappsinc.berniesanderssoundboard.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.randomappsinc.berniesanderssoundboard.Models.SoundbiteViewHolder;
import com.randomappsinc.berniesanderssoundboard.R;
import com.randomappsinc.berniesanderssoundboard.Utils.SoundbiteManager;

import java.util.List;

/**
 * Created by Alex Chiou on 11/17/15.
 */
public class SoundbitesAdapter extends BaseAdapter {
    private Context context;
    private List<String> soundbites;
    private View noSoundbites;

    public SoundbitesAdapter(Context context, View noSoundbites) {
        this.context = context;
        this.soundbites = SoundbiteManager.get().getAllSoundbites();
        this.noSoundbites = noSoundbites;
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
        soundbites = SoundbiteManager.get().getSoundbiteMatches(searchInput, favoritesMode);
        toggleNoSoundbites();
        notifyDataSetChanged();
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
        holder.toggleFavoriteIcon();
        return view;
    }
}
