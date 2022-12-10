package ca.uottawa.engineering.mealer.classes;

import com.google.firebase.firestore.DocumentReference;

public class Order {

    private String chefName;
    private String clientName;
    private DocumentReference mealRef;
    private String mealName;
    private String state;

    public Order() {
    }

    public Order(String chefName, String clientName, DocumentReference mealRef, String mealName, String state) {
        this.chefName = chefName;
        this.clientName = clientName;
        this.mealName = mealName;
        this.state = state;
    }

    public String toString() {
        return String.format("%s: by %s for %s - %s", mealName, chefName, clientName, state);
    }

}
