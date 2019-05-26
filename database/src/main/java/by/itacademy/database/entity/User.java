package by.itacademy.database.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(of = {"id", "login", "password", "role", "userDetail"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user", schema = "doc_service")
public class User implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToOne
    @JoinColumn(name = "detail_user_id")
    private UserDetail userDetail;

    @OneToMany(mappedBy = "partUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Part> parts = new HashSet<>();

    @OneToMany(mappedBy = "docUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Document> documents = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_project", schema = "doc_service",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects = new HashSet<>();

    public User(String login, String password, Role role, UserDetail userDetail) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.userDetail = userDetail;
    }

    public User(String login, String password, Role role, UserDetail userDetail, Set<Project> projects) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.userDetail = userDetail;
        this.projects = projects;
    }
}
