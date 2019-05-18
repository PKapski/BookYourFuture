package polsl.project.pp.BookYourFuture.entities;

import javax.persistence.*;
import java.util.List;
/*
@Entity
@Table (name = "services_categories")
public class ServicesCategories {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_service_category")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
        @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "servicesCategories", cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    List<Services> services;

    public ServicesCategories(){

    }
    public ServicesCategories(String name){
        this.name = name;
    }

    public String getName(){return name;}
    public void setName(String name) {this.name = name;}

    public Company getCompany(){return company;}
    public void setCompany(Company company) {this.company = company;}

    @Override
    public String toString(){
        return "ServicesCategories{" +
                "id= " + id +
                ", name=" + name +
                ", company="+company+ "}";
    }


}
*/