package ca.uottawa.engineering.mealer.classes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class Complaint {

    private final Date PERM_SUSPENSION = new Date(1);

    private Date date_created;
    private String chefName;
    private DocumentReference chefRef;

    public Complaint() {
    }

    public Complaint(Date date_created, String chefName, DocumentReference chefRef) {
        this.date_created = date_created;
        this.chefName = chefName;
        this.chefRef = chefRef;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public DocumentReference getChefRef() {
        return chefRef;
    }

    public void setChefRef(DocumentReference chefRef) {
        this.chefRef = chefRef;
    }

    public void perm_suspend() {
        updateChef(PERM_SUSPENSION);
    }

    public void suspend(Date date) {
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

    public String toString() {
        return String.format("%s: %s", chefName, date_created);
    }
}
