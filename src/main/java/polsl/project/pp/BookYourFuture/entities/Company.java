package polsl.project.pp.BookYourFuture.entities;

import javax.persistence.*;
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

    public Company(String name, String address, String nip, User user) {
        this.name = name;
        this.address = address;
        this.nip = nip;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
