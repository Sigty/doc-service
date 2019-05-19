package by.itacademy.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_detail", schema = "doc_service")
public class UserDetail implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname", nullable = false)
    private String firsname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @OneToOne(mappedBy = "userDetail")
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department departments;
}