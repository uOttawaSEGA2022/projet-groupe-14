package ca.uottawa.engineering.mealer.classes;

import java.util.Date;

public class Admin {

    private Complaint[] complaints;

    public Admin() {
        // get complaints from firebase
        // put in complaints array (or list)
    }

    public void getComplaint() {
        // return first complaint from list
    }

    public void dismiss(Complaint complaint) {
        // delete complaint from firebase and from Admin list
        removeFromList();
    }

    public void suspend(Complaint complaint, Date date) {
        complaint.suspend(date);
        removeFromList();
    }

    public void removeFromList() {};

}
