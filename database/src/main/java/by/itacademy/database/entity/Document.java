package by.itacademy.database.entity;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "document", schema = "doc_service")
public class Document implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "create_doc_date", nullable = false)
    private ZonedDateTime createDocDate;

    @OneToOne(mappedBy = "document")
    private User docUser;

    @ManyToOne
    @JoinColumn(name = "type_doc_id")
    private DocType docTypes;

    @OneToMany(mappedBy = "document")
    private Set<DocPart> DocParts = new HashSet<>();

}
