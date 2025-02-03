package in.athenaeum.springbootintegrationtestsdemo.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appRoleId;

    @Column(unique = true)
    private String role;

    @ManyToMany(mappedBy = "appRoles")
    private Set<AppUser> users = new HashSet<>();

    public int getAppRoleId() {
        return appRoleId;
    }

    public void setAppRoleId(int appRoleId) {
        this.appRoleId = appRoleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<AppUser> users) {
        this.users = users;
    }
}
