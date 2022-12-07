package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import ca.uottawa.engineering.mealer.adapter.CustomBaseAdapter;

public class Clientpage extends AppCompatActivity {
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientpage);
        listview = (ListView)findViewById(R.id.mealList);
        CustomBaseAdapter adapter = new CustomBaseAdapter(getApplicationContext());
        listview.setAdapter(adapter);
    }
}