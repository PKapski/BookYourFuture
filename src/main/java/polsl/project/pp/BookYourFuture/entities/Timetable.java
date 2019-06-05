package polsl.project.pp.BookYourFuture.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "timetable")
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_timetable")
    private int id;

    @Column(name = "start_time")
    private String startTime;

    @Column(name ="end_time")
    private String endTime;

    @Column(name = "date")
    private String date;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    public Timetable(){

    }

    public Timetable(String startTime,String endTime,  String date){
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;

    }

    public int getId(){return id;}
    public void setId(int id) {this.id = id;}

    public String getStartTime() {return startTime;}
    public void setStartTime(String startTime) {this.startTime = startTime;}

    public String getEndTime(){return endTime;}
    public void setEndTime(String endTime) {this.endTime = endTime;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public Service getServices() {return service;}
    public void setServices(Service services) {this.service = services;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    @Override
    public String toString(){
        return "Timetable{" +
                "id=" + id +
                "startTime=" + startTime +
                "endTime=" + endTime +
                "date=" + date +
                "Services=" + service +
                "User=" + user + "}";

    }




}