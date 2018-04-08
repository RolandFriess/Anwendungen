package roland.CalendarPrivacyFilter;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Test extends AppCompatActivity {
    private static int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 0;
    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Events.TITLE,
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events._ID,
            CalendarContract.Events.CALENDAR_DISPLAY_NAME,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.EVENT_LOCATION,

    };
    //private String selection = CalendarContract.Events.CALENDAR_ID +"= '3'";
    //private String selection = null;
    private String selection = CalendarContract.Events.CALENDAR_ID +"= '3' OR "+ CalendarContract.Events.CALENDAR_ID +"='4'";

    private Cursor vorFilterung;
    private Cursor nachFilterung;
    private ArrayList<String> listAusgabe;
    private ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
    public void testen(View view)

    {

        Uri uri = CalendarContract.Events.CONTENT_URI;
        listAusgabe=   new ArrayList<String>();

        ContentResolver contentResolver = getContentResolver();
        //Daten abrufen
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALENDAR},
                    MY_PERMISSIONS_REQUEST_READ_CALENDAR);
        }
        else {
            Cursor cur = contentResolver.query(uri, EVENT_PROJECTION, selection, null, null);
            vorFilterung = cur;
            Log.d("Anzahl Reihen original", " " + cur.getCount() + " ");
            Log.d("Anzahl Spalten original", " " + cur.getColumnCount() + " ");
            listAusgabe.add("Anzahl Reihen vor Filterung: " + cur.getCount());
            listAusgabe.add("Anzahl Spalten vor Filterung: "+ cur.getColumnCount());

            while (cur.moveToNext()) {
                for (int i = 0; i < cur.getColumnCount(); i++) {
                    Log.d("Erste Ausgabe " + cur.getColumnName(i), cur.getString(i) + "Reihennummer" + cur.getPosition());

                }
            }


            cur.moveToFirst();
            for (int i = 0; i < 2;i++)
            {
                for(int x = 0;x< cur.getColumnCount();x++)
                {
                    listAusgabe.add("Vor Filterung " + cur.getColumnName(x) + ": "+ cur.getString(x));
                }

            }
          Cursor cursorneu = MainActivity.datenFiltern(cur);
            Log.d("Anzahl Reihen", " " + cursorneu.getCount() + " ");
            Log.d("Anzahl Spalten", " " + cursorneu.getColumnCount() + " ");
            listAusgabe.add("Anzahl Reihen nach Filterung: "+ cursorneu.getCount());
            listAusgabe.add("Anzahl Spalten nach Filterung: " + cursorneu.getColumnCount());

            cursorneu.move(-1);
            while (cursorneu.moveToNext()) {
                for (int i = 0; i < cursorneu.getColumnCount(); i++) {
                    Log.d("Zweite Ausgabe " + cursorneu.getColumnName(i) + ":", cursorneu.getString(i)+ " Reihennummer " + cursorneu.getPosition());

                }
            }
            cursorneu.moveToFirst();
            for (int i = 0; i < 2;i++)
            {
                for(int x = 0;x< cursorneu.getColumnCount();x++)
                {
                    listAusgabe.add("Nach Filterung " + cursorneu.getColumnName(x) + ": "+ cursorneu.getString(x));
                }

            }
            mAdapter = new ArrayAdapter(this,R.layout.list_item_horizontaler_filter,listAusgabe);
            ListView listView = findViewById(R.id.listViewtest);
            listView.setAdapter(mAdapter);

        }
        if (MainActivity.horizontalerFilter.length > 0) {
            for(int x = 0;x<MainActivity.horizontalerFilter.length;x++)
            {
                Log.d("horizontalerFilter", MainActivity.horizontalerFilter[x] +"An Stelle " + x);
            }
        }
        else{
            Log.d("horizontalerFilter", "horizontaler Filter leer");
        }
    }

}
