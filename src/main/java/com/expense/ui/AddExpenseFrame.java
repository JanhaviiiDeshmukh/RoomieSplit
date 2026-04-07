package com.expense.ui;

import com.expense.entity.Expense;
import com.expense.entity.User;
import com.expense.service.ExpenseService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class AddExpenseFrame extends JFrame {

    private final JTextField titleField;
    private final JTextField amountField;
    private final JTextField categoryField;
    private final JComboBox<String> userComboBox;
    private final ExpenseService expenseService;

    public AddExpenseFrame() {
        expenseService = new ExpenseService();

        setTitle("Add Expense");
        setSize(500, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(UITheme.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("Add New Expense", SwingConstants.CENTER);
        heading.setFont(UITheme.SUBTITLE_FONT);
        heading.setForeground(UITheme.TEXT_DARK);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 12, 12));
        formPanel.setBackground(UITheme.CARD);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        titleField = new JTextField();
        amountField = new JTextField();
        categoryField = new JTextField();
        userComboBox = new JComboBox<>();

        loadUsers();

        styleField(titleField);
        styleField(amountField);
        styleField(categoryField);
        userComboBox.setFont(UITheme.LABEL_FONT);

        formPanel.add(createLabel("Title:"));
        formPanel.add(titleField);

        formPanel.add(createLabel("Amount:"));
        formPanel.add(amountField);

        formPanel.add(createLabel("Category:"));
        formPanel.add(categoryField);

        formPanel.add(createLabel("Paid By:"));
        formPanel.add(userComboBox);

        JButton saveButton = new JButton("Save Expense");
        saveButton.setFont(UITheme.BUTTON_FONT);
        saveButton.setBackground(UITheme.SUCCESS);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);

        formPanel.add(new JLabel());
        formPanel.add(saveButton);

        saveButton.addActionListener(e -> saveExpense());

        mainPanel.add(heading, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.LABEL_FONT);
        label.setForeground(UITheme.TEXT_DARK);
        return label;
    }

    private void styleField(JTextField field) {
        field.setFont(UITheme.LABEL_FONT);
    }

    private void loadUsers() {
        List<User> users = expenseService.getAllUsers();
        for (User user : users) {
            userComboBox.addItem(user.getName());
        }
    }

    private void saveExpense() {
        try {
            String title = titleField.getText().trim();
            double amount = Double.parseDouble(amountField.getText().trim());
            String category = categoryField.getText().trim();
            String selectedUserName = (String) userComboBox.getSelectedItem();

            User selectedUser = null;
            for (User user : expenseService.getAllUsers()) {
                if (user.getName().equals(selectedUserName)) {
                    selectedUser = user;
                    break;
                }
            }

            if (selectedUser == null) {
                JOptionPane.showMessageDialog(this, "User not found.");
                return;
            }

            Expense expense = new Expense(title, amount, category, LocalDate.now(), selectedUser);
            expenseService.saveExpense(expense);

            JOptionPane.showMessageDialog(this, "Expense saved successfully!");

            titleField.setText("");
            amountField.setText("");
            categoryField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check values.");
        }
    }
}