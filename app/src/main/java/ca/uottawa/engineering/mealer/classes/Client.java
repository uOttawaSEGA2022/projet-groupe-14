package ca.uottawa.engineering.mealer.classes;

public class Client extends User {

    // How much info required for card?
    private int cardInfo;

    /**
     * Empty constructor used for Firebase. (DON'T USE, USE FULL CONSTRUCTOR!).
     */
    public Client() {}

    /**
     * Constructor for client
     * @param nickname Nickname of client
     * @param name Name of client
     * @param email Email address of client
     * @param address Address of client
     * @param cardInfo Card info of client
     */
    public Client(String nickname, String name, String email, String address, int cardInfo) {
        super(nickname, name, email, address, "client"); // hardcoded role
        this.cardInfo = cardInfo;
    }

    public int getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(int cardInfo) {
        this.cardInfo = cardInfo;
    }
}
