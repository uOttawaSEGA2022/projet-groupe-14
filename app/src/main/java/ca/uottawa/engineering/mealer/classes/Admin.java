package ca.uottawa.engineering.mealer.classes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class Admin {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<Complaint> complaints = new ArrayList<>();

    public Admin() {
    }

    public void retrieveComplaints() {
        final String TAG = "COMPLAINT";

        db.collection("complaints")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.get("chef").toString();
                                Log.i(TAG, name);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void addComplaint(Complaint complaint) {
        complaints.add(complaint);
    }

    public Complaint getComplaint() {
        return complaints.get(0);
    }

    public void dismiss(Complaint complaint) {
        removeFromList(complaint);
    }

    public void suspend(Complaint complaint, Date date) {
        complaint.suspend(date);
        removeFromList(complaint);
    }

    public void removeFromList(Complaint complaint) {
        complaints.remove(complaint);
        // also delete from firebase
    };

}
