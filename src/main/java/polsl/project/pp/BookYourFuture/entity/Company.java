package polsl.project.pp.BookYourFuture.entity;

public class Company
{
    private String name;
    private String ownerName;
    private String address;
    private String vatIN;
    private String category;
    public Company(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVatIN() {
        return vatIN;
    }

    public void setVatIN(String vatIN) {
        this.vatIN = vatIN;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
