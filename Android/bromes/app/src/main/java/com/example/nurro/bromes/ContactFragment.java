package com.example.nurro.bromes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by nurro on 12/18/2016.
 */

public class ContactFragment extends Fragment{
    View myView;
    ListView myList;
    ArrayAdapter<String> adapter;
    InputStream inputStream = null;
    String link = "http://10.234.68.127/bromes/php/android_search.php?id=contacts";
    String line = null;
    String result = null;
    String[] data;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        myView = inflater.inflate(R.layout.contacts, container, false);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        getData();

        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, data);

        myList.setAdapter(adapter);

        return myView;
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_contact, menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        FragmentManager fragmentManager = getFragmentManager();
        switch (item.getItemId()) {
            case R.id.action_add_contact:
                fragmentManager.beginTransaction()
                        .replace(R.id.contacts, new AddContactFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
    }
    private void getData(){
        myList = (ListView) myView.findViewById(R.id.contact_list);
        try {
            URLConnection url = new URL(link).openConnection();

            HttpURLConnection con = (HttpURLConnection)url;
            //con.setRequestMethod("GET");

            inputStream = new BufferedInputStream(con.getInputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();

            while((line = br.readLine()) != null){
                sb.append(line+"\n");
            }
            inputStream.close();
            result = sb.toString();

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        try {
            JSONArray jsonArray = new JSONArray(result);
            data = new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++){
                data[i] = jsonArray.getJSONObject(i).getString("name");
                data[i] += "\n\t"+jsonArray.getJSONObject(i).getString("no");
            }
        }

        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}