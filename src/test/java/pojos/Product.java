package pojos;

public class Product {

    private int id_product;
    private int qty;
    private int ipa;

    public Product() {
    }

    //use this constructor to convert DataTable to Java Object with cucumber DataTableType
    //it accepts Map<String,String> ...
    public Product(String id_product, String qty, String ipa) {
        this.id_product = id_product!=null ? Integer.parseInt(id_product) : 0;
        this.qty = qty!=null ? Integer.parseInt(qty) : 0;
        this.ipa = ipa!=null ? Integer.parseInt(ipa) : 0;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getIpa() {
        return ipa;
    }

    public void setIpa(int ipa) {
        this.ipa = ipa;
    }
}
