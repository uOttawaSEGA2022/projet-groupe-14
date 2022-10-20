package ca.uottawa.engineering.mealer.classes;

/**
 * User
 */
public abstract class User {

    private String nickname;
    private String name;
    private String email;
    private String address;

    public User() {
    }

    /**
     * User constructor
     *
     * @param nickname
     * @param name
     * @param email
     * @param address
     */
    public User(String nickname, String name, String email, String address) {
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    // Getters and Setters
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}