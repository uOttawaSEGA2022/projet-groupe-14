package ca.uottawa.engineering.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ca.uottawa.engineering.mealer.classes.Meal;

public class pruposedMealUi extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final String TAG = "proposed-meal-UI";

    private Meal meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruposed_meal_ui);

        Bundle extras = getIntent().getExtras();
        meal = (Meal) extras.get("meal");

        TextView mealName = (TextView) findViewById(R.id.nomDuProposedMeal);
        mealName.setText(meal.getName());
    }

    public void deleteProposedMeal(View view) {
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
                                        Meal pMeal = documentSnapshot.toObject(Meal.class);
                                        if (pMeal.getName().equals(meal.getName()) && pMeal.getChefName().equals(meal.getChefName())) {
                                            document.getReference().delete();

                                            Context context = getApplicationContext();
                                            CharSequence text = "Deleted Meal from Proposed Menu!";
                                            int duration = Toast.LENGTH_LONG;

                                            Toast toast = Toast.makeText(context, text, duration);
                                            toast.show();

                                            try {
                                                TimeUnit.SECONDS.sleep(1); // Sometimes it goes too fast and the database doesn't update fast enough
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            onBackPressed();
                                        }
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
