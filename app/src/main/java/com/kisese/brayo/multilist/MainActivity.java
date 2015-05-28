package com.kisese.brayo.multilist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {


    private ListView listView;
    private EditText chatText;
    private ImageButton sendButton;
    int index = 0;
    private CustomAdapter customAdapter;
    ArrayList<ListViewItem> items;

    private String a, date;
    String question, answer;

    JSONParser jsonParser = new JSONParser();
    private JSONArray mMatch = null;
    private ArrayList<HashMap<String, String>> chatList;
    //private String CHAT_URL = "http://shule.enezaeducation.com/gateway/in/?key=Kuber5246&from=254722833440&text=home&action=incoming";
    private String CHAT_URL = "http://shule.enezaeducation.com/gateway/in/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_main); // here, you can create a single layout with a listview

        listView = (ListView) findViewById(R.id.listView1);
        chatText = (EditText)findViewById(R.id.chatText);
        sendButton = (ImageButton)findViewById(R.id.buttonSend);


        //final ListViewItem[] items = new ListViewItem[40];
        items = new ArrayList<ListViewItem>();

        //items.add(0, new ListViewItem("White ", CustomAdapter.TYPE_QUESTION));
        //items.add(1, new ListViewItem("Black ", CustomAdapter.TYPE_ANSWER));


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



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = chatText.getText().toString();
                if(items.size() < 1) {
                    items.add(0,  new ListViewItem(question, CustomAdapter.TYPE_QUESTION, false));
                    scrollMyList();
                    question = chatText.getText().toString();
                    chatText.setText("");
                    new ProcessActivity().execute(question);
                    scrollMyList();
                }else{
                    items.add(items.size(),  new ListViewItem(question, CustomAdapter.TYPE_QUESTION, false));
                    scrollMyList();
                    chatText.setText("");
                    question = chatText.getText().toString();
                    new ProcessActivity().execute(question);
                    scrollMyList();
                }
            }
        });


        customAdapter = new CustomAdapter(this, R.id.text, items);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), items.get(i).getText(), Toast.LENGTH_SHORT).show();
            }
        });


        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6495ED")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name) + "</font"));
        getSupportActionBar().getThemedContext();
        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    public void scrollMyList(){
        listView.post(new Runnable() {
            @Override
            public void run() {
                listView.setSelection(customAdapter.getCount()-1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public String printQuestion(String ques, String date){
        // String title = "<font color=\"#FFFFFF\"></font>";
        // String meta = "<br/><small><font color=\"#FFFFFF\">time and date</font><small>";
        String textViewText = ques + "<br/>" +
                "<br/><small><strong><b><font color=\"#696969\">"+date+"</font></b></strong></small>";
        String answer = Html.fromHtml(textViewText).toString();

        return answer;
    }


    public class ProcessActivity  extends AsyncTask<String,Void,String> {

        private ProgressDialog pDialog;


        protected void onPreExecute(){
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Processing...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... parameters) {
            String question = parameters[0];

                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("key", "Kuber5246"));
                    params.add(new BasicNameValuePair("from", "254722833440"));
                    params.add(new BasicNameValuePair("text", question));
                    params.add(new BasicNameValuePair("action", "incoming"));

                    JSONObject json = jsonParser.makeHttpRequest(CHAT_URL, "GET", params);

                    answer = json.getString("messagej");


                }catch (JSONException e){
                    e.printStackTrace();
                }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();

            items.add(items.size(), new ListViewItem(answer,
                    CustomAdapter.TYPE_ANSWER, false));
            super.onPostExecute(s);
        }
    }

}