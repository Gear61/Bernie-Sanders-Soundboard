package com.randomappsinc.berniesanderssoundboard.Models;

import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.randomappsinc.berniesanderssoundboard.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alex Chiou on 11/17/15.
 */
public class FontAwesomeViewHolder {
    @Bind(R.id.item_icon) public IconTextView itemIcon;
    @Bind(R.id.item_name) public TextView itemName;

    public FontAwesomeViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}

