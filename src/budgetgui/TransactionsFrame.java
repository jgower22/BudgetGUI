/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budgetgui;

import static budgetgui.UUIDGenerator.generateUUID;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author jacksongower
 */
public class TransactionsFrame extends javax.swing.JFrame {

    private String month;
    private ArrayList<String> months = new ArrayList<>();
    private ArrayList<String> transactions = new ArrayList<>();
    private ArrayList<String> income = new ArrayList<>();
    private ArrayList<String> foundYears = new ArrayList<>();
    private HashMap<String, Double> categoryLimits = new HashMap<>();
    private HashMap<String, Double> categorySpending = new HashMap<>();
    private double totalSpending = 0.0;
    private double totalIncome = 0.0;
    private String curYear;
    private int indexOfMonth;
    private static final int DESCRIPTION_LIMIT = 50;

    /**
     * Creates new form TransactionsFrame
     */
    public TransactionsFrame(String month, String curYear, ArrayList<String> foundYears) {
        initComponents();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        this.month = month;
        this.curYear = curYear;
        this.foundYears = foundYears;

        this.setVisible(true);
        this.setResizable(false);
        String formattedMonth = month.replaceAll("\t", " ");
        this.setTitle("Transactions For " + formattedMonth);
        monthLabel.setText(formattedMonth);

        updateTransactionsList();

        updateIncomeList();

        //Check if prevMonth / nextMonth button should be active
        MainFrame obj = new MainFrame();
        months = obj.getMonths();

        //Sort months
        Collections.sort(months, (String o1, String o2) -> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM" + "\t" + "yyyy");
                return sdf.parse(o1).compareTo(sdf.parse(o2));  //sdf.parse returns date - So, Compare Date with date
            } catch (ParseException ex) {
                return o1.compareTo(o2);
            }
        });

        indexOfMonth = months.indexOf(month);
        //Prev month button should be disabled
        if (indexOfMonth == 0) {
            prevMonthButton.setEnabled(false);
        }
        //Next button button should be disabled
        if (indexOfMonth == months.size() - 1) {
            nextMonthButton.setEnabled(false);
        }

        //Hide Recurring Button
        makeRecurringButton.setVisible(false);

        removeTransactionButton.setEnabled(false);
        editDetailsSpendingButton.setEnabled(false);

        transactionsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    removeTransactionButton.setEnabled(true);
                    editDetailsSpendingButton.setEnabled(true);
                }
            }
        });

        removeTransactionIncomeButton.setEnabled(false);
        editDetailsIncomeButton.setEnabled(false);

        incomeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    removeTransactionIncomeButton.setEnabled(true);
                    editDetailsIncomeButton.setEnabled(true);
                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        transactionsList = new javax.swing.JList<>();
        numTransactionsLabel = new javax.swing.JLabel();
        monthLabel = new javax.swing.JLabel();
        exitButton = new javax.swing.JButton();
        addTransactionButton = new javax.swing.JButton();
        removeTransactionButton = new javax.swing.JButton();
        totalLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        incomeList = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        numTransactionsIncomeLabel = new javax.swing.JLabel();
        totalIncomeLabel = new javax.swing.JLabel();
        addTransactionIncomeButton = new javax.swing.JButton();
        removeTransactionIncomeButton = new javax.swing.JButton();
        cashFlowLabel = new javax.swing.JLabel();
        editDetailsSpendingButton = new javax.swing.JButton();
        editDetailsIncomeButton = new javax.swing.JButton();
        viewBudgetsButton = new javax.swing.JButton();
        percentageIncomeSavedLabel = new javax.swing.JLabel();
        makeRecurringButton = new javax.swing.JButton();
        prevMonthButton = new javax.swing.JButton();
        nextMonthButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        transactionsList.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jScrollPane1.setViewportView(transactionsList);

        numTransactionsLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        numTransactionsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        numTransactionsLabel.setText("# Transactions: 0");

        monthLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        monthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        monthLabel.setText("Month");

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        addTransactionButton.setText("Add Transaction");
        addTransactionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTransactionButtonActionPerformed(evt);
            }
        });

        removeTransactionButton.setText("Remove Transaction");
        removeTransactionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeTransactionButtonActionPerformed(evt);
            }
        });

        totalLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        totalLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalLabel.setText("Total: $0.00");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Spending");

        incomeList.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jScrollPane2.setViewportView(incomeList);

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Income");

        numTransactionsIncomeLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        numTransactionsIncomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        numTransactionsIncomeLabel.setText("# Transactions: 0");

        totalIncomeLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        totalIncomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalIncomeLabel.setText("Total: $0.00");

        addTransactionIncomeButton.setText("Add Transaction");
        addTransactionIncomeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTransactionIncomeButtonActionPerformed(evt);
            }
        });

        removeTransactionIncomeButton.setText("Remove Transaction ");
        removeTransactionIncomeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeTransactionIncomeButtonActionPerformed(evt);
            }
        });

        cashFlowLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        cashFlowLabel.setText("Cash Flow: $0.00");

        editDetailsSpendingButton.setText("Edit Details");
        editDetailsSpendingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDetailsSpendingButtonActionPerformed(evt);
            }
        });

        editDetailsIncomeButton.setText("Edit Details");
        editDetailsIncomeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDetailsIncomeButtonActionPerformed(evt);
            }
        });

        viewBudgetsButton.setText("View Budgets");
        viewBudgetsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewBudgetsButtonActionPerformed(evt);
            }
        });

        percentageIncomeSavedLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        percentageIncomeSavedLabel.setText("Percentage of Income Saved: 0%");

        makeRecurringButton.setText("Make Recurring");
        makeRecurringButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeRecurringButtonActionPerformed(evt);
            }
        });

        prevMonthButton.setText("Prev Month");
        prevMonthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevMonthButtonActionPerformed(evt);
            }
        });

        nextMonthButton.setText("Next Month");
        nextMonthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextMonthButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(numTransactionsIncomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalIncomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addComponent(numTransactionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(prevMonthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nextMonthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exitButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addTransactionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(removeTransactionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addTransactionIncomeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(removeTransactionIncomeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(editDetailsSpendingButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editDetailsIncomeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(makeRecurringButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(viewBudgetsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cashFlowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(percentageIncomeSavedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitButton)
                    .addComponent(nextMonthButton)
                    .addComponent(prevMonthButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewBudgetsButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addTransactionButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeTransactionButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editDetailsSpendingButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(makeRecurringButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numTransactionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addTransactionIncomeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeTransactionIncomeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editDetailsIncomeButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numTransactionsIncomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalIncomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(cashFlowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(percentageIncomeSavedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        MainFrame mainFrame = new MainFrame(curYear, foundYears);
    }//GEN-LAST:event_exitButtonActionPerformed

    private boolean isValidFormat(String amount, DecimalFormat df) {
        ParsePosition parsePosition = new ParsePosition(0);
        df.setParseBigDecimal(true);

        df.parse(amount, parsePosition);

        return parsePosition.getIndex() == amount.length();
    }

    private void addTransactionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTransactionButtonActionPerformed
        // TODO add your handling code here:

        //Ask for Date, Description, Amount and Category
        //Prefill month and year
        //Ask for day of month
        String[] monthInfo = month.split("\t");
        String monthName = monthInfo[0];
        String year = monthInfo[1];
        String output = "Enter the Day of the Month: "
                + "\n" + monthName + " _ " + year;
        String input = "", date = "";

        //Ask for date until valid
        while (true) {
            input = JOptionPane.showInputDialog(output, input);

            if (input == null) {
                return;
            }

            //Check for integer input
            int dayOfMonth = 0;
            try {
                dayOfMonth = Integer.parseInt(input);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
                continue;
            }

            date = monthName + " " + dayOfMonth + " " + year;

            //Check if day is valid with month/year
            try {
                DateFormat df = new SimpleDateFormat("MMM " + "dd " + "yyyy");
                df.setLenient(false);
                df.parse(date);
                break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid date. Please try again.");
                continue;
            }
        }

        //Ask for description until valid
        String description = "";
        while (true) {
            description = JOptionPane.showInputDialog("Enter a Description:", description);

            if (description == null) {
                return;
            }

            if (description.length() > DESCRIPTION_LIMIT) {
                JOptionPane.showMessageDialog(null, "Input exceeds character limit of " + DESCRIPTION_LIMIT + ". Please try again.");
                continue;
            }

            if (description.equals("")) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
                continue;
            }

            //Check for --
            int indexOfForbiddenCharacters = description.indexOf("--");
            if (indexOfForbiddenCharacters != -1) {
                JOptionPane.showMessageDialog(null, "Error: -- is not allowed to be used. Please try again.");
                continue;
            }
            break;
        }

        //Ask for amount until valid
        String amountStr = "";
        double amount = 0.0;
        while (true) {
            amountStr = JOptionPane.showInputDialog("Enter an Amount:", amountStr);

            if (amountStr == null) {
                return;
            }

            try {

                //Check for valid DecimalFormat pattern
                String pattern = "#,###.00";
                DecimalFormat df = new DecimalFormat(pattern);

                boolean isValidAmount = isValidFormat(amountStr, df);

                if (!isValidAmount) {
                    JOptionPane.showMessageDialog(null, "Invalid amount. Please try again.");
                    continue;
                }

                Number number = df.parse(amountStr);
                amount = number.doubleValue();

                if (amount <= 0.0) {
                    JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a positive number.");
                    continue;
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid amount. Please try again.");
                continue;
            }
            break;
        }

        //Ask for category
        //Read categories from text file
        String category = "";
        ArrayList<String> categories = getCategories();
        Collections.sort(categories);
        if (categories == null) {
            JOptionPane.showMessageDialog(null, "Error 405");
            return;
        }

        //If no categories have been added, make the transaction uncategorized
        if (categories.isEmpty()) {
            category = "UNCATEGORIZED";
        } else {
            //Convert categories array list to array
            String[] categoriesArr = new String[categories.size()];

            int index = 0;
            for (String s : categories) {
                categoriesArr[index] = s;
                index++;
            }

            //Ask for category
            Object[] categoriesObjArr = categoriesArr;
            Object selection = JOptionPane.showInputDialog(null,
                    "Select a Category", "Categories",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    categoriesObjArr, categoriesObjArr[0]);
            category = (String) selection;

            if (category == null) {
                return;
            }
        }

        /*
               escription);
        );
        gory);*/
        //Format amount
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String tempFormattedAmountStr = Double.toString(amount);
        String formattedAmountStr = df.format(amount);
        amount = Double.parseDouble(tempFormattedAmountStr);

        String marker = "1";
        addTransaction(month, date, description, amount, category, marker);

        removeTransactionButton.setEnabled(false);
        editDetailsSpendingButton.setEnabled(false);
    }//GEN-LAST:event_addTransactionButtonActionPerformed

    private void removeTransactionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeTransactionButtonActionPerformed
        // TODO add your handling code here:

        //If something is selected
        if (!(transactionsList.isSelectionEmpty())) {
            String selectedTransaction = transactionsList.getSelectedValue();

            String[] lineInfo = selectedTransaction.split("--");
            String date = lineInfo[0].trim();
            String description = lineInfo[1].trim();
            String amount = lineInfo[2].trim();
            String category = lineInfo[3].trim();

            //Remove $ from amount
            amount = amount.substring(1, amount.length());

            //String lineToRemove = "#" + date + "\t" + description + "\t" + amount + "\t" + category + "\t" + "1";
            String uuid = transactions.get(transactionsList.getSelectedIndex()).split("--")[4].trim();
            removeTransaction(uuid);

            removeTransactionButton.setEnabled(false);
            editDetailsSpendingButton.setEnabled(false);
        }

    }//GEN-LAST:event_removeTransactionButtonActionPerformed

    private void addTransactionIncomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTransactionIncomeButtonActionPerformed
        // TODO add your handling code here:

        //Ask for Date, Description, Amount and Category
        //Prefill month and year
        String[] monthInfo = month.split("\t");
        String monthName = monthInfo[0];
        String year = monthInfo[1];
        String output = "Enter the Day of the Month: "
                + "\n" + monthName + " _ " + year;

        String date = "", description = "", input = "";
        double amount = 0.0;

        //Ask for day of month until valid
        while (true) {
            input = JOptionPane.showInputDialog(output, input);

            if (input == null) {
                return;
            }

            //Check if its a number
            int dayOfMonth = 0;
            try {
                dayOfMonth = Integer.parseInt(input);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
                continue;
            }

            //Check if the day is valid for the month
            date = monthName + " " + dayOfMonth + " " + year;
            try {
                DateFormat df = new SimpleDateFormat("MMM " + "dd " + "yyyy");
                df.setLenient(false);
                df.parse(date);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid date. Please try again.");
                continue;
            }
            break;
        }

        //Ask for description until valid
        while (true) {
            description = JOptionPane.showInputDialog("Enter a Description:", description);

            if (description == null) {
                return;
            }
            if (description.equals("")) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
                continue;
            }
            if (description.length() > DESCRIPTION_LIMIT) {
                JOptionPane.showMessageDialog(null, "Input exceeds character limit of " + DESCRIPTION_LIMIT + ". Please try again.");
                continue;
            }

            //Check for --
            int indexOfForbiddenCharacters = description.indexOf("--");
            if (indexOfForbiddenCharacters != -1) {
                JOptionPane.showMessageDialog(null, "Error: -- is not allowed to be used. Please try again.");
                continue;
            }
            break;
        }

        //Ask for amount until valid
        String amountStr = "";
        while (true) {
            amountStr = JOptionPane.showInputDialog("Enter an Amount:", amountStr);

            if (amountStr == null) {
                return;
            }

            amount = 0.0;
            try {
                //Check for valid DecimalFormat pattern
                String pattern = "#,###.00";
                DecimalFormat df = new DecimalFormat(pattern);

                boolean isValidAmount = isValidFormat(amountStr, df);

                if (!isValidAmount) {
                    JOptionPane.showMessageDialog(null, "Invalid amount. Please try again.");
                    continue;
                }

                Number number = df.parse(amountStr);
                amount = number.doubleValue();

                if (amount <= 0.0) {
                    JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a positive number.");
                    continue;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid amount. Please try again.");
                continue;
            }
            break;
        }

        String category = "INCOME";
        String marker = "2";

        //Format amount
        /*DecimalFormat df = new DecimalFormat("#,##0.00");
        String formattedAmountStr = df.format(amount);
        amount = Double.parseDouble(formattedAmountStr);*/
        addTransaction(month, date, description, amount, category, marker);

        removeTransactionIncomeButton.setEnabled(false);
        editDetailsIncomeButton.setEnabled(false);
    }//GEN-LAST:event_addTransactionIncomeButtonActionPerformed

    private void removeTransactionIncomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeTransactionIncomeButtonActionPerformed
        // TODO add your handling code here:

        //If something is selected
        if (!(incomeList.isSelectionEmpty())) {
            String selectedTransaction = incomeList.getSelectedValue();

            String[] lineInfo = selectedTransaction.split("--");
            String date = lineInfo[0].trim();
            String description = lineInfo[1].trim();
            String amount = lineInfo[2].trim();
            String category = lineInfo[3].trim();

            //Remove $ from amount
            amount = amount.substring(1, amount.length());

            //String lineToRemove = "#" + date + "\t" + description + "\t" + amount + "\t" + category + "\t" + "2";
            String uuid = income.get(incomeList.getSelectedIndex()).split("--")[4].trim();
            removeTransaction(uuid);

            updateIncomeList();

            removeTransactionIncomeButton.setEnabled(false);
            editDetailsIncomeButton.setEnabled(false);
        }
    }//GEN-LAST:event_removeTransactionIncomeButtonActionPerformed

    private void editDetailsSpendingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDetailsSpendingButtonActionPerformed
        // TODO add your handling code here:
        //JComboBox -- Ask user what they want to change
        //Change Date, Change Description, Change Amount, Change Category

        //If something is selected
        if (!(transactionsList.isSelectionEmpty())) {
            String selectedLine = transactionsList.getSelectedValue();
            editTransaction("1", selectedLine);
        }

    }//GEN-LAST:event_editDetailsSpendingButtonActionPerformed

    private void editDetailsIncomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDetailsIncomeButtonActionPerformed
        // TODO add your handling code here:

        //If something is selected
        if (!(incomeList.isSelectionEmpty())) {
            String selectedLine = incomeList.getSelectedValue();
            editTransaction("2", selectedLine);
        }
    }//GEN-LAST:event_editDetailsIncomeButtonActionPerformed

    private void viewBudgetsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewBudgetsButtonActionPerformed
        // TODO add your handling code here:
        //Show a JOptionPane Message Dialog with all of the budgets
        //Include the limits and the amount spent

        //Key and values
        //Key = Category/Limit
        //Value = Spending
        getCategoryLimits();

        if (categoryLimits == null || categoryLimits.size() == 0) {
            JOptionPane.showMessageDialog(null, "Error: You have added no categories.");
            return;
        }

        getTransactions();

        for (String s : transactions) {
            String[] transactionInfo = s.split("--");
            String amountStr = transactionInfo[2].trim();
            String category = transactionInfo[3].trim();

            //Remove $
            amountStr = amountStr.substring(1, amountStr.length());
            String tempAmountStr = amountStr.replaceAll(",", "");
            double amount = Double.parseDouble(tempAmountStr);

            if (!(category.equalsIgnoreCase("uncategorized"))) {
                //Get current spending for the category from the hashmap
                double currentValue = categorySpending.get(category);

                //Update the key by adding the new amount
                double newValue = currentValue + amount;
                categorySpending.put(category, newValue);
            }
        }

        ArrayList<MonthlyBudget> budgetList = new ArrayList<>();
        for (String category : categorySpending.keySet()) {
            MonthlyBudget budget = new MonthlyBudget(category, categorySpending.get(category), categoryLimits.get(category));
            budgetList.add(budget);
        }
        Collections.sort(budgetList, (budget1, budget2) -> budget1.getCategory().compareTo(budget2.getCategory()));

        DefaultCategoryDataset dataset = createDataset(budgetList);

        JFreeChart chart = ChartFactory.createBarChart(
                "Budget vs Actual Spending (" + month.split("\t")[0] + " " + curYear + ")",
                "Category",
                "Amount ($)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot catPlot = chart.getCategoryPlot();
        //Rotate labels
        CategoryAxis domainAxis = catPlot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        //Change color of bars
        BarRenderer renderer = (BarRenderer) catPlot.getRenderer();
        renderer.setSeriesPaint(0, new Color(74, 144, 226));
        renderer.setSeriesPaint(1, new Color(255, 165, 0));

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame("Budget vs Actual Spending (" + month.split("\t")[0] + " " + curYear + ")");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the popup without exiting the application
        frame.getContentPane().add(chartPanel); // Add the ChartPanel to the frame
        frame.pack(); // Size the frame according to its content
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true); // Make the frame visible

    }//GEN-LAST:event_viewBudgetsButtonActionPerformed

    private DefaultCategoryDataset createDataset(ArrayList<MonthlyBudget> budgetList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (MonthlyBudget budget : budgetList) {
            if (budget.getActualSpending() >= 0.0) {
                dataset.addValue(budget.getActualSpending(), "Actual", budget.getCategory());
                dataset.addValue(budget.getBudgetedSpending(), "Budget", budget.getCategory());
            }
        }

        return dataset;
    }

    private void makeRecurringButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeRecurringButtonActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Currently unavailable.");
        /*if (transactionsList.isSelectionEmpty())
            return;
        String selectedTransaction = transactionsList.getSelectedValue();
        String[] transactionInfo = selectedTransaction.split("--");
        String selectedDescription = transactionInfo[1].trim();
        String selectedAmount = transactionInfo[2].trim();
        //See if the transaction is already recurring
        ArrayList<String> recurringTransactions = getRecurringTransactions();
        if (recurringTransactions == null) {
            JOptionPane.showMessageDialog(null, "Error making this a recurring transaction.");
            return;
        }
        for (String s: recurringTransactions) {
            String[] lineInfo = s.split("--");
            String date = lineInfo[0].trim();
            String description = lineInfo[1].trim();
            String amountStr = lineInfo[2].trim();
            String category = lineInfo[3].trim();
            String recurringMarker = lineInfo[4].trim();
            if (selectedDescription.equals(description) && selectedAmount.equals(amountStr)) {
                JOptionPane.showMessageDialog(null, "Error: Transaction is already recurring.");
                return;
            }
        }
        String transactionLine = addRecurringTransactionToFile(selectedTransaction);
        updateRecurringTransactionOnList(transactionLine, true);
        JOptionPane.showMessageDialog(null, "Transaction is now recurring.");*/

    }//GEN-LAST:event_makeRecurringButtonActionPerformed

    private void prevMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevMonthButtonActionPerformed
        // TODO add your handling code here:
        indexOfMonth--;

        //Update cur year
        curYear = months.get(indexOfMonth).split("\t")[1];

        this.dispose();
        TransactionsFrame transactionsFrame = new TransactionsFrame(months.get(indexOfMonth), curYear, foundYears);
    }//GEN-LAST:event_prevMonthButtonActionPerformed

    private void nextMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextMonthButtonActionPerformed
        // TODO add your handling code here:
        indexOfMonth++;

        //Update cur year
        curYear = months.get(indexOfMonth).split("\t")[1];

        this.dispose();
        TransactionsFrame transactionsFrame = new TransactionsFrame(months.get(indexOfMonth), curYear, foundYears);
    }//GEN-LAST:event_nextMonthButtonActionPerformed

    private void updateRecurringTransactionOnList(String transactionLine, boolean makeRecurring) {
        //Add back transaction with recurring marker (-- ***)
        //Get current details
        String date, description, amountStr, category = null, recurringMarker = "";
        String[] transactionInfo = transactionLine.split("--");
        date = transactionInfo[0].trim();
        description = transactionInfo[1].trim();
        amountStr = transactionInfo[2].trim().replaceAll("\\$", "");
        category = transactionInfo[3].trim();

        if (makeRecurring) {
            category = category + " -- ***";
        }

        //Copy of current details
        String origDate = transactionInfo[0].trim();
        String origDescription = transactionInfo[1].trim();
        String origAmountStr = transactionInfo[2].trim().replaceAll("\\$", "");
        String origCategory = transactionInfo[3].trim();

        //Format amount
        DecimalFormat df = new DecimalFormat("0.00");
        String tempAmountStr = amountStr.replaceAll(",", "");
        String formattedAmountStr = df.format(Double.parseDouble(tempAmountStr));

        addTransaction(month, date, description, Double.parseDouble(formattedAmountStr), category, "1");

        String lineToRemove = "#" + origDate + "\t" + origDescription + "\t" + origAmountStr + "\t" + origCategory + "\t" + 1;
        removeTransaction(lineToRemove);

    }

    private String addRecurringTransactionToFile(String transactionToAdd) {
        transactionToAdd = transactionToAdd + " -- ***";
        PrintWriter printer = null;
        try {
            ArrayList<String> recurringTransactions = getRecurringTransactions();
            printer = new PrintWriter(new File("recurring.txt"));
            for (String s : recurringTransactions) {
                printer.println(s);
            }
            printer.println(transactionToAdd);
            printer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TransactionsFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            printer.close();
        }
        return transactionToAdd;
    }

    private ArrayList<String> getRecurringTransactions() {
        try {
            Scanner sc = new Scanner(new File("recurring.txt"));
            ArrayList<String> recurringTransactions = new ArrayList<>();
            while (sc.hasNextLine()) {
                recurringTransactions.add(sc.nextLine());
            }
            sc.close();
            return recurringTransactions;
        } catch (FileNotFoundException ex) {

            return null;
        }
    }

    private void getCategoryLimits() {
        try {
            Scanner sc = new Scanner(new File("categorylimits.txt"));

            String line = "";
            categoryLimits.clear();
            while (sc.hasNextLine()) {
                line = sc.nextLine();

                String[] lineInfo = line.split("\t");
                String categoryName = lineInfo[0];
                String categoryLimit = lineInfo[1];
                categoryLimits.put(categoryName, Double.parseDouble(categoryLimit));
                categorySpending.put(categoryName, 0.0);
            }

            sc.close();

        } catch (FileNotFoundException ex) {

        }
    }

    private void editTransaction(String marker, String selectedLine) {

        //Ask what user wants to edit for the transaction
        String[] optionsArr = new String[5 - Integer.parseInt(marker)];
        if (marker.equals("1")) {
            optionsArr[0] = "Change Date";
            optionsArr[1] = "Change Description";
            optionsArr[2] = "Change Amount";
            optionsArr[3] = "Change Category";
        } else if (marker.equals("2")) {
            optionsArr[0] = "Change Date";
            optionsArr[1] = "Change Description";
            optionsArr[2] = "Change Amount";
        }

        Object[] options = optionsArr;
        Object selection = JOptionPane.showInputDialog(null,
                "Select an Option", "Options",
                JOptionPane.INFORMATION_MESSAGE, null,
                options, options[0]);

        String selectionStr = (String) selection;

        if (selectionStr == null) {
            return;
        }

        //Get current details
        String[] transactionInfo = selectedLine.split("--");
        String date = transactionInfo[0].trim();
        String description = transactionInfo[1].trim();
        String amountStr = transactionInfo[2].trim().replaceAll("\\$", "");
        String category = transactionInfo[3].trim();

        //Copy of current details
        String origDate = transactionInfo[0].trim();
        String origDescription = transactionInfo[1].trim();
        String origAmountStr = transactionInfo[2].trim().replaceAll("\\$", "");
        String origCategory = transactionInfo[3].trim();

        //Ask for date until input is valid
        if (selectionStr.equals(optionsArr[0])) {
            while (true) {
                //Prefill month and year
                //Ask for day of month
                String[] dateArr = origDate.split(" ");
                String monthName = dateArr[0];
                String dayOfMonth = dateArr[1];
                String year = dateArr[2];
                String output = "Edit the Day of the Month for: "
                        + "\n" + description 
                        + "\n" + monthName + " _ " + year;
                String input = JOptionPane.showInputDialog(null, output, dayOfMonth);

                if (input == null || input.equals(dayOfMonth)) {
                    return;
                }

                //Check if its a number
                int tempDay = 0;
                try {
                    tempDay = Integer.parseInt(input);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
                    continue;
                }

                //Check if the day is valid for the month
                date = monthName + " " + tempDay + " " + year;
                try {
                    DateFormat df = new SimpleDateFormat("MMM " + "dd " + "yyyy");
                    df.setLenient(false);
                    df.parse(date);
                    break;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Invalid date. Please try again.");
                    continue;
                }
            }
        }

        //Ask for description until input is valid
        if (selectionStr.equals(optionsArr[1])) {
            while (true) {
                description = JOptionPane.showInputDialog(null, "Edit the Description for: \n" + date + " -- " + origDescription, origDescription);

                //If null or if value does not change
                if (description == null || description.equals(origDescription)) {
                    return;
                }

                if (description.equals("")) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
                    continue;
                }

                if (description.length() > DESCRIPTION_LIMIT) {
                    JOptionPane.showMessageDialog(null, "Input exceeds character limit of " + DESCRIPTION_LIMIT + ". Please try again.");
                    continue;
                }

                //Check for --
                int indexOfForbiddenCharacters = description.indexOf("--");
                if (indexOfForbiddenCharacters != -1) {
                    JOptionPane.showMessageDialog(null, "Error: -- is not allowed to be used.");
                    continue;
                }
                break;
            }
        }

        //Ask for amount until input is valid
        if (selectionStr.equals(optionsArr[2])) {
            String amountStrCopy = amountStr;
            while (true) {
                amountStr = JOptionPane.showInputDialog(null, "Edit the Amount for: \n" + date + " -- " + description, amountStrCopy);

                //Check for valid DecimalFormat pattern
                String pattern = "#,###.00";
                DecimalFormat df = new DecimalFormat(pattern);

                //Can optimize by checking if orig amount is equal to new amount
                /*System.out.println("ORIG AMOUNT: " + amountStrCopy);
                System.out.println("NEW AMOUNT: " + amountStr);
                String formattedOrigAmount = df.format(amountStrCopy);
                String formattedNewAmount = df.format(amountStr);
                
                if (amountStr == null || formattedOrigAmount.equals(formattedNewAmount)) {
                    System.out.println("DONT CHANGE");
                    return;
                }*/
                if (amountStr == null) {
                    return;
                }

                boolean isValidAmount = isValidFormat(amountStr, df);

                if (!isValidAmount) {
                    JOptionPane.showMessageDialog(null, "Invalid amount. Please try again.");
                    continue;
                }

                Number number;
                try {
                    number = df.parse(amountStr);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid amount. Please try again.");
                    continue;
                }
                double amount = number.doubleValue();

                if (amount <= 0.0) {
                    JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a positive number.");
                    continue;
                }

                break;
            }
        }

        //Change Category
        if (marker.equals("1")) {
            if (selectionStr.equals(optionsArr[3])) {
                //Ask for category
                //Read categories from text file
                category = "";
                ArrayList<String> categories = getCategories();
                if (categories == null) {
                    JOptionPane.showMessageDialog(null, "Error 405");
                    return;
                }

                //If no categories have been added, make the transaction uncategorized
                if (categories.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Error: No categories have been added.");
                    return;
                } else {
                    //Convert categories array list to array
                    Collections.sort(categories);
                    String[] categoriesArr = new String[categories.size()];

                    int index = 0;
                    for (String s : categories) {
                        categoriesArr[index] = s;
                        index++;
                    }

                    //Ask for category
                    Object[] categoriesObjArr = categoriesArr;
                    Object selection2 = JOptionPane.showInputDialog(null,
                            "Edit the category for: \n" + date + " -- " + description, "Categories",
                            JOptionPane.INFORMATION_MESSAGE, null,
                            categoriesObjArr, categoriesObjArr[java.util.Arrays.asList(categoriesObjArr).indexOf(origCategory)]);
                    category = (String) selection2;

                    if (category == null || category.equals(origCategory)) {
                        return;
                    }
                }
            }
        }

        //Format amount
        DecimalFormat df = new DecimalFormat("0.00");
        String tempAmountStr = amountStr.replaceAll(",", "");
        String formattedAmountStr = df.format(Double.parseDouble(tempAmountStr));
        
        String uuid = null;
        System.out.println("MARKER: " + marker);
        if (marker.equals("1")) {
            uuid = transactions.get(transactionsList.getSelectedIndex()).split("--")[4].trim();
        } else {
            uuid = income.get(incomeList.getSelectedIndex()).split("--")[4].trim();
        }
        removeTransaction(uuid);

        addTransaction(month, date, description, Double.parseDouble(formattedAmountStr), category, marker);

        if (marker.equals("1")) {
            removeTransactionButton.setEnabled(false);
            editDetailsSpendingButton.setEnabled(false);
        } else {
            removeTransactionIncomeButton.setEnabled(false);
            editDetailsIncomeButton.setEnabled(false);
        }

    }

    private void addTransaction(String month, String date, String description,
            double amount, String category, String marker) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String newTransaction = "#" + date + "\t" + description + "\t" + df.format(amount) + "\t" + category + "\t" + marker + "\t" + generateUUID();
        try {
            Scanner sc = new Scanner(new File("months.txt"));

            String line = "";
            ArrayList<String> lines = new ArrayList<>();
            boolean addedTransaction = false;
            while (sc.hasNextLine()) {
                line = sc.nextLine();

                //Check for month
                if (line.charAt(0) != '#') {
                    if (line.equals(month)) {
                        lines.add(line);
                        //Adds transaction at first line after month divider
                        if (!addedTransaction) {
                            lines.add(newTransaction);
                        }
                        addedTransaction = true;
                    } else {
                        lines.add(line);
                    }
                } else {
                    lines.add(line);
                }
            }

            sc.close();

            PrintWriter printer = new PrintWriter(new File("months.txt"));

            for (String s : lines) {
                printer.println(s);
            }

            printer.close();

            if (marker.equals("1")) {
                updateTransactionsList();
            }
            if (marker.equals("2")) {
                updateIncomeList();
            }
        } catch (FileNotFoundException ex) {
        }
    }

    private void removeTransaction(String uuid) {
        try {
            Scanner sc = new Scanner(new File("months.txt"));

            System.out.println("UUID TO REMOVE: " + uuid);
            ArrayList<String> lines = new ArrayList<>();
            UUID uuidToRemove = UUID.fromString(uuid.trim());
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                UUID foundUUID = null;

                if (line.charAt(0) == '#') {
                    foundUUID = UUID.fromString(line.split("\t")[5].trim());
                    //System.out.println("FOUND UUID: " + foundUUID);
                } else {
                    lines.add(line);
                    continue;
                }

                if (foundUUID != null) {
                    if (!foundUUID.equals(uuidToRemove)) {
                        lines.add(line);
                    } else {
                        System.out.println("FOUND UUID. DONT ADD LINE HERE");
                    }
                }
            }

            sc.close();

            PrintWriter printer = new PrintWriter(new File("months.txt"));

            for (String s : lines) {
                printer.println(s);
            }

            printer.close();

            updateTransactionsList();
            updateIncomeList();

        } catch (FileNotFoundException ex) {

        }
    }

    private ArrayList<String> getCategories() {
        try {
            Scanner sc = new Scanner(new File("categories.txt"));

            String line = "";
            ArrayList<String> categories = new ArrayList<>();
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                categories.add(line);
            }

            sc.close();

            return categories;

        } catch (FileNotFoundException ex) {

        }
        return null;
    }

    private void updateNumTransactionsLabel(int numTransactions) {
        numTransactionsLabel.setText("# Transactions: " + numTransactions);
    }

    private void updateTransactionsList() {
        getTransactions();

        //Format of Line:
        //Aug 10, 2020 (tab) Description (tab) $10.00 (tab) Category
        //Sort transactions by date
        Collections.sort(transactions, new Comparator<String>() {
            public int compare(String o1, String o2) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM " + "dd " + "yyyy");
                    return sdf.parse(o1).compareTo(sdf.parse(o2));  //sdf.parse returns date - So, Compare Date with date
                } catch (ParseException ex) {
                    return o1.compareTo(o2);
                }
            }
        });

        //Format transactions
        String[] listArr = new String[transactions.size()];

        int index = 0;
        for (String s : transactions) {
            //Remove uuid
            int lastSeparatorIndex = s.lastIndexOf("--");
            listArr[index] = s.substring(0, lastSeparatorIndex);
            index++;
        }

        transactionsList.setListData(listArr);

        updateNumTransactionsLabel(transactions.size());
        updateTotalLabel();
        updateCashFlowLabel();
        updatePercentageIncomeSavedLabel();
    }

    private void updateIncomeList() {
        getIncome();

        Collections.sort(income, new Comparator<String>() {
            public int compare(String o1, String o2) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM " + "dd " + "yyyy");
                    return sdf.parse(o1).compareTo(sdf.parse(o2));  //sdf.parse returns date - So, Compare Date with date
                } catch (ParseException ex) {
                    return o1.compareTo(o2);
                }
            }
        });

        //Convert array list to array
        String[] listArr = new String[income.size()];

        int index = 0;
        for (String s : income) {
            int lastSeparatorIndex = s.lastIndexOf("--");
            listArr[index] = s.substring(0, lastSeparatorIndex);
            index++;
        }

        incomeList.setListData(listArr);

        updateIncomeTotalLabel();
        updateNumTransactionsIncomeLabel(income.size());
        updateCashFlowLabel();
        updatePercentageIncomeSavedLabel();
    }

    private void getTransactions() {
        try {
            Scanner sc = new Scanner(new File("months.txt"));

            transactions.clear();
            String line = "";
            boolean foundMonth = false;

            totalSpending = 0.0;

            while (sc.hasNextLine()) {
                line = sc.nextLine();

                //MONTHS DONT HAVE MARKERS
                //TRANSACTIONS DO HAVE THE # MARKERS
                if (line.charAt(0) != '#') {
                    foundMonth = false;
                }

                if (foundMonth) {
                    //Remove #
                    String formattedLine = line.substring(1, line.length());

                    String[] lineInfo = formattedLine.split("\t");
                    String date = lineInfo[0];
                    String description = lineInfo[1];
                    String amount = lineInfo[2];
                    String category = lineInfo[3];
                    String marker = lineInfo[4];
                    String uuid = lineInfo[5];

                    //1 is spending
                    //2 is income
                    //Add --
                    formattedLine = date + " -- " + description + " -- $" + amount + " -- " + category + " -- " + uuid;

                    if (marker.equals("1")) {
                        //Remove commas from amount
                        String amountStr = amount.replaceAll(",", "");
                        totalSpending += Double.parseDouble(amountStr);
                        transactions.add(formattedLine);
                    }
                }

                if (line.charAt(0) != '#') {
                    if (line.equals(month)) {
                        foundMonth = true;
                    }
                }
            }

            sc.close();
        } catch (FileNotFoundException ex) {

        }
    }

    private void getIncome() {
        try {
            Scanner sc = new Scanner(new File("months.txt"));

            income.clear();
            String line = "";
            boolean foundMonth = false;

            totalIncome = 0.0;

            while (sc.hasNextLine()) {
                line = sc.nextLine();

                //MONTHS DONT HAVE MARKERS
                //TRANSACTIONS DO HAVE THE # MARKERS
                if (line.charAt(0) != '#') {
                    foundMonth = false;
                }

                if (foundMonth) {
                    //Remove #
                    String formattedLine = line.substring(1, line.length());

                    String[] lineInfo = formattedLine.split("\t");
                    String date = lineInfo[0];
                    String description = lineInfo[1];
                    String amount = lineInfo[2];
                    String category = lineInfo[3];
                    String marker = lineInfo[4];
                    String uuid = lineInfo[5];

                    //1 is spending
                    //2 is income
                    //Add --
                    formattedLine = date + " -- " + description + " -- $" + amount + " -- " + category + " -- " + uuid;

                    if (marker.equals("2")) {
                        String amountStr = amount.replaceAll(",", "");
                        totalIncome += Double.parseDouble(amountStr);
                        income.add(formattedLine);
                    }
                }

                if (line.charAt(0) != '#') {
                    if (line.equals(month)) {

                        foundMonth = true;
                    }
                }
            }

            sc.close();
        } catch (FileNotFoundException ex) {

        }
    }

    private void updateTotalLabel() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String totalAmountStr = df.format(totalSpending);

        totalLabel.setText("Total: $" + totalAmountStr);
    }

    private void updateIncomeTotalLabel() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String totalIncomeStr = df.format(totalIncome);

        totalIncomeLabel.setText("Total: $" + totalIncomeStr);
    }

    private void updateNumTransactionsIncomeLabel(int numTransactions) {
        numTransactionsIncomeLabel.setText("# Transactions: " + numTransactions);
    }

    private void updateCashFlowLabel() {
        double cashFlow = totalIncome - totalSpending;
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String cashFlowStr = df.format(cashFlow);

        cashFlowLabel.setText("Cash Flow: $" + cashFlowStr);
    }

    private void updatePercentageIncomeSavedLabel() {
        double cashFlow = totalIncome - totalSpending;

        double percentageIncomeSaved = 0.0;
        if (totalIncome != 0.0 && cashFlow > 0.0) {
            percentageIncomeSaved = (cashFlow / totalIncome) * 100.0;
        }

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String percentageIncomeSavedStr = df.format(percentageIncomeSaved);

        percentageIncomeSavedLabel.setText("Percentage of Income Saved: " + percentageIncomeSavedStr + "%");

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTransactionButton;
    private javax.swing.JButton addTransactionIncomeButton;
    private javax.swing.JLabel cashFlowLabel;
    private javax.swing.JButton editDetailsIncomeButton;
    private javax.swing.JButton editDetailsSpendingButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JList<String> incomeList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton makeRecurringButton;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JButton nextMonthButton;
    private javax.swing.JLabel numTransactionsIncomeLabel;
    private javax.swing.JLabel numTransactionsLabel;
    private javax.swing.JLabel percentageIncomeSavedLabel;
    private javax.swing.JButton prevMonthButton;
    private javax.swing.JButton removeTransactionButton;
    private javax.swing.JButton removeTransactionIncomeButton;
    private javax.swing.JLabel totalIncomeLabel;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JList<String> transactionsList;
    private javax.swing.JButton viewBudgetsButton;
    // End of variables declaration//GEN-END:variables
}
