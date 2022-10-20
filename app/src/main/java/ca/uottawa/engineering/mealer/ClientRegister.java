package ca.uottawa.engineering.mealer;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import ca.uottawa.engineering.mealer.classes.Client;

public class ClientRegister extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String role = "client";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);
    }

    private void register() {
        mAuth = FirebaseAuth.getInstance();

        // TODO: get information about client

        Client client = new Client();
        db.collection("users").document(mAuth.getUid()).set(client);
    }
}