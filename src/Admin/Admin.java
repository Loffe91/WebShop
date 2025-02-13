package Admin;
import User.User;

public class Admin extends User{

    public Admin(){
        super("admin@webshop.com", "123"); //
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

