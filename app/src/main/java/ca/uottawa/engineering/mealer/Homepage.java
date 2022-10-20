package ca.uottawa.engineering.mealer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Homepage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        mAuth = FirebaseAuth.getInstance();
        String user = mAuth.getUid();

        TextView hText = (TextView) findViewById(R.id.homepageText);
        hText.setText(String.format("You are signed in as%s", user));
    }

}