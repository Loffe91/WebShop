package User;

public abstract class User {
    protected int userId;
    protected String email;
    protected String password;
    protected String role; // Admin eller customer


    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
