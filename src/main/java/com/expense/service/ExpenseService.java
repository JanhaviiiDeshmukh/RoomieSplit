package com.expense.service;

import com.expense.entity.Expense;
import com.expense.entity.SavingsGoal;
import com.expense.entity.User;
import com.expense.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ExpenseService {

    public void saveUser(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void saveExpense(Expense expense) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(expense);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void saveSavingsGoal(SavingsGoal savingsGoal) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(savingsGoal);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    public List<Expense> getAllExpenses() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Expense", Expense.class).list();
        }
    }

    public List<SavingsGoal> getAllSavingsGoals() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from SavingsGoal", SavingsGoal.class).list();
        }
    }

    public String calculateBalance() {
        List<Expense> expenses = getAllExpenses();

        double total = 0;
        double janhaviPaid = 0;
        double roommatePaid = 0;

        for (Expense e : expenses) {
            total += e.getAmount();

            if (e.getPaidBy().getName().equalsIgnoreCase("Janhavi")) {
                janhaviPaid += e.getAmount();
            } else {
                roommatePaid += e.getAmount();
            }
        }

        double eachShare = total / 2.0;

        StringBuilder result = new StringBuilder();
        result.append("Total Shared Expense = ").append(total).append("\n");
        result.append("Each Person Share = ").append(eachShare).append("\n");
        result.append("Janhavi Paid = ").append(janhaviPaid).append("\n");
        result.append("Roommate Paid = ").append(roommatePaid).append("\n\n");

        if (janhaviPaid > eachShare) {
            result.append("Roommate has to pay Janhavi = ").append(janhaviPaid - eachShare);
        } else if (roommatePaid > eachShare) {
            result.append("Janhavi has to pay Roommate = ").append(roommatePaid - eachShare);
        } else {
            result.append("Both are settled up.");
        }

        return result.toString();
    }
}