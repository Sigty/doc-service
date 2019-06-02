package com.itacademy.database.entity;

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
@DiscriminatorValue("assembly")
public class Assembly extends DocType {

    @Column(name = "assembly")
    private Boolean assembly;

    public Assembly(String name, Boolean assembly) {
        super(name);
        this.assembly = assembly;
    }
}
