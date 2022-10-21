package ca.uottawa.engineering.mealer.classes;

public class Chef extends User {

    // Currently using int for cheque, but should consider different option
    private String cheque;
    private String description;

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
}
