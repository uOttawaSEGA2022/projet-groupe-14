package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Date;
import java.util.GregorianCalendar;

import ca.uottawa.engineering.mealer.classes.Complaint;

public class ComplaintUI extends AppCompatActivity {

    Complaint complaint;

    private DatePicker datePicker;

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

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.setMinDate(new Date().getTime());
    }

    // Suspend button
    public void suspend(View view) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Date date = new GregorianCalendar(year, month, day).getTime();
        complaint.suspend(date);
        go_back();
    }

    // permSuspend button
    public void permSuspend(View view) {
        complaint.perm_suspend();
        go_back();
    }

    // permSuspend button
    public void dismissComplaint(View view) {
        complaint.dismiss();
        go_back();
    }

    // Go back to adminPage
    public void go_back(){
        onBackPressed();
    }
}