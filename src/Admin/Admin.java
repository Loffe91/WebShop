package Admin;
import User.User;

public class Admin extends User{

    public Admin(){
        super("admin@webshop.com", "123"); //
        setUserId(0);
    }
    @Override
    public String toString(){
        return "Admin{" +
                "id=" + getUserId() +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}

