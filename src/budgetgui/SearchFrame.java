/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budgetgui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author jgower17
 */
public class SearchFrame extends javax.swing.JFrame {

    private boolean spendingSelected;
    private boolean incomeSelected;
    private String curYear;

    /**
     * Creates new form SearchFrame
     */
    public SearchFrame(String curYear, boolean prefillYear) {
        initComponents();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        this.curYear = curYear;

        this.setVisible(true);
        this.setTitle("Search Transactions");
        this.setResizable(false);

        spendingButton.setSelected(true);
        spendingSelected = true;
        incomeSelected = false;

        ArrayList<String> yearsArrList = getSavedYears();
        String[] yearsArr = new String[yearsArrList.size() + 1];
        int index = 1;
        yearsArr[0] = "All";
        for (String s : yearsArrList) {
            yearsArr[index] = s;
            index++;
        }

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(yearsArr);
        yearComboBox.setModel(model);
        //Prefill year
        if (prefillYear) {
            yearComboBox.setSelectedIndex(java.util.Arrays.asList(yearsArr).indexOf(curYear));
        }

        ArrayList<String> categoriesArrList = getCategories();
        categoriesArrList.add("UNCATEGORIZED");
        Collections.sort(categoriesArrList);
        String[] categoriesArr = new String[categoriesArrList.size() + 1];
        index = 1;
        categoriesArr[0] = "All";
        for (String s : categoriesArrList) {
            categoriesArr[index] = s;
            index++;
        }

        DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>(categoriesArr);
        categoriesComboBox.setModel(model2);
    }

