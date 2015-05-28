package com.kisese.brayo.multilist;

import android.widget.TextView;

/**
 * Created by Brayo on 4/26/2015.
 */
public class ViewHolder {
    TextView text;

    public ViewHolder(TextView text) {
        this.text = text;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }
}
