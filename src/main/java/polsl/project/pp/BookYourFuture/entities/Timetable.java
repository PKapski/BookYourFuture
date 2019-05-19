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

    @Column(name = "start_hour")
    private int startHour;

    @Column(name = "start_minute")
    private int startMinute;

    @Column(name ="end_hour")
    private int endHour;

    @Column(name = "end_minute")
    private int endMinute;

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

    public Timetable(int startHour, int startMinute, int endHour, int endMinute, String date){
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.date = date;

    }

    public int getId(){return id;}
    public void setId(int id) {this.id = id;}

    public int getStartHour() {return startHour;}
    public void setStartHour(int startHour) {this.startHour = startHour;}

    public int getStartMinute() {return startMinute;}
    public void setStartMinute(int startMinute) {this.startMinute = startMinute;}

    public int getEndHour(){return endHour;}
    public void setEndHour(int endHour) {this.endHour = endHour;}

    public int getEndMinute(){return endMinute;}
    public void setEndMinute(int endMinute) {this.endMinute = endMinute;}

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
                "startHour=" + startHour +
                "startMinute=" + startMinute +
                "endHour=" + endHour +
                "endMinute=" + endMinute+
                "date=" + date +
                "Services=" + service +
                "User=" + user + "}";

    }




}