    private ArrayList<String> getSavedYears() {
        try {
            Scanner sc = new Scanner(new File("years.txt"));
            String line = "";
            ArrayList<String> savedYears = new ArrayList<>();
            while (sc.hasNextLine()) {
                savedYears.add(sc.nextLine());
            }
            sc.close();
            return savedYears;
        } catch (FileNotFoundException ex) {

        }
        return null;
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
            System.out.println("File not found.");
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exitButton = new javax.swing.JButton();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        transactionsList = new javax.swing.JList<>();
        yearComboBox = new javax.swing.JComboBox<>();
        minLabel = new javax.swing.JLabel();
        yearLabel = new javax.swing.JLabel();
        maxLabel = new javax.swing.JLabel();
        minField = new javax.swing.JTextField();
        maxField = new javax.swing.JTextField();
        spendingButton = new javax.swing.JRadioButton();
        incomeButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        categoriesComboBox = new javax.swing.JComboBox<>();
        clearButton = new javax.swing.JButton();
        exportButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        transactionsList.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jScrollPane1.setViewportView(transactionsList);

        minLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        minLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minLabel.setText("Min $");

        yearLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        yearLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yearLabel.setText("Year");

        maxLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        maxLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        maxLabel.setText("Max $");

        spendingButton.setText("Spending");
        spendingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spendingButtonActionPerformed(evt);
            }
        });

        incomeButton.setText("Income");
        incomeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomeButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Category");

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        exportButton.setText("Export Transactions");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(incomeButton)
                            .addComponent(spendingButton)
                            .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(yearLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(minField, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(maxField, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(categoriesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(minLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(maxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(24, 24, 24)
                                                .addComponent(exportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 85, Short.MAX_VALUE)
                                                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(33, 33, 33)
                                                .addComponent(jLabel1)
                                                .addGap(0, 0, Short.MAX_VALUE))))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchTextField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(exitButton)
                            .addComponent(clearButton)
                            .addComponent(exportButton))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(minLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yearLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(minField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maxField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(categoriesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchButton)
                            .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(spendingButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(incomeButton)
                        .addGap(1, 1, 1)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        if (curYear != null) {
            MainFrame mainFrame = new MainFrame(curYear);
        } else {
            YearFrame yearFrame = new YearFrame();
        }
        
    }//GEN-LAST:event_exitButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        //Check for spending/income selection
        if (!spendingSelected && !incomeSelected) {
            JOptionPane.showMessageDialog(null, "Please select spending/income.");
            return;
        }

        //Check for valid min/max fields
        double minValue = 0.0;
        double maxValue = 0.0;
        if (!(minField.getText().equals("") && maxField.getText().equals(""))) {
            try {
                if (minField.getText().equals("")) {
                    minValue = 0;
                } else {
                    minValue = Double.parseDouble(minField.getText());
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid min value. Please try again.");
                return;
            }
            try {
                if (maxField.getText().equals("")) {
                    maxValue = Integer.MAX_VALUE;
                } else {
                    maxValue = Double.parseDouble(maxField.getText());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid max value. Please try again.");
                return;
            }
            System.out.println("MIN: " + minValue);
            System.out.println("MAX: " + maxValue);
            if (minValue > maxValue) {
                JOptionPane.showMessageDialog(null, "Min value cannot be greater than max value.");
                return;
            }
            updateList(searchTextField.getText(), minValue, maxValue);
        } else {
            updateList(searchTextField.getText(), -1, -1);
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    private void spendingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spendingButtonActionPerformed
        // TODO add your handling code here:
        if (spendingButton.isSelected()) {
            spendingSelected = true;
            categoriesComboBox.setEnabled(true);
        } else {
            spendingSelected = false;
        }
        if (incomeSelected) {
            incomeButton.setSelected(false);
            incomeSelected = false;
        }
    }//GEN-LAST:event_spendingButtonActionPerformed

    private void incomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomeButtonActionPerformed
        // TODO add your handling code here:
        if (incomeButton.isSelected()) {
            incomeSelected = true;
            categoriesComboBox.setEnabled(false);
            categoriesComboBox.setSelectedIndex(0);
        } else {
            incomeSelected = false;
            categoriesComboBox.setEnabled(true);
        }
        if (spendingSelected) {
            spendingButton.setSelected(false);
            spendingSelected = false;
        }
    }//GEN-LAST:event_incomeButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        // TODO add your handling code here:
        minField.setText("");
        maxField.setText("");
        searchTextField.setText("");
        spendingButton.setSelected(false);
        incomeButton.setSelected(false);
        yearComboBox.setSelectedIndex(0);
        categoriesComboBox.setSelectedIndex(0);
        String[] emptyArr = new String[0];
        categoriesComboBox.setEnabled(true);
        transactionsList.setListData(emptyArr);
    }//GEN-LAST:event_clearButtonActionPerformed

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exportButtonActionPerformed

    private void updateList(String input, double min, double max) {
        //Search through months.txt file
        try {
            Scanner sc = new Scanner(new File("months.txt"));
            String line = "";
            ArrayList<String> returnedTransactions = new ArrayList<>();
            Double transactionTotal = 0.0;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                //Check for transaction lines
                if (line.charAt(0) == '#') {
                    //Remove # from beginning of line
                    line = line.substring(1, line.length());
                    String[] lineInfo = line.split("\t");
                    String date = lineInfo[0];
                    String description = lineInfo[1];
                    String amountStr = lineInfo[2];
                    String category = lineInfo[3];
                    int transactionType = Integer.parseInt(lineInfo[4]);

                    String formattedLine = date + " -- " + description + " -- $" + amountStr + " -- " + category;

                    //Remove commas from amount
                    amountStr = lineInfo[2].replaceAll(",", "");
                    Double amount = Double.parseDouble(amountStr);

                    //Case doesn't matter for the search
                    String tempFormattedLine = formattedLine.toLowerCase();
                    input = input.toLowerCase();
                    if (tempFormattedLine.contains(input)) {
                        boolean validTransaction = true;
                        //Check to see if the year matches
                        if (!yearComboBox.getSelectedItem().equals("All")) {
                            int selectedYear = Integer.valueOf((String) yearComboBox.getSelectedItem());
                            String[] dateInfo = date.split(" ");
                            String month = dateInfo[0];
                            int day = Integer.parseInt(dateInfo[1]);
                            int year = Integer.parseInt(dateInfo[2]);

                            if (selectedYear != year) {
                                validTransaction = false;
                            }
                        }

                        //Check min field and max field
                        //Already have valid input
                        if (min != -1) {
                            if (!(amount >= min && amount <= max)) {
                                validTransaction = false;
                            }
                        }

                        //Check category for spending/income selection
                        if (incomeSelected && !category.equalsIgnoreCase("income")) {
                            validTransaction = false;
                        }
                        if (spendingSelected && category.equalsIgnoreCase("income")) {
                            validTransaction = false;
                        }

                        //Compare category for combo box selection on spending transactions
                        if (spendingSelected) {
                            String selectedCategory = (String) categoriesComboBox.getSelectedItem();
                            System.out.println("SELECTED CATEGORY: " + selectedCategory);
                            if (!selectedCategory.equals("All")) {
                                if (!category.equalsIgnoreCase(selectedCategory)) {
                                    validTransaction = false;
                                }
                            }
                        }

                        if (validTransaction) {
                            returnedTransactions.add(formattedLine);
                            if (!category.equalsIgnoreCase("retirement")) {
                                transactionTotal += amount;
                            }
                        }
                    }

                }
            }
            //Sort transactions by date
            Collections.sort(returnedTransactions, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM " + "dd " + "yyyy");
                        return sdf.parse(o1).compareTo(sdf.parse(o2));  //sdf.parse returns date - So, Compare Date with date
                    } catch (ParseException ex) {
                        return o1.compareTo(o2);
                    }
                }
            });

            //Add dividers
            //Monthly subtotals
            //Month headers
            //Total amount at bottom
            int currentIndex = 0;
            int currentYear = 0;
            String currentMonth = "";
            HashMap<Integer, String> dividers = new HashMap<>();

            DecimalFormat df = new DecimalFormat("#,##0.00");

            ArrayList<String> returnedTransactionsCopy = new ArrayList<>();
            double monthlyTotal = 0.0;
            for (String s : returnedTransactions) {
                if (currentIndex == 0) {
                    String[] lineInfo = s.split("--");
                    String[] dateInfo = lineInfo[0].split(" ");
                    currentYear = Integer.parseInt(dateInfo[2]);
                    currentMonth = dateInfo[0];
                    String divider = "----- " + currentMonth + " " + currentYear + " -----";
                    returnedTransactionsCopy.add(divider);
                }

                String[] lineInfo = s.split("--");
                String[] dateInfo = lineInfo[0].split(" ");
                String foundMonth = dateInfo[0];
                String amountStr = lineInfo[2];
                String category = lineInfo[3].trim();
                amountStr = amountStr.substring(2, amountStr.length());
                amountStr = amountStr.replaceAll(",", "");
                double amount = Double.parseDouble(amountStr);
                int foundYear = Integer.parseInt(dateInfo[2]);

                if (!currentMonth.equals(foundMonth) || currentYear != foundYear) {
                    //Add monthly subtotal
                    String subtotalDivider = currentMonth + " " + currentYear + " Total: $" + df.format(monthlyTotal);
                    returnedTransactionsCopy.add(subtotalDivider);
                    monthlyTotal = 0.0;
                    //Need to add divider here
                    currentYear = Integer.parseInt(dateInfo[2]);
                    String monthDivider = "----- " + foundMonth + " " + currentYear + " -----";
                    returnedTransactionsCopy.add(monthDivider);
                    currentMonth = foundMonth;

                }
                if (!category.equalsIgnoreCase("retirement")) {
                    monthlyTotal += amount;
                }

                returnedTransactionsCopy.add(s);
                currentIndex++;
                //End of transactions
                if (currentIndex == returnedTransactions.size()) {
                    String subtotalDivider = currentMonth + " " + currentYear + " Total: $" + df.format(monthlyTotal);
                    returnedTransactionsCopy.add(subtotalDivider);
                }
            }

            String totalStr = "Spending";
            if (incomeSelected) {
                totalStr = "Income";
            }
            if (!returnedTransactionsCopy.isEmpty()) {
                returnedTransactionsCopy.add("Total " + totalStr + ": $" + df.format(transactionTotal));
            }

            String[] listArr = new String[returnedTransactionsCopy.size()];

            int index = 0;
            for (String s : returnedTransactionsCopy) {
                listArr[index] = s;
                index++;
            }

            transactionsList.setListData(listArr);

            if (returnedTransactions.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No results found.");
            }

        } catch (FileNotFoundException ex) {

        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> categoriesComboBox;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton exportButton;
    private javax.swing.JRadioButton incomeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField maxField;
    private javax.swing.JLabel maxLabel;
    private javax.swing.JTextField minField;
    private javax.swing.JLabel minLabel;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JRadioButton spendingButton;
    private javax.swing.JList<String> transactionsList;
    private javax.swing.JComboBox<String> yearComboBox;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables
}
