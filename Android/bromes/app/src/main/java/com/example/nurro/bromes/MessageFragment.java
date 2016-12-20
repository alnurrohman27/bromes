package com.example.nurro.bromes;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by nurro on 12/18/2016.
 */

public class MessageFragment extends Fragment {
    View myView;
    String[] listContact = null;
    String[] listEvent = null;
    String[] listIDEvent = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        myView = inflater.inflate(R.layout.message, container, false);
        getListContact();
        sendMessage();
        return myView;
    }

    private void getListContact(){
        String link = "http://192.168.43.10/bromes/php/android_search.php?id=contacts";

        try {
            URLConnection url = new URL(link).openConnection();

            HttpURLConnection con = (HttpURLConnection)url;
            //con.setRequestMethod("GET");

            InputStream inputStream = new BufferedInputStream(con.getInputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();

            String line ="";
            while((line = br.readLine()) != null){
                sb.append(line+"\n");
            }
            inputStream.close();
            String result = sb.toString();

            JSONArray jsonArray = new JSONArray(result);
            listContact = new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++){
                if(jsonArray.getJSONObject(i).getString("privileges").equals("message"))
                    listContact[i] = jsonArray.getJSONObject(i).getString("no");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void sendMessage(){
        String link = "http://192.168.43.10/bromes/php/android_search.php?id=events";
        final LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.messageLayout);
        try {
            URLConnection url = new URL(link).openConnection();

            HttpURLConnection con = (HttpURLConnection)url;
            //con.setRequestMethod("GET");

            InputStream inputStream = new BufferedInputStream(con.getInputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();

            String line ="";
            while((line = br.readLine()) != null){
                sb.append(line+"\n");
            }
            inputStream.close();
            String result = sb.toString();
            Log.d("Event", result);

            JSONArray jsonArray = new JSONArray(result);
            Log.d("JSON", jsonArray.toString());
            listEvent = new String[jsonArray.length()];
            listIDEvent = new String[jsonArray.length()];

            for(int i = 0; i < jsonArray.length(); i++){
                String name = jsonArray.getJSONObject(i).getString("name");
                String date = jsonArray.getJSONObject(i).getString("date");
                String location = jsonArray.getJSONObject(i).getString("location");
                listEvent[i] = "Acara: " + name + "\nWaktu: " + date + "\nTempat: " + location;
                listIDEvent[i] = jsonArray.getJSONObject(i).getString("id");

                Log.d("Event", listEvent[i]);

                // create the layout params that will be used to define how your
                // button will be displayed
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMarginStart(30);
                params.setMarginEnd(30);
                params.setMargins(0, 30, 0, 30);

                // Create LinearLayout
                LinearLayout ll = new LinearLayout(this.getActivity());
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setBackgroundColor(Color.WHITE);
                ll.setLayoutParams(params);

                // Create TextView Event Name
                TextView viewName = new TextView(this.getActivity());
                viewName.setText(listEvent[i]);
                ll.addView(viewName, params);

                linearLayout.addView(ll);
            }
            SmsManager smsManager = SmsManager.getDefault();
            for(int i = 0; i < listContact.length; i++){
                for(int j = 0; j < listEvent.length; j++){
                    try{
                        smsManager.sendTextMessage(listContact[i], null, listEvent[j], null, null);
                        sendStatusMessage(listContact[i], listIDEvent[j]);
                        Toast.makeText(myView.getContext(), "Sms Sent " + listContact[i], Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                        Toast.makeText(myView.getContext(), "Sms Failed. Try Again " + listContact[i], Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void sendStatusMessage(String contact, String idEvent){
        String link = "http://192.168.43.10/bromes/php/android_insert.php?id=add_status_message&event="+idEvent+"&no="+contact;
        Log.d("Link", link);
        try {
            URLConnection url = new URL(link).openConnection();
            HttpURLConnection con = (HttpURLConnection)url;
            InputStream inputStream = new BufferedInputStream(con.getInputStream());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
