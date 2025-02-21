package Admin;

import Customers.Customer;
import Customers.CustomerRepository;
import Customers.CustomerService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminController {
    Admin loggedIn;
    AdminService adminService;
    CustomerService customerService;
    CustomerRepository customerRepository;
    Scanner scanner;



    // Metod för att visa kunder
    public void showCustomers() throws SQLException {
        System.out.println("\n=== Visa kunder ===");
        System.out.println("1. Visa alla kunder. ");
        System.out.println("2. Hämta kund baserat på email. ");
        System.out.println("3. Hämta kund baserat på ID ");
        System.out.println("0. Tillbaka. ");
        System.out.println("Välj ett alternativ: ");

        String select = scanner.nextLine();

        switch (select){
            case "1":
                // Anropa service-lagret för att visa alla kunder
                adminService.showAllUsers();
                break;
            case "2": // Hämta kund baserat på mail
                System.out.println("Ange mail: ");
                String mail = scanner.nextLine();
                Customer customerByEmail = customerService.getCustomerByEmail(mail); // Spara den returnerade kunden
                if(customerByEmail != null){ // Om kund hittas, skriv ut ID, namn & mail
                    System.out.println("ID: "+ customerByEmail.getUserId());
                    System.out.println("Namn: "+ customerByEmail.getName());
                    System.out.println("Email: "+ customerByEmail.getEmail());
                }
                break;
            case "3": // Hämta kund baserat på ID
                System.out.println("Ange ID: ");
                String idString = scanner.nextLine(); // Hämta id och konvertera till en stril
                int id = Integer.parseInt(idString);
                Customer customerById = customerService.getCustomerById(id); // Spara customerById till Customer
                if(customerById != null){ // Om en kund med angivet ID finns, dvs customerById är ej null
                    System.out.println("ID: "+customerById.getUserId()); // Skriv ut info
                    System.out.println("Namn: "+customerById.getName());
                    System.out.println("Email: "+customerById.getEmail());
                }
                break;
            case "0":
                return;
            default:
                System.out.println("Felaktigt val. Försök igen ");
        }
    }


}