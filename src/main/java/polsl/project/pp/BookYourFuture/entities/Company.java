package polsl.project.pp.BookYourFuture.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="companies")
public class Company
{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_company")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "nip")
    private String nip;

    @Column(name = "open_time")
    private String openTime;

    @Column(name = "close_time")
    private String closeTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

/*    @OneToMany(mappedBy = "company", cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})*/
    @OneToMany(mappedBy = "company", cascade=CascadeType.ALL)
    List<ServiceCategory> servicesCategories;

    public Company(){

    }

    public Company(String name, String address, String nip,String openTime,String closeTime, User user) {
        this.name = name;
        this.address = address;
        this.nip = nip;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.user = user;
    }
    public int getId() {return id; }

    public void setId(int id) { this.id = id;}

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

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ServiceCategory> getServicesCategories() {
        return servicesCategories;
    }

    public void setServicesCategories(List<ServiceCategory> servicesCategories) {
        this.servicesCategories = servicesCategories;
    }

    public void addServiceCategory(ServiceCategory serviceCategory){
        if(servicesCategories==null){
            servicesCategories = new ArrayList<>();
        }
        servicesCategories.add(serviceCategory);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", nip='" + nip + '\'' +
                ", user=" + user +
                '}';
    }
}
