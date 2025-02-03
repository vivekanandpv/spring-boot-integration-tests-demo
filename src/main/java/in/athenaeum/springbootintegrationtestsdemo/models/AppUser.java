package in.athenaeum.springbootintegrationtestsdemo.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appUserId;

    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app_user_roles",
            joinColumns = { @JoinColumn(name = "app_user_id") },
            inverseJoinColumns = { @JoinColumn(name = "app_role_id") })
    private Set<AppRole> appRoles = new HashSet<>();

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<AppRole> getAppRoles() {
        return appRoles;
    }

    public void setAppRoles(Set<AppRole> appRoles) {
        this.appRoles = appRoles;
    }
}
