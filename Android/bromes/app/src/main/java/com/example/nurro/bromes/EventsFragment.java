package com.example.nurro.bromes;

import android.Manifest;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by nurro on 12/18/2016.
 */

public class EventsFragment extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        myView = inflater.inflate(R.layout.events, container, false);
        ServerConnection();
        return myView;
    }

    private void ServerConnection() {
        String link = "http://10.234.68.127/bromes/php/android_search.php?id=events";

        try {
            URLConnection url = new URL(link).openConnection();

            HttpURLConnection con = (HttpURLConnection) url;
            //con.setRequestMethod("GET");

            InputStream inputStream = new BufferedInputStream(con.getInputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            inputStream.close();
            String result = sb.toString();

            InitUI(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void InitUI(String result) {
        final LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.eventsLayout);

        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                // create the layout params that will be used to define how your
                // button will be displayed
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMarginStart(30);
                params.setMarginEnd(30);
                params.setMargins(0, 30, 0, 0);

                int id = Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));

                final String name = jsonArray.getJSONObject(i).getString("name");
                String date = jsonArray.getJSONObject(i).getString("date");
                String note = jsonArray.getJSONObject(i).getString("note");
                String location = jsonArray.getJSONObject(i).getString("location");
                int day = Integer.parseInt(jsonArray.getJSONObject(i).getString("day"));
                int month = Integer.parseInt(jsonArray.getJSONObject(i).getString("month"));
                int year = Integer.parseInt(jsonArray.getJSONObject(i).getString("year"));
                int hours = Integer.parseInt(jsonArray.getJSONObject(i).getString("hours"));
                int min = Integer.parseInt(jsonArray.getJSONObject(i).getString("min"));


                // Create LinearLayout
                LinearLayout ll = new LinearLayout(this.getActivity());
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setBackgroundColor(Color.WHITE);
                ll.setLayoutParams(params);

                // Create TextView Event Name
                TextView viewName = new TextView(this.getActivity());
                viewName.setText(name + " (" + location + ")");
                ll.addView(viewName, params);

                // Create TextView Event Date
                TextView viewDate = new TextView(this.getActivity());
                viewDate.setText(date);
                ll.addView(viewDate, params);

                // Create TextView Event Note
                TextView viewNote = new TextView(this.getActivity());
                viewNote.setText(note);
                ll.addView(viewNote, params);

                // Create Button
                final Button btn = new Button(this.getActivity());
                // Give button an ID
                btn.setId(id);
                btn.setText("Tampilkan");
                // set the layoutParams on the button
                RelativeLayout.LayoutParams paramsRelative = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                btn.setLayoutParams(paramsRelative);

                // Set click listener for button
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(myView.getContext(), "Acara: " + name, Toast.LENGTH_SHORT).show();
                    }
                });

                //Add button to LinearLayout
                ll.addView(btn);
                //Add button to LinearLayout defined in XML

                linearLayout.addView(ll);

                boolean answer = AddingEventToCalendar(id, name, location, note, day, month, year, hours, min);
                if (answer)
                    Toast.makeText(myView.getContext(), "Acara: " + name + " berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(myView.getContext(), "Acara: " + name + " sudah ada", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private boolean AddingEventToCalendar(int id, String name, String location, String note, int day, int month, int year, int hours, int min) {
        Calendar beginCal = Calendar.getInstance();
        beginCal.set(year, month-1, day, hours, min);
        long startTime = beginCal.getTimeInMillis();

        Calendar endCal = Calendar.getInstance();
        endCal.set(year, month-1, day, hours + 2, min);
        long endTime = endCal.getTimeInMillis();

        Log.d("Date", year+"-"+month+"-"+day);

//        Intent calIntent = new Intent(Intent.ACTION_INSERT);
//        calIntent.setType("vnd.android.cursor.item/event");
//        calIntent.putExtra(CalendarContract.Events.TITLE, name);
//        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
//        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, note);
//
//        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
//        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
//        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
//        startActivity(calIntent);

        ContentResolver cr = this.getActivity().getContentResolver();
        ContentValues values = new ContentValues();

        long longID = (long) id;
        values.put(CalendarContract.Events.DTSTART, startTime);
        values.put(CalendarContract.Events.DTEND, endTime);
        values.put(CalendarContract.Events.TITLE, name);
        values.put(CalendarContract.Events.DESCRIPTION, note);
        values.put(CalendarContract.Events.CALENDAR_ID, longID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());
        values.put(CalendarContract.Events.HAS_ALARM, 1);


        Log.d("Version", String.valueOf(Build.VERSION_CODES.M));

        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.WRITE_CALENDAR}, 1);
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.READ_CALENDAR}, 1);
        }

        if(ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

            try {
                longID = Integer.parseInt(uri.getLastPathSegment());
                cr = this.getActivity().getContentResolver();
                values = new ContentValues();
                values.put(CalendarContract.Reminders.EVENT_ID, longID);
                values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                values.put(CalendarContract.Reminders.MINUTES, 5);

                cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            return true;

        }



        return false;
    }
}
