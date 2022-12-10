package ca.uottawa.engineering.mealer.helpers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import ca.uottawa.engineering.mealer.classes.Meal;

public class MealHandler {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = "Chef-Handler";

    private DocumentReference mealRef;
    private Meal meal;

    public MealHandler() {
    }

    public MealHandler(String mealName) {
        db.collection("propMeals")
                .whereEqualTo("mealName", meal)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                meal = document.toObject(Meal.class);
                                mealRef = document.getReference();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void updateMeal() {
        mealRef.set(meal);
    }

    public Meal getMeal() {
        return meal;
    }

    public DocumentReference getMealRef() {
        return mealRef;
    }
}
