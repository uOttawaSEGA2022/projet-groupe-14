package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import ca.uottawa.engineering.mealer.classes.Chef;
import ca.uottawa.engineering.mealer.classes.Client;

public class ChefRegistration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String role = "chef";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_registration);
    }

    public void register(View view) {
        mAuth = FirebaseAuth.getInstance();
        EditText name = (EditText)findViewById(R.id.NicknameEdit);
        EditText lname = (EditText)findViewById(R.id.NameEdit);
        EditText email = (EditText)findViewById(R.id.EmailEdit);
        EditText adress = (EditText)findViewById(R.id.AdressEdit);
        EditText description = (EditText)findViewById(R.id.DescriptionEdit);
        EditText cheque = (EditText)findViewById(R.id.informationchequeEdit);
        Chef chef = new Chef(getstring(name),getstring(lname),getstring(email),getstring(adress),"Chef",Integer.parseInt(getstring(cheque)),getstring(description));
        db.collection("users").document(mAuth.getUid()).set(chef);
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
    public String getstring(EditText e){
        return e.getText().toString();
    }

}