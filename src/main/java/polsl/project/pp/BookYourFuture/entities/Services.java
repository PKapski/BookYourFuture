package polsl.project.pp.BookYourFuture.entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;
/*
@Entity
@Table (name = "services")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name="duration")
    private int duration;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "category_service_id")
    private ServicesCategories servicesCategories;

    @OneToMany(mappedBy = "services", cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    List<Timetable> timetable;

    public Services(){

    }

    public Services(String name, int duration){
        this.name = name;
        this.duration = duration;
    }


    public String getName() {return  name;}
    public void setName(String name) {this.name = name;}

    public int getDuration() {return duration;}
    public void setDuration(int duration) {this.duration = duration;}

    public ServicesCategories getServicesCategories() {return servicesCategories;}
    public void setServicesCategories(ServicesCategories servicesCategories) {this.servicesCategories = servicesCategories;}

    @Override
    public String toString(){
        return "Services{" +
                "id=" + id +
                ", name=" + name+
                ", servicesCategories=" + servicesCategories +"}";

    }


}
*/