package by.itacademy.database.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(exclude = "userProject")
@AllArgsConstructor
@Entity
@Table(name = "project", schema = "doc_service")
public class Project implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "projects", cascade = {CascadeType.PERSIST})
    private Set<User> userProject = new HashSet<>();

    public Project(String name) {
        this.name = name;
    }

    public Project(String name, Set<User> userProject) {
        this.name = name;
        this.userProject = userProject;
    }
}
