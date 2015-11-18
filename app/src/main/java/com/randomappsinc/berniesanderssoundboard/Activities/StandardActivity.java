package com.randomappsinc.berniesanderssoundboard.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.randomappsinc.berniesanderssoundboard.R;
import com.randomappsinc.berniesanderssoundboard.Utils.FormUtils;

/**
 * Created by Alex Chiou on 11/17/15.
 */
public class StandardActivity extends AppCompatActivity {
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            FormUtils.closeKeyboard(this);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
