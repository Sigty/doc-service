package com.itacademy.database.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "doc_part", schema = "doc_service")
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class DocPart implements BaseEntity<DocPart.Id> {

    @EmbeddedId
    private Id id;

    @Column(name = "quantity_part", nullable = false)
    private Integer quantityPart;

    @ManyToOne
    @JoinColumn(name = "doc_id", insertable = false, updatable = false)
    private Document document;

    @ManyToOne
    @JoinColumn(name = "part_id", insertable = false, updatable = false)
    private Part parts;

    @Version
    @Column(name = "version")
    private Long version;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(of = {"docId", "partId"})
    @Embeddable
    public static class Id implements Serializable {

        @Column(name = "doc_id")
        private Integer docId;

        @Column(name = "part_id")
        private Integer partId;

    }
}
