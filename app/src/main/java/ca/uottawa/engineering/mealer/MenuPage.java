package ca.uottawa.engineering.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import ca.uottawa.engineering.mealer.classes.Meal;

public class MenuPage extends AppCompatActivity {

    private final String TAG = "menu-page";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<Meal> meals = new ArrayList<>();
    ArrayAdapter<Meal> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        retrieveMenu();

        listView = (ListView) findViewById(R.id.menulist);
        Log.i(TAG, String.valueOf(meals.size()));

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

    @Override
    public void onRestart() {
        super.onRestart();
        this.recreate();
    }

    public void retrieveMenu() {

        db.collection("users/" + mAuth.getUid() + "/menu/")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Meal meal = document.toObject(Meal.class);
                                meals.add(meal);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void switch_page(Meal meal) {
        Intent switchActivityIntent = new Intent(this, MealUi.class);
        switchActivityIntent.putExtra("meal", meal);

        startActivity(switchActivityIntent);
    }
}