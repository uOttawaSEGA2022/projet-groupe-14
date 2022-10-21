package ca.uottawa.engineering.mealer.classes;

/**
 * User
 */
public abstract class User {

    private String nickname;
    private String name;
    private String email;
    private String address;
    private String role; // Either chef, client

    /**
     * Empty constructor used for Firebase. (DON'T USE, USE FULL CONSTRUCTOR!).
     */
    public User() {
    }

    /**
     * User constructor (CAN'T BE INITIALIZED!)
     * @param nickname Nickname of user
     * @param name Name of user
     * @param email Email address of user
     * @param address Address of user
     * @param role Role of user. Will be automatically set by child class.
     */
    public User(String nickname, String name, String email, String address, String role) {
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.address = address;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}