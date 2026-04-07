package com.expense.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double monthlyAllowance;

    public User() {
    }

    public User(String name, double monthlyAllowance) {
        this.name = name;
        this.monthlyAllowance = monthlyAllowance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMonthlyAllowance() {
        return monthlyAllowance;
    }

    public void setMonthlyAllowance(double monthlyAllowance) {
        this.monthlyAllowance = monthlyAllowance;
    }
}