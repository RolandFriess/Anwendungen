package roland.CalendarPrivacyFilter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class Vertikal extends AppCompatActivity {
    public static Boolean[] vertikalerFilter;
    private CheckBox checktitle;
    private CheckBox checkStart;
    private CheckBox checkEnd;
    private CheckBox checkLoc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertikal);
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

        public void filterAnwenden(View view)

    {
        checktitle =  findViewById(R.id.checktitle);
        checkStart =  findViewById(R.id.startTime);
        checkEnd =  findViewById(R.id.endTime);
        checkLoc = findViewById(R.id.event_Location);
       if(checktitle.isChecked())
       {
           vertikalerFilter[0]= true;
       }
        if (checkStart.isChecked()) {
           vertikalerFilter[1]= true;
        }
        if(checkEnd.isChecked()){
           vertikalerFilter[2] = true;

        }
        if(checkLoc.isChecked()){
            vertikalerFilter[3] = true;
        }
        MainActivity.vertikalerFilter =vertikalerFilter;
        Toast.makeText(this,"Änderungen übernommen", Toast.LENGTH_LONG).show();
    }

}
