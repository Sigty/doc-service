package by.itacademy.database.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doc_part", schema = "doc_service")
public class DocPart implements BaseEntity<DocPart.DocPartId> {

    @EmbeddedId
    private DocPartId id;

    @Column(name = "quantity_part", nullable = false)
    private Integer quantityPart;

    @ManyToOne
    @JoinColumn(name = "doc_id")
    private Document document;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part parts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Embeddable
    public static class DocPartId implements Serializable {

        @Column(name = "doc_id")
        private Integer docId;

        @Column(name = "part_id")
        private Integer partId;
    }
}
