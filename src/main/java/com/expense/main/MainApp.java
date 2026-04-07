package com.expense.main;

import com.expense.entity.User;
import com.expense.service.ExpenseService;
import com.expense.ui.DashboardFrame;

import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {
        ExpenseService service = new ExpenseService();

        if (service.getAllUsers().isEmpty()) {
            service.saveUser(new User("Janhavi", 10000));
            service.saveUser(new User("Roommate", 10000));
        }

        SwingUtilities.invokeLater(DashboardFrame::new);
    }
}