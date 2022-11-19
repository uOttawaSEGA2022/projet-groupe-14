package ca.uottawa.engineering.mealer.classes;

import java.util.ArrayList;
import java.util.Date;

public class Chef extends User {

    private ArrayList<Meal> menu;

    private String cheque;
    private String description;

    // TODO: garbage system, must fix
    // if suspension == 0, no ban
    // if suspension == 1, permanent ban
    // else, banned until date
    private Date suspension;

    /**
     * Empty constructor used for Firebase. (DON'T USE, USE FULL CONSTRUCTOR!).
     */
    public Chef() {
    };

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

        // Get meal list from firebase
    }

    public void addMeal(String name, String mealType, String cuisineType, String ingredients, String allergies, String cost, String desc,String chefname) {
        Meal meal = new Meal(name, cuisineType, ingredients, allergies, cost, desc,chefname);
        menu.add(meal);
    };

    public void addToProposed(Meal meal) {};

    // meal must not be in the proposed meals list
    public void deleteMealFromMenu(Meal meal) {};


    public void deleteFromProposed(Meal meal) {};

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
