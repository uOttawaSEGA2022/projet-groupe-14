package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class chefProfileinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_profileinfo);
    }
    public  void onRate(View view){
        Intent switchActivityIntent = new Intent(this, ClientRatingsActivity.class);
        startActivity(switchActivityIntent);
    }
}