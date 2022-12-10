package ca.uottawa.engineering.mealer.handlers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import ca.uottawa.engineering.mealer.classes.Chef;

public class ChefHandler {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = "Chef-Handler";

    private DocumentReference chefRef;
    private Chef chef;


    /**
     * Creates a chef handler instance without containing chef or chef ref.
     */
    public ChefHandler() {
    }

    /**
     * Creates a chef handler instance containing the Chef object and a reference to it in firebase.
     *
     * @param chefName
     */
    public ChefHandler(String chefName) {
        retrieveChef(chefName);
    }

    /**
     * Retrieves Chef and ChefRef given chef name.
     *
     * @param chefName
     */
    public void retrieveChef(String chefName) {
        db.collection("users")
                .whereEqualTo("name", chefName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                chef = document.toObject(Chef.class);
                                chefRef = document.getReference();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /**
     * Updates firebase chef with current chef object values.
     */
    public void updateChef() {
        chefRef.set(chef);
    }

    public Chef getChef() {
        return chef;
    }

    public DocumentReference getChefRef() {
        return chefRef;
    }
}
