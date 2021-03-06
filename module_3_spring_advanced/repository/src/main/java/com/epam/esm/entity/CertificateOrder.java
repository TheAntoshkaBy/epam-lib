package com.epam.esm.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "certificate_order")
@Table(name = "certificate_order")
@NoArgsConstructor
@AllArgsConstructor
public class CertificateOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "description")
    private String description;


    @Column(name = "cost")
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @ManyToMany(cascade = {
        CascadeType.ALL
    })
    @JoinTable(
        name = "o_c",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "certificate_id")
    )
    private List<Certificate> certificates;

    public CertificateOrder(BigDecimal cost, String description, Date createTime) {
        this.cost = cost;
        this.description = description;
        this.createTime = createTime;
    }
}
