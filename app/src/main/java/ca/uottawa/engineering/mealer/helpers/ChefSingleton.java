package ca.uottawa.engineering.mealer.helpers;

import com.google.firebase.auth.FirebaseAuth;

public final class ChefSingleton {

    private static ChefHandler chefHandler;
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private ChefSingleton() {
    }

    ;

    public static ChefHandler getInstance() {
        if (chefHandler == null) {
            chefHandler = new ChefHandler(mAuth);
        }

        return chefHandler;
    }
}
