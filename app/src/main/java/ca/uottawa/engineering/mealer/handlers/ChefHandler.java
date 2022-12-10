package ca.uottawa.engineering.mealer.handlers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import ca.uottawa.engineering.mealer.classes.Chef;

public class ChefHandler {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = "Chef-Handler";

    private DocumentReference chefRef;
    private Chef chef;

    /**
     * Creates a chef handler instance containing the Chef object and a reference to it in firebase.
     * @param chefName
     */
    public ChefHandler(String chefName) {
        db.collection("complaints")
                .whereEqualTo("name", chefName) // will get self
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

    public Chef getChef() {
        return chef;
    }

    public DocumentReference getChefRef() {
        return chefRef;
    }
}
