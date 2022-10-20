package ca.uottawa.engineering.mealer.classes;

import java.util.ArrayList;

public class Chef extends User {

    private int cheque;
    private String description;

    public Chef() {
    };

    /**
     * Constructor for Chef
     * @param nickname
     * @param name
     * @param email
     * @param address
     * @param cheque
     * @param desc
     */
    public Chef(String nickname, String name, String email, String address, int cheque,
                String desc) {
        super(nickname, name, email, address);
        this.cheque = cheque;
        this.description = desc;
    }

    public int getCheque() {
        return cheque;
    }

    public void setCheque(int cheque) {
        this.cheque = cheque;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
