package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ca.uottawa.engineering.mealer.classes.Complaint;
import ca.uottawa.engineering.mealer.classes.Meal;

public class MenuPage extends AppCompatActivity {
    private ArrayList<Meal> meals = new ArrayList<>();
    ArrayAdapter<Meal> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        listView = (ListView) findViewById(R.id.menulist);
        Log.i("COMPLAINT", String.valueOf(meals.size()));

        adapter = new ArrayAdapter<Meal>
                (this, android.R.layout.simple_list_item_1, meals);

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal meal= (Meal) adapterView.getItemAtPosition(i);

                switch_page(meal);
            }
        });
    }
    private void switch_page(Meal meal) {
        Intent switchActivityIntent = new Intent(this, MealUi.class);
        switchActivityIntent.putExtra("meal", meals);

        startActivity(switchActivityIntent);
    }
}