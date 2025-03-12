package Customers;

/**
 * Enum DiscountLevel representerar olika rabattnivåer för kunder.
 * Varje nivå har en specifik rabattprocent.
 */
public enum DiscountLevel {
    // Definierar respektive rabatter
    CUSTOMER(0),       // Vanlig kund, ingen rabatt
    NEW_CUSTOMER(10),  // Nya kunder får 10% rabatt
    BRONZE(5),         // Bronze-nivå ger 5% rabatt
    SILVER(10),        // Silver-nivå ger 10% rabatt
    GOLD(15),          // Guld-nivå ger 15% rabatt
    PLATINUM(20);      // Platinum-nivå ger 20% rabatt

    // Privat variabel som lagrar rabattprocenten för varje nivå
    private final int discount;

    /**
     * Konstruktor för varje DiscountLevel.
     * @param discount Rabattprocent kopplad till nivån.
     */
    DiscountLevel(int discount) {
        this.discount = discount;
    }

    /**
     * Getter-metod för att hämta rabattprocenten för DiscountLevel.
     * @return Rabatt i procent.
     */
    public int getDiscount() {
        return discount;
    }

    /**
     * Metod som avgör DiscountLevel baserat på kundens poäng och om kunden är ny.
     * @param points Antalet kundpoäng som kunden har.
     * @param isNewCustomer Boolean som anger om kunden är ny.
     * @return Kundens aktuella DiscountLevel.
     */
    public static DiscountLevel getLevel(int points, boolean isNewCustomer) {
        // Om kunden är ny, sätt nivå till NEW_CUSTOMER oavsett poäng
        if (isNewCustomer) {
            return NEW_CUSTOMER;
        }

        // Bestäm DiscountLevel baserat på antal poäng.
        if (points >= 100000) {
            return PLATINUM;   // 100,000+ poäng ger Platinum-nivå (20% rabatt)
        } else if (points >= 50000) {
            return GOLD;       // 50,000+ poäng ger Guld-nivå (15% rabatt)
        } else if (points >= 20000) {
            return SILVER;     // 20,000+ poäng ger Silver-nivå (10% rabatt)
        } else if (points >= 10000) {
            return BRONZE;     // 10,000+ poäng ger Bronze-nivå (5% rabatt)
        } else {
            return CUSTOMER;   // Mindre än 10,000 poäng ger ingen rabatt
        }
    }
}
