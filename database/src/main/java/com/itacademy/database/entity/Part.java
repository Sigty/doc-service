package com.itacademy.database.entity;

import java.time.OffsetDateTime;
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
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "docParts")
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "part", schema = "doc_service")
public class Part implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "part_number", unique = true, nullable = false)
    private String partNumber;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "sort", nullable = false)
    private String sort;

    @Column(name = "create_part_date", nullable = false)
    private OffsetDateTime createPartDate;

    @ManyToOne
    @JoinColumn(name = "create_user_id", nullable = false)
    private User partUser;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "parts")
    private Set<DocPart> docParts = new HashSet<>();
}
