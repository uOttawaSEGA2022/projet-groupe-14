package ca.uottawa.engineering.mealer.classes;

public class Client extends User {

    private int cardInfo;

    public Client() {}

    public Client(String nickname, String name, String email, String address, String role, int cardInfo) {
        super(nickname, name, email, address, role);
        this.cardInfo = cardInfo;
    }

    public int getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(int cardInfo) {
        this.cardInfo = cardInfo;
    }
}
