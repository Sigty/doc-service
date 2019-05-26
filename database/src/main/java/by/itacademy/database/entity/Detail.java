package by.itacademy.database.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@DiscriminatorValue("detail")
public class Detail extends DocType {

    @Column(name = "detail")
    private Boolean detail;

    public Detail(String name, Boolean detail) {
        super(name);
        this.detail = detail;
    }
}
