package polsl.project.pp.BookYourFuture.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "services")
public class Service {

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
    private ServiceCategory servicesCategories;

/*    @OneToMany(mappedBy = "service", cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})*/
    @OneToMany(mappedBy = "service", cascade={CascadeType.ALL})
    private List<Timetable> timetables;

    public Service(){

    }

    public Service(String name, int duration){
        this.name = name;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {return  name;}

    public void setName(String name) {this.name = name;}

    public int getDuration() {return duration;}

    public void setDuration(int duration) {this.duration = duration;}

    public ServiceCategory getServicesCategories() {return servicesCategories;}

    public void setServicesCategories(ServiceCategory servicesCategories) {this.servicesCategories = servicesCategories;}

    @Override
    public String toString(){
        return "Service{" +
                "id=" + id +
                ", name=" + name+
                ", servicesCategories=" + servicesCategories +"}";

    }


}