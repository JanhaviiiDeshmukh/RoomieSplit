package com.expense.ui;

import com.expense.service.ExpenseService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private final ExpenseService expenseService;

    public DashboardFrame() {
        expenseService = new ExpenseService();

        setTitle("RoomieSplit - Expense Management System");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(UITheme.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel titleLabel = new JLabel("RoomieSplit Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(UITheme.TITLE_FONT);
        titleLabel.setForeground(UITheme.TEXT_DARK);

        JLabel subtitleLabel = new JLabel("Manage shared expenses, balances, and savings goals", SwingConstants.CENTER);
        subtitleLabel.setFont(UITheme.LABEL_FONT);
        subtitleLabel.setForeground(UITheme.TEXT_LIGHT);

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.setBackground(UITheme.BACKGROUND);
        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        JPanel cardPanel = new JPanel(new GridLayout(5, 1, 15, 15));
        cardPanel.setBackground(UITheme.BACKGROUND);

        JButton addExpenseButton = createStyledButton("Add Expense");
        JButton viewExpensesButton = createStyledButton("View Expenses");
        JButton checkBalanceButton = createStyledButton("Check Balance");
        JButton savingsGoalButton = createStyledButton("Savings Goals");
        JButton exitButton = createStyledButton("Exit");

        cardPanel.add(addExpenseButton);
        cardPanel.add(viewExpensesButton);
        cardPanel.add(checkBalanceButton);
        cardPanel.add(savingsGoalButton);
        cardPanel.add(exitButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        addExpenseButton.addActionListener(e -> new AddExpenseFrame());
        viewExpensesButton.addActionListener(e -> new ViewExpensesFrame());

        checkBalanceButton.addActionListener(e -> {
            String balance = expenseService.calculateBalance();
            JOptionPane.showMessageDialog(this, balance, "Balance Summary", JOptionPane.INFORMATION_MESSAGE);
        });

        savingsGoalButton.addActionListener(e -> new SavingsGoalFrame());
        exitButton.addActionListener(e -> System.exit(0));

        setContentPane(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UITheme.BUTTON_FONT);
        button.setBackground(UITheme.PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        return button;
    }
}