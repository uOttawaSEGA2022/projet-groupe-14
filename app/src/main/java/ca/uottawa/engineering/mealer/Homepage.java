package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import android.widget.TextView;

import ca.uottawa.engineering.mealer.classes.Chef;

public class Homepage extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        mAuth = FirebaseAuth.getInstance();

        db.collection("chefs").add(chef);

        TextView hText = findViewById(R.id.homepageText);
        hText.setText(String.format("You are signed in as%s", mAuth.getCurrentUser().getEmail()));
    }

}