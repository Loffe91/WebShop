package Admin;
import User.User;

public class Admin extends User{

    private String name;
    private String email;
    public Admin(){
        super("admin@webshop.com", "123", "admin"); //
        this.userId = 0;
    }
    @Override
    public String toString(){
        return "Admin{" +
                "id=" + userId +
                ", email='" + email + '\'' +
                '}';
    }
}

