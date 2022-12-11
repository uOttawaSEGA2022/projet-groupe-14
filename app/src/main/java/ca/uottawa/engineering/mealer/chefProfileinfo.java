package ca.uottawa.engineering.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import ca.uottawa.engineering.mealer.classes.Chef;
import ca.uottawa.engineering.mealer.classes.Meal;
import ca.uottawa.engineering.mealer.classes.Order;
import ca.uottawa.engineering.mealer.helpers.ChefHandler;
import ca.uottawa.engineering.mealer.helpers.ChefSingleton;

public class chefProfileinfo extends AppCompatActivity {
    TextView rating;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "chef_profile";
    private String chefname;
    private Chef cheff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_profileinfo);
        Bundle extras = getIntent().getExtras();
        chefname = (String) extras.get("chefname");
        getchef();

        rating = findViewById(R.id.Rate);
    }
    public  void onRate(View view){
        Intent switchActivityIntent = new Intent(this, ClientRatingsActivity.class);
        startActivity(switchActivityIntent);
    }
    public void getchef(){
        db.collection("users")
                .whereEqualTo("name",chefname)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();
                            Log.d(TAG, document.getQuery().toString());
                            for (QueryDocumentSnapshot documentt : task.getResult()){
                                Log.d(TAG, documentt.getId() + " => " + documentt.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}