package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import ca.uottawa.engineering.mealer.classes.Meal;

public class orderedlist extends AppCompatActivity {
    private final String TAG = "ordered-meals";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<String> meals = new ArrayList<String>();

    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderedlist);

        listView = (ListView) findViewById(R.id.orderedmealslist);
        Log.i(TAG, String.valueOf(meals.size()));

        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, meals);

        listView.setAdapter(adapter);
        listView.setClickable(true);
    }
}