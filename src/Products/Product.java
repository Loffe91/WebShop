package Products;

public class Product {

    public int productID;
    public String productName;
    public String productDescription;
    public int productPrice;
    public int productQuantity;

    public Product(int productID, String manufacturerID, String productName, String productDescription, int productPrice) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productQuantity = 0; //ska denna vara 0 eller ska den hämtas från db eller det kanske den gör senare?
    }

    //Skriver ut all produktinfo på ett läsbart sätt
    @Override
    public String toString() {
        return "Products.Product {" +
                "id=" + productID +
                ", name='" + productName + '\'' +
                ", description='" + productDescription + '\'' +
                ", price='" + productPrice + '\'' +
                ", quantity=" + productQuantity + '\'' +
                '}';
    }
}
