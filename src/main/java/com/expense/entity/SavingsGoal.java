package com.expense.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "savings_goals")
public class SavingsGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String goalName;

    @Column(nullable = false)
    private double targetAmount;

    @Column(nullable = false)
    private double savedAmount;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate targetDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public SavingsGoal() {
    }

    public SavingsGoal(String goalName, double targetAmount, double savedAmount,
                       LocalDate startDate, LocalDate targetDate, User user) {
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.savedAmount = savedAmount;
        this.startDate = startDate;
        this.targetDate = targetDate;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(double savedAmount) {
        this.savedAmount = savedAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getRemainingAmount() {
        return targetAmount - savedAmount;
    }
}