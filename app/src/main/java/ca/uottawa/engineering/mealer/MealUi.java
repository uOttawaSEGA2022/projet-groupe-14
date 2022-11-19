package ca.uottawa.engineering.mealer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ca.uottawa.engineering.mealer.classes.Meal;

public class MealUi extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private final String TAG = "meal-UI";

    Meal meal;
    DocumentReference mealRef;
    private ArrayList<Meal> proposedMeals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_ui);

        Bundle extras = getIntent().getExtras();
        meal = (Meal) extras.get("meal");

        TextView mealName = (TextView) findViewById(R.id.nomDuMeal);
        mealName.setText(meal.getName());

        // get reference to meal
        db.collection("users/" +mAuth.getUid() + "/menu/")
                .whereEqualTo("name", meal.getName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                mealRef = document.getReference();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        // get proposed menu list to compare
        retrieveProposedMenu();

    }

    public void deleteMeal(View view) throws InterruptedException {
        for (Meal pMeal: proposedMeals) {
            if (pMeal.equals(meal)) {
                Context context = getApplicationContext();
                CharSequence text = "Meal is in proposed menu! Cannot delete.";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }
        }

        db.collection("users/" + mAuth.getUid() + "/menu/")
                .whereEqualTo("name", meal.getName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                document.getReference().delete();

                                Context context = getApplicationContext();
                                CharSequence text = "Deleted Meal from Menu!";
                                int duration = Toast.LENGTH_LONG;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        TimeUnit.SECONDS.sleep(1); // Sometimes it goes too fast and the database doesn't update fast enough
        onBackPressed();
    }

    public void addToProposedMeals(View view) throws InterruptedException {
        Map<String, Object> pMeal = new HashMap<>();
        pMeal.put("chefName", meal.getChefName());
        pMeal.put("mealRef", mealRef);

        db.collection("propMeals").document()
                .set(pMeal)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Context context = getApplicationContext();
                        CharSequence text = "Added meal to proposed meals!";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        onBackPressed();
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
                                        proposedMeals.add(meal);
                                    }
                                });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

}