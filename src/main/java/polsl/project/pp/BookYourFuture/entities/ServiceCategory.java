package polsl.project.pp.BookYourFuture.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "services_categories")
public class ServiceCategory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_service_category")
    private int id;

    @Column(name = "name")
    private String categoryName;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    private Company company;

/*    @OneToMany(mappedBy = "servicesCategories", cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})*/
    @OneToMany(mappedBy = "servicesCategories", cascade=CascadeType.ALL)
    List<Service> services;

    public ServiceCategory(){

    }

    public int getId() {
        return id;
    }

    public ServiceCategory(String name){
        this.categoryName = name;
    }

    public String getCategoryName(){return categoryName;}

    public void setCategoryName(String name) {this.categoryName = name;}

    public Company getCompany(){return company;}

    public void setCompany(Company company) {this.company = company;}

    @Override
    public String toString(){
        return "ServicesCategories{" +
                "id= " + id +
                ", name=" + categoryName +
                ", company="+company+ "}";
    }


}
