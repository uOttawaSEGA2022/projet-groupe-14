package ca.uottawa.engineering.mealer.classes;

import java.util.ArrayList;
import java.util.Date;

public class Chef extends User {

    private ArrayList<Meal> menu;

    private String cheque;
    private String description;

    private int reviewTotal;
    private int reviewCount;

    private Date suspension;

    /**
     * Empty constructor used for Firebase. (DON'T USE, USE FULL CONSTRUCTOR!).
     */
    public Chef() {
    }

    ;

    /**
     * Constructor for Chef
     * @param nickname Nickname of chef
     * @param name Name of chef
     * @param email Email address of chef
     * @param address Pick-up address of chef
     * @param cheque Cheque information
     * @param desc Description of chef
     */
    public Chef(String nickname, String name, String email, String address, String cheque,
                String desc) {
        super(nickname, name, email, address, "chef"); // hardcoded chef role
        this.cheque = cheque;
        this.description = desc;
        this.suspension = new Date(0);
        this.reviewTotal = 0;
        this.reviewCount = 0;
    }

    /**
     * Constructor for Chef
     *
     * @param nickname    Nickname of chef
     * @param name        Name of chef
     * @param email       Email address of chef
     * @param address     Pick-up address of chef
     * @param cheque      Cheque information
     * @param desc        Description of chef
     * @param reviewTotal Total review value
     * @param reviewCount Total reviews
     */
    public Chef(String nickname, String name, String email, String address, String cheque,
                String desc, int reviewTotal, int reviewCount) {
        super(nickname, name, email, address, "chef"); // hardcoded chef role
        this.cheque = cheque;
        this.description = desc;
        this.suspension = new Date(0);
        this.reviewTotal = reviewTotal;
        this.reviewCount = reviewCount;
    }

    public void addReview(int val) {
        reviewTotal += val;
        reviewCount++;
    }

    // Check if banned
    public boolean isSuspended() {
        return getSuspension().getTime() > new Date().getTime();
    }

    // TODO: see if it's possible to replace this with an image
    public String getCheque() {
        return cheque;
    }

    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getSuspension() {
        return suspension;
    }

    public void setSuspension(Date date) {
        this.suspension = date;
    }

}
