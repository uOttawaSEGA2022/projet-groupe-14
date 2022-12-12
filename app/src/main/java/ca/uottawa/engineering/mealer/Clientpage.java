package ca.uottawa.engineering.mealer;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import ca.uottawa.engineering.mealer.adapter.CustomBaseAdapter;
import ca.uottawa.engineering.mealer.classes.Chef;
import ca.uottawa.engineering.mealer.classes.Meal;
import ca.uottawa.engineering.mealer.helpers.ChefHandler;
import ca.uottawa.engineering.mealer.helpers.ChefSingleton;

public class Clientpage extends AppCompatActivity {
    ListView listview;
    private final String TAG = "Clientpage";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<Meal> meals = new ArrayList<>();
    private CustomBaseAdapter adapter;

    private String chefName;
    private Chef chef;
    private  boolean issuspended;

    SearchView searchbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientpage);
        searchbar = findViewById(R.id.search_bar);
        retrieveProposedMenu();
        Log.d(TAG,String.valueOf(meals.size()));
        listview = (ListView)findViewById(R.id.mealList);
        adapter = new CustomBaseAdapter(getApplicationContext(),meals);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal meal= (Meal) adapterView.getItemAtPosition(i);
                meal = meals.get(i);
                switch_page(meal);
            }
        });

        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)){
                    Log.d(TAG, "empty");
                    adapter.filter("");
                    listview.clearTextFilter();
                }
                else {
                    adapter.filter(s);

                }
                return true;
            }
        });

    }
    public void retrieveProposedMenu() {

        db.collection("propMeals")
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
                                        chefName = meal.getChefName();
                                        getChefInfo();
                                        if(issuspended==false){
                                            meals.add(meal);
                                        }
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
        Intent switchActivityIntent = new Intent(this, ClientMeal.class);
        switchActivityIntent.putExtra("meal", meal);
        startActivity(switchActivityIntent);
    }
    public  void Onordered(View view){
        Intent switchActivityIntent = new Intent(this, ClientOrderedList.class);
        startActivity(switchActivityIntent);
    }

    public void getChefInfo() {
        db.collection("users")
                .whereEqualTo("name", chefName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();
                            Log.d(TAG, document.getQuery().toString());
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d(TAG, documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                chef = documentSnapshot.toObject(Chef.class);
                                issuspended = chef.isSuspended();
                                Log.d(TAG, String.valueOf(chef.isSuspended()));


                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}