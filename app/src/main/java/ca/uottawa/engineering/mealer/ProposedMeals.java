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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import ca.uottawa.engineering.mealer.classes.Complaint;
import ca.uottawa.engineering.mealer.classes.Meal;

public class ProposedMeals extends AppCompatActivity {

    private final String TAG = "proposed-meals";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<Meal> proposedMeals = new ArrayList<>();
    ArrayAdapter<Meal> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposed_meals_page);

        retrieveProposedMenu();

        listView = (ListView) findViewById(R.id.proposedMealsList);
        Log.i(TAG, String.valueOf(proposedMeals.size()));

        adapter = new ArrayAdapter<Meal>
                (this, android.R.layout.simple_list_item_1, proposedMeals);

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

    public void retrieveProposedMenu() {

        // restrict to current chef's meals?
        db.collection("propMeals")
                .whereEqualTo("chefName", chefHandler.getChef().getName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                // get meal from reference
                                DocumentReference mealRef = (DocumentReference) document.get("mealRef");
                                mealRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Log.d(TAG, documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                        Meal meal = documentSnapshot.toObject(Meal.class);
                                        proposedMeals.add(meal);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void switch_page(Meal meal) {
        Intent switchActivityIntent = new Intent(this, pruposedMealUi.class);
        switchActivityIntent.putExtra("meal", meal);

        startActivity(switchActivityIntent);
    }
}