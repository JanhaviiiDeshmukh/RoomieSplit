package com.expense.ui;

import com.expense.entity.Expense;
import com.expense.service.ExpenseService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewExpensesFrame extends JFrame {

    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private ExpenseService expenseService;

    public ViewExpensesFrame() {
        expenseService = new ExpenseService();

        setTitle("View Expenses");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(UITheme.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("Expense Records", SwingConstants.CENTER);
        heading.setFont(UITheme.SUBTITLE_FONT);
        heading.setForeground(UITheme.TEXT_DARK);

        String[] columnNames = {"ID", "Title", "Amount", "Category", "Date", "Paid By"};
        tableModel = new DefaultTableModel(columnNames, 0);
        expenseTable = new JTable(tableModel);

        expenseTable.setRowHeight(28);
        expenseTable.setFont(UITheme.TABLE_FONT);
        expenseTable.getTableHeader().setFont(UITheme.BUTTON_FONT);
        expenseTable.setSelectionBackground(new Color(220, 235, 250));

        JScrollPane scrollPane = new JScrollPane(expenseTable);

        mainPanel.add(heading, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        loadExpenses();

        setContentPane(mainPanel);
        setVisible(true);
    }

    private void loadExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        tableModel.setRowCount(0);

        for (Expense e : expenses) {
            Object[] row = {
                    e.getId(),
                    e.getTitle(),
                    e.getAmount(),
                    e.getCategory(),
                    e.getExpenseDate(),
                    e.getPaidBy().getName()
            };
            tableModel.addRow(row);
        }
    }
}