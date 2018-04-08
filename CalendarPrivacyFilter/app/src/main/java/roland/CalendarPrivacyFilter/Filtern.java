package roland.CalendarPrivacyFilter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class Filtern extends AppCompatActivity {
    public static boolean horizontalerFilterakt;
    public static boolean vertikalerFilterakt;


    public static String[] vertikalerFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtern);
        horizontalerFilterakt = false;
        vertikalerFilterakt = false;
    }
    public void horizontalFiltern(View view)
    {
        Intent intent = new Intent(this, Horizontal.class);
        startActivity(intent);
    }
    public void vertikalFiltern(View view)
    {
        Intent intent = new Intent(this, Vertikal.class);
        startActivity(intent);
    }
    public void filterubernehmen(View view)
    {
        CheckBox hor =  findViewById(R.id.horizontalBox);
        CheckBox ver = findViewById(R.id.VertikalBox);
        horizontalerFilterakt = hor.isChecked();
        vertikalerFilterakt = ver.isChecked();
        Toast.makeText(this,"Filter übernommen",Toast.LENGTH_LONG).show();
    }
}
