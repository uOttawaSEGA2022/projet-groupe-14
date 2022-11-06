package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ca.uottawa.engineering.mealer.classes.Chef;
import ca.uottawa.engineering.mealer.classes.Complaint;

public class AdminPage extends AppCompatActivity {
    ListView listView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<Complaint> complaints = new ArrayList<>();
    ArrayAdapter<Complaint> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        retrieveComplaints();

        listView = (ListView) findViewById(R.id.list);
        Log.i("COMPLAINT", String.valueOf(complaints.size()));

        adapter = new ArrayAdapter<Complaint>
                (this, android.R.layout.simple_list_item_1, complaints);

        listView.setAdapter(adapter);
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
                                Complaint complaint = document.toObject(Complaint.class);
                                complaints.add(complaint);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}