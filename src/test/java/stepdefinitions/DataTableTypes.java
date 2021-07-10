package stepdefinitions;

import io.cucumber.java.DataTableType;
import pojos.Address;
import pojos.Product;
import pojos.User;

import java.util.Map;

//convert DataTable to Java Object
public class DataTableTypes {

    @DataTableType
    public Address addressEntry(Map<String, String> entry) {
        return new Address(
                entry.get("firstName"),
                entry.get("lastName"),
                entry.get("address"),
                entry.get("city"),
                entry.get("state"),
                entry.get("zip"),
                entry.get("country"),
                entry.get("mobilePhone"),
                entry.get("addressAlias")
        );
    }

    @DataTableType
    public User userEntry(Map<String, String> entry) {
        return new User(
                entry.get("email"),
                entry.get("firstName"),
                entry.get("lastName"),
                entry.get("password")
        );
    }

    @DataTableType
    public Product productEntry(Map<String, String> entry) {
        return new Product(
                entry.get("id_product"),
                entry.get("qty"),
                entry.get("ipa")
        );
    }
}
