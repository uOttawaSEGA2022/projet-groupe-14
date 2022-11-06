package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

import ca.uottawa.engineering.mealer.classes.Complaint;

public class AdminPage extends AppCompatActivity {
    ListView listView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<Complaint> complaints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        retrieveComplaints();
        listView = (ListView) findViewById(R.id.list);
        //initialiser le custombaseadaptor avec la list de complaint comme argument
        //listview.setAdapter(custombaseadaptor) pour configurer la list a afficher tous les complains dans l ordre
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
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map complaintData = document.getData();
                                Timestamp timestamp = (Timestamp) complaintData.get("date_created");

                                DocumentReference documentReference = (DocumentReference) complaintData.get("chef");
                                Complaint complaint = new Complaint(timestamp.toDate(), documentReference);
                                complaints.add(complaint);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}