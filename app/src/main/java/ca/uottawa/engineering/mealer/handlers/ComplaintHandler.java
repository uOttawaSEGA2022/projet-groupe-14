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

import java.util.Date;

import ca.uottawa.engineering.mealer.classes.Complaint;

/**
 * ComplaintHandler class helps manage a complaint by keeping a reference to a complaint object
 * and a reference to its firebase object. Contains useful methods.
 */
public class ComplaintHandler {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = "Complaint-Handler";

    private DocumentReference complaintRef;
    private Complaint complaint;

    /**
     * Creates complaint handler without complaint or reference.
     */
    public ComplaintHandler() {

    }

    /**
     * Creates complaint for chef using name and creates the object and its reference.
     *
     * @param chefName
     */
    public void createComplaint(String chefName) {
        ChefHandler chefHandler = new ChefHandler(chefName);

        DocumentReference chefRef = chefHandler.getChefRef();
        complaint = new Complaint(chefName, chefRef);

        db.collection("complaints").document().set(complaint);
        retrieveComplaint(complaint.getDate_created());
    }

    /**
     * Retrieves a complaint from firebase based on date.
     *
     * @param date
     */
    public void retrieveComplaint(Date date) {
        db.collection("complaints")
                .whereEqualTo("date_created", date)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                complaint = document.toObject(Complaint.class);
                                complaintRef = document.getReference();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
