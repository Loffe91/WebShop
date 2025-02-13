package Products;

/**
 * Klass som representerar en produkt i webbshopen
 * Används för att hantera produktdata mellan databasen och applikationen
 */
public class Product {

    private int productId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;

    /**
     * Konstruktor för att skapa en ny Products.Product
     * Tar emot all nödvändig information för en produkt
     */
    public Product(int productId, String name, String description, double price, int stockQuantity) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * toString-metod för att få en läsbar representation av produkten
     * Användbar vid utskrift eller debugging
     */
    @Override
    public String toString() {
        return "Products.Product{" +
                "id=" + productId +
                ", Name='" + name + '\'' +
                ", Description='" + description + '\'' +
                ", Price='" + price + '\'' +
                ", Stock='" + stockQuantity + '\'' +
                '}';
    }
}
