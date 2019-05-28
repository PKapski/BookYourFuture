package polsl.project.pp.BookYourFuture.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "phone_num")
    private String phone;

    @Column(name = "role")
    private String role;

/*    @OneToMany(mappedBy = "user", cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})*/
    @OneToMany(mappedBy = "user", cascade={CascadeType.ALL})
    List<Timetable> timetables;

    public User(){

    }

    public User(String login, String password, String firstName, String lastName, String phone, String email) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email=email;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +

                '}';
    }
}
