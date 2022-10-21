package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import ca.uottawa.engineering.mealer.classes.Chef;

public class ChefRegistration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_registration);
    }

    private void register() {
        mAuth = FirebaseAuth.getInstance();

        // TODO: get information about chef

        Chef chef = new Chef();
        db.collection("users").document(mAuth.getUid()).set(chef);
    }

}