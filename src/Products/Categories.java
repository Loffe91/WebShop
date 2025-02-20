package Products;

/**
 * Klass som representerar en kategori i webbshopen.
 * Används för att hantera kategoridata mellan databasen och applikationen.
 */
public class Categories {
    private int category_id;
    private String category_name;

    /**
     * Konstruktor för att skapa en ny Categories.Category.
     * Tar emot all nödvändig information för en kategori.
     */
    public Categories(int category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
    }

    /**
     * Metod för att hämta en kategoris id från databasen.
     */
    public int getCategory_id() {
        return category_id;
    }

    /**
     * Metod för att sätta ett id på en kategori i databasen.
     */
    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    /**
     * Metod för att hämta en kategoris namn från databasen.
     */
    public String getCategory_name() {
        return category_name;
    }

    /**
     * Metod för att sätta ett namn på en kategori i databasen.
     */
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    /**
     * Metod för att visa data från Categories table.
     */
    @Override
    public String toString() {
        return "Categories{" +
                "category_id=" + category_id +
                ", category_name=" + category_name +
                '}';
    }
}
