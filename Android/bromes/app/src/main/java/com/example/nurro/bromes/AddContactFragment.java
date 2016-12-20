package com.example.nurro.bromes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class AddContactFragment extends Fragment{
    View myView;
    Button saveButton;
    String no;
    String name;
    String email;
    String address;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        myView = inflater.inflate(R.layout.add_contact, container, false);
        initUI();
        return myView;
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_add_contact, menu);
    }
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
    }

    public void initUI(){
        saveButton = (Button) myView.findViewById(R.id.saveNewContactButton);
        saveButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textViewNo = (TextView) getActivity().findViewById(R.id.addNumber);
                TextView textViewName = (TextView) getActivity().findViewById(R.id.addName);
                TextView textViewEmail = (TextView) getActivity().findViewById(R.id.addEmail);
                TextView textViewAddress = (TextView) getActivity().findViewById(R.id.addAddress);

                no = textViewNo.getText().toString();
                name = textViewName.getText().toString();
                email = textViewEmail.getText().toString();
                address = textViewAddress.getText().toString();

                if (no.equals("") || name.equals("")) {
                    Toast.makeText(v.getContext(), "Data kurang lengkap", Toast.LENGTH_SHORT).show();
                } else {
                    String link = "http://192.168.43.10/bromes/php/android_insert.php?id=add_contact&no="+no+
                            "&name="+name+"&email="+email+"&address="+address;
                    Log.d("Link", link.toString());
                    boolean result = addContact(link);
                    if(result){
                        onBackPressed();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.contacts, new ContactFragment())
                                .commit();
                    }
                }
            }
        });
    }

    public boolean addContact(String link){
        boolean result = false;
        try{
            URLConnection url = new URL(link).openConnection();
            HttpURLConnection con = (HttpURLConnection)url;
            InputStream inputStream = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            int res = Integer.parseInt(br.readLine());

            if(res == 1){
                Toast.makeText(this.getActivity(), "Data berhasil dimasukkan", Toast.LENGTH_SHORT).show();
                result = true;
            }
            else
                Toast.makeText(this.getActivity(), "Data gagal dimasukkan", Toast.LENGTH_SHORT).show();


        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
