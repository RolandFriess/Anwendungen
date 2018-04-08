package roland.CalendarPrivacyFilter;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Horizontal extends AppCompatActivity {
    private int counter;
    public String[] calendartypes;
    private boolean[] checkedItems;
    private ListView listView;
    private ArrayList<String> calendarList = new ArrayList<>();
    private ArrayAdapter<String> filterlisteAdapter;
//    private Spinner spinn;
    private static final String[] projection = {CalendarContract.Calendars.CALENDAR_DISPLAY_NAME};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
//        spinn = findViewById(R.id.spinner);
        listView = findViewById(R.id.listViewhor);
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        ContentResolver resolver = getContentResolver();

        Cursor cur = resolver.query(uri,projection,null,null,null);
        calendartypes = new String[cur.getCount()];
        while(cur.moveToNext())
        {
            calendartypes[cur.getPosition()] = cur.getString(0);
        }
        checkedItems = new boolean[calendartypes.length];
        //Erstellen des Arrayadapters
        filterlisteAdapter = new ArrayAdapter<>(this,R.layout.list_item_horizontaler_filter,calendarList);
        listView.setAdapter(filterlisteAdapter);


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,calendartypes);
//        Spinner spinn = findViewById(R.id.spinner);
//        spinn.setAdapter(adapter);

        MainActivity.horizontalerFilter = new String[cur.getCount()];
        MainActivity.horizontalerFilter[0]= null;

    }
//    public void filterHinzufuegen(View view)
//
//    {
//        String hinzu = spinn.getSelectedItem().toString();
//        boolean enthalten = false;
//        Log.d("filterHinzufuegen","Methode gestartet");
//
//        for(int i = 0; i< calendartypes.length;i++) {
//            Log.d("filterHinzufuegen","for-Schleife" + i);
//
//            if (hinzu == horizontalerFilter[i]) {
//                Log.d("filterHinzufuegen","schon enthalten");
//                enthalten = true;
//            }
//        }
//            if(enthalten == false)
//            {
//                Log.d("filterHinzufuegen","nicht enthalten");
//                horizontalerFilter[counter] = hinzu;
//                counter++;
//                Log.d("filterHinzufuegen","Wert hinzugefuegt");
//                Toast.makeText(this,hinzu +" hinzugefügt",Toast.LENGTH_SHORT).show();
//
//
//
//
//
//
//            }
//            else
//            {
//                Toast.makeText(this,"Element schon enthalten",Toast.LENGTH_LONG).show();
//            }
//
//        }
    public void filterHinzufuegen(View view)
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Ausgewählte Kalender zum Filter hinzufügen");
        mBuilder.setMultiChoiceItems(calendartypes, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean checked) {
            if(checked)
            {
                if(!calendarList.contains(calendartypes[position])){
                    calendarList.add(calendartypes[position]);
                }
               else if(calendarList.contains(calendartypes[position])){
                    calendarList.remove(calendarList.indexOf(calendartypes[position]));
               }
            }

            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item ="";
                String[] horFilteruebergabe = new String[calendarList.size()];
                for(int i=0; i<calendarList.size();i++)
                {


                        horFilteruebergabe[i] = calendarList.get(i);





                }
                filterlisteAdapter.notifyDataSetChanged();
                MainActivity.horizontalerFilter = horFilteruebergabe;
               //Darstellung einfuegen

            }

        });
        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for(int i=0; i<checkedItems.length;i++)
                {
                    checkedItems[i] = false;
                    calendarList.clear();
                   //Darstellung leeren
                    MainActivity.horizontalerFilter = new String[1];
                }
                filterlisteAdapter.notifyDataSetChanged();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
    }

