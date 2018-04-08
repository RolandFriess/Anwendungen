package roland.CalendarPrivacyFilter;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static Boolean[] vertikalerFilter;
    private static boolean wirdUebernommen;
    public static String[] horizontalerFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialisierung falls Horizontal noch nicht initialisiert
        if(horizontalerFilter == null) {
            horizontalerFilter = new String[1];
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vertikalerFilter = new Boolean[]{
                false,
                //Filter aktiviert für TITLE
                false,
                //Filter aktiviert für Startzeit
                false,
                //Filter aktiviert für Endzeit
                false
                //Filter aktiviert für Event_Location
        };

    }


    public void filtern(View view) {
        Intent intent = new Intent(this, Filtern.class);
        startActivity(intent);

    }


    public void testen(View view) {
        Intent intent = new Intent(this, Test.class);
        startActivity(intent);
    }

    public static Cursor datenFiltern(Cursor cursor) {
        boolean horizontaleFilterungMoeglich = false;

        //Nachschauen, ob die Kalendar_ID mit übergeben wurde true falls es übergeben wurde
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            if (cursor.getColumnName(i).equals(CalendarContract.Events.CALENDAR_DISPLAY_NAME)) {
                horizontaleFilterungMoeglich = true;
                Log.d("datenFiltern","horizontaleFilterungmoeglich = true");
            }
        }


        Log.d("datenFiltern", "Horizontaler Filter moeglich" + horizontaleFilterungMoeglich);
        //Abrufen der Spaltennamen
        String[] columnNames = cursor.getColumnNames();
        //Erstellen eines neuen MatrixCursors mit den Spaltennamen
        MatrixCursor matrixCursorneu = new MatrixCursor(columnNames);
        Log.d("datenFiltern", "MatrixCursor erstellt" + matrixCursorneu.toString());


        //Cursor an erste Stelle setzen
        cursor.moveToFirst();
        //Überprüfen ob horizontale Filterung möglich ist
        if (Filtern.horizontalerFilterakt && horizontaleFilterungMoeglich) {
            // For-Schleife, die durch alle Reihen nacheinander durchgeht
            Log.d("datenFiltern", "Beginn Horizontale Filterung");
            cursor.move(-1);
            while(cursor.moveToNext()) {
                //Horizontale Filterung
                //Überprüfen ob Reihe übernommen wird

                wirdUebernommen = true;
               // Log.d("For Schleife", wirdUebernommen+ "in Durchlauf" + x);



                for (int y = 0; y < horizontalerFilter.length; y++) {
                    String cal_ID = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.CALENDAR_DISPLAY_NAME));
                    if (cal_ID.equals(horizontalerFilter[y])) {
                        Log.d("Wert der Cal_id",cal_ID);
                        Log.d("Wert des horizontalen Filters", "An Stelle: " + y + " Und Wert "+ horizontalerFilter[y]);
                        wirdUebernommen = false;
                    }
                }
                Log.d("datenFiltern", "Wird übernommen: " + wirdUebernommen +" in Durchlauf " + cursor.getPosition());
                if (wirdUebernommen) {
                    //Daten in neuen Cursor schreiben


                    Log.d("in if Schleife", "Durchlauf: " + cursor.getPosition() );
                    Object[] neueReihe = new Object[cursor.getColumnCount()];
                    Log.d("Vertikaler Filter","" + Filtern.vertikalerFilterakt);
                    for (int z = 0; z < cursor.getColumnCount(); z++) {

                        if (Filtern.vertikalerFilterakt) {
                            if (vertikalerFilter[0] == true && CalendarContract.Events.TITLE.equals(cursor.getColumnName(z)) ||
                                    vertikalerFilter[1] == true && CalendarContract.Events.DTSTART.equals(cursor.getColumnName(z)) ||
                                    vertikalerFilter[2] == true && CalendarContract.Events.DTEND.equals(cursor.getColumnName(z)) ||
                                    vertikalerFilter[3] == true && CalendarContract.Events.EVENT_LOCATION.equals(cursor.getColumnName(z))) {
                                neueReihe[z] = null;
                            } else {
                                String add = cursor.getString(z);
                                neueReihe[z] = add;
                            }
                        }

                    }
                    matrixCursorneu.addRow(neueReihe);

                }
                else
                {
                    wirdUebernommen = true;

                }


            }
        } else {
            for (int x = 0; x < cursor.getCount(); x++) {


                Object[] neueReihe = new Object[cursor.getColumnCount()];
                for (int z = 0; z < cursor.getColumnCount(); z++) {


                    if (Filtern.vertikalerFilterakt && vertikalerFilter[0] && CalendarContract.Events.TITLE.equals( cursor.getColumnName(z)) ||
                            vertikalerFilter[1] && CalendarContract.Events.DTSTART.equals( cursor.getColumnName(z)) ||
                            vertikalerFilter[2] && CalendarContract.Events.DTEND.equals( cursor.getColumnName(z)) ||
                            vertikalerFilter[3] && CalendarContract.Events.EVENT_LOCATION.equals( cursor.getColumnName(z))) {
                        neueReihe[z] = null;
                    } else {
                        String add = cursor.getString(z);
                        neueReihe[z] = add;
                    }
                }
                matrixCursorneu.addRow(neueReihe);

            }
        }
        Object[] nullReihe = new Object[cursor.getColumnCount()];
        for(int i = 0;i<cursor.getColumnCount();i++)
        {
            nullReihe[i] = null;
        }
        if(matrixCursorneu.getCount() == 0) {
            matrixCursorneu.addRow(nullReihe);
        }

        return matrixCursorneu;

    }
}
