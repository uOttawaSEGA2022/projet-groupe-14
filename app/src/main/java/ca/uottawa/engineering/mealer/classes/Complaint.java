package ca.uottawa.engineering.mealer.classes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

public class Complaint {

    private final Date PERMANENT_SUSPENSION = new Date(1);

    private Date date_created;
    private DocumentReference chefRef;
    private Chef chef;

    public Complaint(Date date_created, DocumentReference chefRef) {
        this.date_created = date_created;
        this.chefRef = chefRef;

        chefRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Chef chef = documentSnapshot.toObject(Chef.class);
                setChef(chef);
            }
        });    }

    public Chef getChef() {
        return chef;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }

    public void perm_suspend() {
        chef.setSuspension(PERMANENT_SUSPENSION);
        updateChef(PERMANENT_SUSPENSION);
    }

    public void suspend(Date date) {
        chef.setSuspension(date);
        updateChef(date);
    }

    public void updateChef(Date date) {
        final String TAG = "UPDATE";
        Timestamp timestamp = new Timestamp(date);

        chefRef.update("suspension", timestamp)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }
}
