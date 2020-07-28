package com.epam.esm.pojo;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificatePOJO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer durationDays;
    private List<Tag> tags;
    private Date creationDate;
    private Date modification;

    public CertificatePOJO(Long id,
                           String name,
                           String description,
                           Double price,
                           Date creationDate,
                           Date modification,
                           Integer durationDays) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationDays = durationDays;
        this.creationDate = creationDate;
        this.modification = modification;
    }

    public CertificatePOJO(String name, String description, Double price, Integer durationDays) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationDays = durationDays;
    }

    public CertificatePOJO(Certificate certificate) {
        this.id = certificate.getId();
        this.name = certificate.getName();
        this.description = certificate.getDescription();
        this.price = certificate.getPrice();
        this.creationDate = certificate.getCreationDate();
        this.modification = certificate.getModification();
        this.durationDays = certificate.getDurationDays();
        this.tags = certificate.getTags();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificatePOJO that = (CertificatePOJO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(durationDays, that.durationDays) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, durationDays, tags);
    }

    public Certificate pojoToEntity() {
        if(this.id == null){
            return new Certificate(
                    this.name,
                    this.description,
                    this.price,
                    this.durationDays
            );
        }else {
            return new Certificate(
                    this.id,
                    this.name,
                    this.description,
                    this.price,
                    this.durationDays
            );
        }
    }
}