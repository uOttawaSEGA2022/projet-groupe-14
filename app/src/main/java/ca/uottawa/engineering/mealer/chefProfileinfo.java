package ca.uottawa.engineering.mealer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import ca.uottawa.engineering.mealer.classes.Chef;
import ca.uottawa.engineering.mealer.helpers.ComplaintHandler;

public class chefProfileinfo extends AppCompatActivity {
    TextView rateText;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "chef_profile";
    private String chefName;
    private TextView chefprofilename;
    private Chef chef;
    private DocumentReference chefRef;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_profileinfo);

        Bundle extras = getIntent().getExtras();
        chefName = (String) extras.get("chefname");
        getChefInfo();

        rateText = findViewById(R.id.RateText);
        spinner = findViewById(R.id.ratingSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ratings, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        chefprofilename = (TextView) findViewById(R.id.cheffprofilename);
    }

    public void onRate(View view) {
        int rating = Integer.parseInt(spinner.getSelectedItem().toString());
        chef.addReview(rating);
        chefRef.set(chef);

        Context context = getApplicationContext();
        CharSequence text = "Thank you for rating!";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void complain(View view) {
        ComplaintHandler complaintHandler = new ComplaintHandler();
        complaintHandler.createComplaint(chefName, chefRef);

        Context context = getApplicationContext();
        CharSequence text = "Added complaint to chef!";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void getChefInfo() {
        db.collection("users")
                .whereEqualTo("name", chefName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();
                            Log.d(TAG, document.getQuery().toString());
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d(TAG, documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                chef = documentSnapshot.toObject(Chef.class);
                                chefRef = documentSnapshot.getReference();
                                chefprofilename.setText(chef.getName());

                                String rating;
                                double ratingNumber = chef.getReview();
                                if (ratingNumber == 0) {
                                    rating = "No Reviews";
                                } else {
                                    rating = String.format("%.1f out of 5 stars", ratingNumber);
                                }

                                rateText.setText(rating);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}