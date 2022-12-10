package ca.uottawa.engineering.mealer.helpers;

import com.google.firebase.auth.FirebaseAuth;

public final class ClientSingleton {

    private static ClientHandler clientHandler;
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private ClientSingleton() {
    }

    ;

    public static ClientHandler getInstance() {
        if (clientHandler == null) {
            clientHandler = new ClientHandler(mAuth);
        }

        return clientHandler;
    }
}
