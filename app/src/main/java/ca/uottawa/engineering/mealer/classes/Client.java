package ca.uottawa.engineering.mealer.classes;

public class Client extends User {

    // How much info required for card?
    private String cardNum;
    private String cardExp;
    private String cardCVV;

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
     * @param cardNum Card number of client
     * @param cardExp Card expiration date of client
     * @param cardCVV Card cvv of client
     */
    public Client(String nickname, String name, String email, String address, String cardNum, String cardExp, String cardCVV) {
        super(nickname, name, email, address, "client"); // hardcoded role
        this.cardNum = cardNum;
        this.cardExp = cardExp;
        this.cardCVV = cardCVV;
    }


    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardExp() {
        return cardExp;
    }

    public void setCardExp(String cardExp) {
        this.cardExp = cardExp;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public Complaint complain(Chef chef) {

        return new Complaint(chef);
    }
}
