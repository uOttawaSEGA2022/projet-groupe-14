package ca.uottawa.engineering.mealer.classes;

import java.util.Date;

public class Complaint {

    private final Date PERMANENT_SUSPENSION = new Date(1);

    private final Chef chef;

    public Complaint(Chef chef) {
        this.chef = chef;
    }

    public void suspend(Date date) {
        chef.setSuspension(date);
    }

    public void perm_suspend() {
        chef.setSuspension(PERMANENT_SUSPENSION);
    }

}
