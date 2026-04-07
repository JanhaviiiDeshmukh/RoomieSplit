package com.expense.ui;

import com.expense.entity.SavingsGoal;
import com.expense.entity.User;
import com.expense.service.ExpenseService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SavingsGoalFrame extends JFrame {

    private JTextField goalNameField;
    private JTextField targetAmountField;
    private JTextField savedAmountField;
    private JTextField targetDateField;
    private JComboBox<String> userComboBox;

    private JTable goalTable;
    private DefaultTableModel tableModel;

    private ExpenseService expenseService;

    public SavingsGoalFrame() {
        expenseService = new ExpenseService();

        setTitle("Savings Goals");
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(UITheme.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("Savings Goal Manager", SwingConstants.CENTER);
        heading.setFont(UITheme.SUBTITLE_FONT);
        heading.setForeground(UITheme.TEXT_DARK);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 12, 12));
        formPanel.setBackground(UITheme.CARD);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        goalNameField = new JTextField();
        targetAmountField = new JTextField();
        savedAmountField = new JTextField();
        targetDateField = new JTextField();
        userComboBox = new JComboBox<>();

        styleField(goalNameField);
        styleField(targetAmountField);
        styleField(savedAmountField);
        styleField(targetDateField);
        userComboBox.setFont(UITheme.LABEL_FONT);

        loadUsers();

        formPanel.add(createLabel("Goal Name:"));
        formPanel.add(goalNameField);

        formPanel.add(createLabel("Target Amount:"));
        formPanel.add(targetAmountField);

        formPanel.add(createLabel("Saved Amount:"));
        formPanel.add(savedAmountField);

        formPanel.add(createLabel("Target Date (yyyy-mm-dd):"));
        formPanel.add(targetDateField);

        formPanel.add(createLabel("User:"));
        formPanel.add(userComboBox);

        JButton saveButton = new JButton("Save Goal");
        saveButton.setFont(UITheme.BUTTON_FONT);
        saveButton.setBackground(UITheme.SUCCESS);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);

        formPanel.add(new JLabel());
        formPanel.add(saveButton);

        String[] columns = {
                "ID", "Goal Name", "Target Amount", "Saved Amount",
                "Remaining", "Monthly Needed", "Target Date", "User"
        };

        tableModel = new DefaultTableModel(columns, 0);
        goalTable = new JTable(tableModel);
        goalTable.setRowHeight(28);
        goalTable.setFont(UITheme.TABLE_FONT);
        goalTable.getTableHeader().setFont(UITheme.BUTTON_FONT);

        JScrollPane scrollPane = new JScrollPane(goalTable);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(UITheme.BACKGROUND);
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        saveButton.addActionListener(e -> saveGoal());

        mainPanel.add(heading, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        loadGoals();

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

    private void saveGoal() {
        try {
            String goalName = goalNameField.getText().trim();
            double targetAmount = Double.parseDouble(targetAmountField.getText().trim());
            double savedAmount = Double.parseDouble(savedAmountField.getText().trim());
            LocalDate startDate = LocalDate.now();
            LocalDate targetDate = LocalDate.parse(targetDateField.getText().trim());
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

            SavingsGoal goal = new SavingsGoal(
                    goalName,
                    targetAmount,
                    savedAmount,
                    startDate,
                    targetDate,
                    selectedUser
            );

            expenseService.saveSavingsGoal(goal);

            JOptionPane.showMessageDialog(this, "Savings goal saved successfully!");

            goalNameField.setText("");
            targetAmountField.setText("");
            savedAmountField.setText("");
            targetDateField.setText("");

            loadGoals();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid input. Please enter proper values.\nDate format must be yyyy-mm-dd"
            );
        }
    }

    private void loadGoals() {
        tableModel.setRowCount(0);

        List<SavingsGoal> goals = expenseService.getAllSavingsGoals();

        for (SavingsGoal goal : goals) {
            double remaining = goal.getRemainingAmount();

            long months = ChronoUnit.MONTHS.between(
                    LocalDate.now().withDayOfMonth(1),
                    goal.getTargetDate().withDayOfMonth(1)
            );

            if (months <= 0) {
                months = 1;
            }

            double monthlyNeeded = remaining / months;

            Object[] row = {
                    goal.getId(),
                    goal.getGoalName(),
                    goal.getTargetAmount(),
                    goal.getSavedAmount(),
                    remaining,
                    String.format("%.2f", monthlyNeeded),
                    goal.getTargetDate(),
                    goal.getUser().getName()
            };

            tableModel.addRow(row);
        }
    }
}