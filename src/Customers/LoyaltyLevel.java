package Customers;

public enum LoyaltyLevel  {
    NEW_CUSTOMER(10),
    BRONZE(5),
    SILVER(10),
    GOLD(15),
    PLATINUM(20);

    private final int discount;

    LoyaltyLevel(int discount) {
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }

    public static LoyaltyLevel getLevel(int points, boolean isNewCustomer) {
        if (isNewCustomer) {
            return NEW_CUSTOMER;
        }
        if (points >= 100000) {
            return PLATINUM;
        } else if (points >= 50000) {
            return GOLD;
        } else if (points >= 20000) {
            return SILVER;
        } else {
            return BRONZE;
        }
    }
}