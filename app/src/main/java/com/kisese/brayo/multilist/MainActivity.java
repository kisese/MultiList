package com.kisese.brayo.multilist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import java.util.Iterator;
import java.util.List;

import storage.DBOpenHelper;
import storage.MessagesTableAdapter;


public class MainActivity extends ActionBarActivity {

    MessagesTableAdapter adapter;
    DBOpenHelper helper_ob;

    private ListView listView;
    private EditText chatText;
    private ImageButton sendButton;
    int index = 0;
    private CustomAdapter customAdapter;
    ArrayList<ListViewItem> items;

    private String a, date;
    String answer, question;

    JSONParser jsonParser = new JSONParser();
    private JSONArray mMatch = null;
    private ArrayList<HashMap<String, String>> chatList;
    //private String CHAT_URL = "http://shule.enezaeducation.com/gateway/in/?key=Kuber5246&from=254722833440&text=home&action=incoming";
    private String CHAT_URL = "http://shule.enezaeducation.com/gateway/in/";
    private JSONArray mComments = null;
    private JSONArray mCommentsB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_main);

        adapter = new MessagesTableAdapter(this);

        new ProcessActivity().execute("Home");




        listView = (ListView) findViewById(R.id.listView1);
        chatText = (EditText)findViewById(R.id.chatText);
        sendButton = (ImageButton)findViewById(R.id.buttonSend);


        //final ListViewItem[] items = new ListViewItem[40];
       // items.add(items.size(), new ListViewItem(question, CustomAdapter.TYPE_QUESTION, false));

        items = new ArrayList<ListViewItem>();

        int size = adapter.getCount();
        Cursor cursor = adapter.queryAll();




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
                answer = chatText.getText().toString();
                if(items.size() < 1) {
                    items.add(0, new ListViewItem(answer, CustomAdapter.TYPE_ANSWER, false));
                    scrollMyList();
                    answer = chatText.getText().toString();
                    adapter.insertDetails(date, answer, 0, CustomAdapter.TYPE_ANSWER, false);
                    new ProcessActivity().execute(answer);
                    scrollMyList();
                    Toast.makeText(MainActivity.this, answer, Toast.LENGTH_LONG).show();
                    chatText.setText("");
                }else{
                    items.add(items.size(), new ListViewItem(answer, CustomAdapter.TYPE_ANSWER, false));
                    scrollMyList();
                    answer = chatText.getText().toString();
                    adapter.insertDetails(date, answer, 0, CustomAdapter.TYPE_ANSWER, false);
                    new ProcessActivity().execute(answer);
                    scrollMyList();
                    Toast.makeText(MainActivity.this, answer, Toast.LENGTH_LONG).show();
                    chatText.setText("");
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

        }


        @Override
        protected String doInBackground(String... parameters) {

                answer = parameters[0];

                try {
                    /*
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("key", "Kuber5246"));
                    params.add(new BasicNameValuePair("from", "254722833440"));
                    params.add(new BasicNameValuePair("text", question));
                    params.add(new BasicNameValuePair("action", "incoming"));
                    //http://shule.enezaeducation.com/gateway/in/?key=Kuber5246&from=254722833440&text=home&action=incoming

                    JSONObject json = jsonParser.makeHttpRequest(CHAT_URL, "GET", params);
                    */
                  //  JSONArray msg = (JSONArray)json.get("events");
                    chatList = new ArrayList<HashMap<String, String>>();
                    //JSONParser jParser = new JSONParser();
                    JSONObject jsonObject = jsonParser.getJSONFromUrl(CHAT_URL
                            + "?key=Kuber5246&from=254722833440&text="+answer+"&action=incoming");

                    mComments = jsonObject.getJSONArray("events");
                    for (int i = 0; i < mComments.length(); i++) {
                        JSONObject c = mComments.getJSONObject(i);

                        //String id = c.getString("id");
                        ///String to = c.getString("to");
                        mCommentsB= c.getJSONArray("messages");

                        for (int o = 0; o < mCommentsB.length(); o++) {
                            JSONObject b = mCommentsB.getJSONObject(i);

                            question = b.getString("message");
                            question = String.format(question.replace("\n", "<br/>"));
                        }
                       // question = mComments.getJSONObject(1).toString();
                        
                    }



                    //question =  chatList.get(2).toString();



                }catch (JSONException e){
                    e.printStackTrace();
                }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
          //  pDialog.dismiss();
            //Toast.makeText(MainActivity.this, question, Toast.LENGTH_LONG).show();
            items.add(items.size(), new ListViewItem(question,
                    CustomAdapter.TYPE_QUESTION, false));

            adapter.insertDetails(date, question, 0, CustomAdapter.TYPE_QUESTION, false);

            scrollMyList();

            super.onPostExecute(s);
        }
    }

}