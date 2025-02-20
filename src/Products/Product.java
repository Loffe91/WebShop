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

    /**
     * Metod för att returnera produkt id.
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Metod för att sätta ett produkt id.
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Metod för att returnera produkt namn.
     */
    public String getName() {
        return name;
    }

    /**
     * Metod för att sätta produkt namn.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metod för att returnera produkt beskrivning.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Metod för att sätta produkt beskrivning.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Metod för att returnera produkt pris.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Metod för att sätta produkt pris.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Metod för att returnera produkt lagerstatus.
     */
    public int getStockQuantity() {
        return stockQuantity;
    }

    /**
     * Metod för att sätta produkt lagerstatus.
     */
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
