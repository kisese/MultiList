package com.kisese.brayo.multilist;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Brayo on 4/26/2015.
 */
public class CustomAdapter extends ArrayAdapter {
    public static final int TYPE_QUESTION = 0;
    public static final int TYPE_ANSWER = 1;

    private String a, date;

    //private ListViewItem[] objects;
    ArrayList<ListViewItem> objects;

    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public int getItemViewType(int position) {
        return objects.get(position).getType();
    }
    public CustomAdapter(Context context, int resource, ArrayList<ListViewItem> objects) {
        super(context, resource, objects);

        this.objects = objects;

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int time = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);
        int am = c.get(Calendar.AM_PM);

        if(am == 1){
            a = "PM";
        }else{
            a = "AM";
        }

        if(min < 10){
            date = time + " : 0" + min + " " + a;
        }else {
            date = time + " : " + min  + " " + a;
        }

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        ListViewItem listViewItem = objects.get(position);
        int listViewItemType = getItemViewType(position);
        if (convertView == null) {
            if (listViewItemType == TYPE_QUESTION) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.question_text, null);
            }else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.answer_text, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.text);
            viewHolder = new ViewHolder(textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String textViewText = listViewItem.getText() + "<br/>" +
                "<br/><small><strong><b><font color=\"#ffffff\">"+date+"</font></b></strong></small>";

        viewHolder.getText().setText(Html.fromHtml(textViewText));
        return convertView;
    }

}

