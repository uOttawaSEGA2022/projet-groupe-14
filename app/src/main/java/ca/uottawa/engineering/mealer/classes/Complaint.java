package ca.uottawa.engineering.mealer.classes;

import com.google.android.gms.tasks.OnSuccessListener;
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
}
