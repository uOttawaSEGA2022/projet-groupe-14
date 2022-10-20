package ca.uottawa.engineering.mealer.classes;

import java.util.ArrayList;

public class Chef extends User {

    private String cheque;
    private String description;

    public Chef() {
    };

    public Chef(String nickname, String name, String email, String address, String cheque,
                String desc) {
        super(nickname, name, email, address);
        this.cheque = cheque;
        this.description = desc;
    }

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
}
