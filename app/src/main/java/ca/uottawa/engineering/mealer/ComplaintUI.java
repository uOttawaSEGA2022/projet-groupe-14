package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import ca.uottawa.engineering.mealer.classes.Complaint;

public class ComplaintUI extends AppCompatActivity {

    Complaint complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_ui);

        Bundle extras = getIntent().getExtras();
        complaint = (Complaint) extras.get("complaint");

        TextView dateCreated = (TextView) findViewById(R.id.dateCreatedTextView);
        dateCreated.setText(complaint.getDate_created().toString());

        TextView chefName = (TextView) findViewById(R.id.chefNameTextView);
        chefName.setText(complaint.getChefName());
    }


}