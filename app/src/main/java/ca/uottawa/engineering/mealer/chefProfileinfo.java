package ca.uottawa.engineering.mealer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private Chef chef;
    private DocumentReference chefRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_profileinfo);

        Bundle extras = getIntent().getExtras();
        chefName = (String) extras.get("chefname");
        getChefInfo();

        rateText = findViewById(R.id.RateText);
    }
    public  void onRate(View view){
        Intent switchActivityIntent = new Intent(this, ClientRatingsActivity.class);
        startActivity(switchActivityIntent);
    }
    public void getchef(){
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