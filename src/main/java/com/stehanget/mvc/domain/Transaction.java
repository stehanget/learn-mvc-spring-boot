package com.stehanget.mvc.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "unit")
    private int unit;

    @Column(name = "total")
    private Long total;

    @Column(name = "remaining_stock")
    private int remainingStock;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
}
