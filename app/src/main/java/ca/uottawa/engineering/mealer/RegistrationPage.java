package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class RegistrationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
    }

    public void registerAsChef(View view) {
        Intent switchActivityIntent = new Intent(this, ChefRegistration.class);
        startActivity(switchActivityIntent);
    }

    public void registerAsClient(View view) {
        Intent switchActivityIntent = new Intent(this, ClientRegistration.class);
        startActivity(switchActivityIntent);
    }

}