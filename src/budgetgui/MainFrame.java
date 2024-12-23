/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budgetgui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author jacksongower
 */
public class MainFrame extends javax.swing.JFrame {

    private ArrayList<String> months = new ArrayList<>();
    private ArrayList<String> foundYears = new ArrayList<>();
    private String curYear;
    private String prevYear = null;
    private String nextYear = null;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {

    }

    public MainFrame(String year, ArrayList<String> foundYears) {
        initComponents();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        this.setVisible(true);
        this.setTitle("Budget Tracker");
        this.setResizable(false);

        titleLabel.setText(year);
        curYear = year;

        removeMonthButton.setEnabled(false);
        viewTransactionsButton.setEnabled(false);

        //Set prev/next year buttons
        Collections.sort(foundYears);
        int yearIndex = foundYears.indexOf(year);
        if (yearIndex >= 1) {
            prevYear = foundYears.get(yearIndex - 1);
        }
        if (yearIndex < foundYears.size() - 1) {
            nextYear = foundYears.get(yearIndex + 1);
        }
        if (prevYear == null) {
            prevYearButton.setEnabled(false);
        }
        if (nextYear == null) {
            nextYearButton.setEnabled(false);
        }
        this.foundYears = foundYears;

        monthsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    removeMonthButton.setEnabled(true);
                    viewTransactionsButton.setEnabled(true);
                }
            }
        });

        updateList();
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
        monthsList = new javax.swing.JList<>();
        titleLabel = new javax.swing.JLabel();
        addMonthButton = new javax.swing.JButton();
        removeMonthButton = new javax.swing.JButton();
        numMonthsLabel = new javax.swing.JLabel();
        viewTransactionsButton = new javax.swing.JButton();
        viewYearlyStatisticsButton = new javax.swing.JButton();
        autoCompleteYearButton = new javax.swing.JButton();
        importTransactionsButton = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        prevYearButton = new javax.swing.JButton();
        nextYearButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        monthsList.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jScrollPane1.setViewportView(monthsList);

        titleLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Months");

        addMonthButton.setText("Add Month");
        addMonthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMonthButtonActionPerformed(evt);
            }
        });

        removeMonthButton.setText("Remove Month");
        removeMonthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeMonthButtonActionPerformed(evt);
            }
        });

        numMonthsLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        numMonthsLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        numMonthsLabel.setText("Number of Months: 0");

        viewTransactionsButton.setText("View Transactions");
        viewTransactionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewTransactionsButtonActionPerformed(evt);
            }
        });

        viewYearlyStatisticsButton.setText("View Yearly Statistics");
        viewYearlyStatisticsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewYearlyStatisticsButtonActionPerformed(evt);
            }
        });

        autoCompleteYearButton.setText("Auto Complete Year");
        autoCompleteYearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoCompleteYearButtonActionPerformed(evt);
            }
        });

        importTransactionsButton.setText("Import Transactions");
        importTransactionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importTransactionsButtonActionPerformed(evt);
            }
        });

        searchButton.setText("Search Transactions");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        prevYearButton.setText("Prev Year");
        prevYearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevYearButtonActionPerformed(evt);
            }
        });

        nextYearButton.setText("Next Year");
        nextYearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextYearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(numMonthsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(prevYearButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(9, 9, 9)
                        .addComponent(nextYearButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addMonthButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeMonthButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(viewTransactionsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(viewYearlyStatisticsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(autoCompleteYearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(importTransactionsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitButton)
                    .addComponent(titleLabel)
                    .addComponent(prevYearButton)
                    .addComponent(nextYearButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addMonthButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeMonthButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewTransactionsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(viewYearlyStatisticsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(autoCompleteYearButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(importTransactionsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(numMonthsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void addMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMonthButtonActionPerformed
        // TODO add your handling code here:
        //Select the month -- J COMBO BOX
        //Select the year -- J COMBO BOX

        if (months.size() == 12) {
            JOptionPane.showMessageDialog(null, "All months for " + curYear + " have been added already.");
            return;
        }

        String[] tempMonthsArr = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        //Determine which month to show first to add
        /*int indexToShow = 0;
        for (int i = 0; i < months.size(); i++) {
            String tempStr = months.get(i).substring(0, 3).trim();
            System.out.println(tempStr + " : " + tempMonthsArr[i]);
            if (!tempStr.equals(tempMonthsArr[i])) {
                indexToShow = i;
                System.out.println("TEST");
                break;
            } else {
                indexToShow = months.size();
            }
        }*/
        //System.out.println("INDEX TO SHOW: " + indexToShow);
        //Remove all months that have already been added for year
        String[] monthsArr = new String[12 - months.size()];
        System.out.println("LENGTH: " + monthsArr.length);
        int index = 0;
        for (String s : tempMonthsArr) {
            s += "\t" + curYear;
            if (!months.contains(s)) {
                System.out.println(s);
                monthsArr[index] = s.substring(0, 4).trim();
                index++;
            }
        }

        Object[] monthsObjArr = monthsArr;
        Object selection = JOptionPane.showInputDialog(null,
                "Select a Month", "Months",
                JOptionPane.INFORMATION_MESSAGE, null,
                monthsObjArr, monthsObjArr[0]);
        String month = (String) selection;

        if (month == null) {
            return;
        }

        String year = curYear;

        if (year == null) {
            return;
        }

        System.out.println("MONTH: " + month);
        System.out.println("YEAR: " + year);
        String monthAndYear = month + "\t" + year;

        getMonths();
        if (months.contains(monthAndYear)) {
            JOptionPane.showMessageDialog(null, "Month already added.");
            return;
        } else {
            addMonth(monthAndYear, true);
        }
    }//GEN-LAST:event_addMonthButtonActionPerformed

    private void removeMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMonthButtonActionPerformed
        // TODO add your handling code here:

        //If something is selected
        if (!(monthsList.isSelectionEmpty())) {
            //Ask for confirmation
            String selectedMonth = monthsList.getSelectedValue();
            String input = JOptionPane.showInputDialog("WARNING: Removing a month will remove all" + "\n"
                    + "of the associated transactions." + "\n"
                    + "This action cannot be undone." + "\n"
                    + "Type DELETE to remove the month: " + selectedMonth);
            selectedMonth = selectedMonth.replaceAll(" ", "\t");

            if (!(input.equalsIgnoreCase("delete"))) {
                return;
            }

            try {
                Scanner sc = new Scanner(new File("months.txt"));

                String line = "";
                ArrayList<String> lines = new ArrayList<>();

                boolean foundMonth = false;

                while (sc.hasNextLine()) {
                    line = sc.nextLine();

                    if (line.charAt(0) != '#') {
                        foundMonth = false;
                    }

                    //MONTHS DONT HAVE MARKERS
                    //ONLY TRANSACTIONS HAVE THE # MARKERS
                    //Look for month
                    if (line.charAt(0) != '#') {
                        if (line.equals(selectedMonth)) {
                            foundMonth = true;
                        }
                    }

                    if (foundMonth) {
                        System.out.println("FOUND MONTH");
                        //Skip month line and transactions
                    } else {
                        //Dont want to add the month
                        lines.add(line);
                    }

                }
                //System.out.println("LINES: " + lines);

                sc.close();

                PrintWriter printer = new PrintWriter(new File("months.txt"));

                for (String s : lines) {
                    printer.println(s);
                }

                printer.close();

                updateList();

                removeMonthButton.setEnabled(false);
                viewTransactionsButton.setEnabled(false);
            } catch (FileNotFoundException ex) {
                System.out.println("File not found.");
            }
        }
    }//GEN-LAST:event_removeMonthButtonActionPerformed

    private void viewTransactionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewTransactionsButtonActionPerformed
        // TODO add your handling code here:

        //If something is selected
        if (!(monthsList.isSelectionEmpty())) {
            int indexOfMonth = monthsList.getSelectedIndex();
            String selectedMonth = months.get(indexOfMonth);
            System.out.println("SELECTED MONTH: " + selectedMonth);

            this.dispose();
            TransactionsFrame transactionsFrame = new TransactionsFrame(selectedMonth, curYear, foundYears);
        }
    }//GEN-LAST:event_viewTransactionsButtonActionPerformed

    private void autoCompleteYearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoCompleteYearButtonActionPerformed
        // TODO add your handling code here

        if (monthsList.getModel().getSize() == 12) {
            JOptionPane.showMessageDialog(null, "All months have already been added.");
            return;
        }

        //Ask for confirmation
        String input = JOptionPane.showInputDialog(null, "Autocomplete year " + curYear + "? \nType Yes to Autocomplete the Year.");
        if (input == null) {
            return;
        }
        if (!input.equalsIgnoreCase("yes")) {
            return;
        }

        String year = curYear;

        if (year == null) {
            return;
        }

        String[] monthsArr = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        //For each month, add a new month/year
        getMonths();
        for (int i = 0; i < monthsArr.length; i++) {
            String monthAndYear = monthsArr[i] + "\t" + year;
            if (months.contains(monthAndYear)) {

            } else {
                addMonth(monthAndYear, false);
                System.out.println("ADDING: " + monthAndYear);
            }
        }
        JOptionPane.showMessageDialog(null, "Request Complete.");
        autoCompleteYearButton.setEnabled(false);
    }//GEN-LAST:event_autoCompleteYearButtonActionPerformed

    private void viewYearlyStatisticsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewYearlyStatisticsButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        StatisticsFrame stats = new StatisticsFrame(curYear, foundYears);
    }//GEN-LAST:event_viewYearlyStatisticsButtonActionPerformed

    private void importTransactionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importTransactionsButtonActionPerformed
        // TODO add your handling code here:

        //Ask user to choose a file;
        JFileChooser jfc = new JFileChooser();
        jfc.showDialog(null, "Select File");
        jfc.setVisible(true);
        File filename = jfc.getSelectedFile();
        if (filename == null) {
            return;
        }
        if (filename.exists()) {
            System.out.println("File name " + filename.getAbsolutePath());
            importTransactions(filename);
        }
    }//GEN-LAST:event_importTransactionsButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        SearchFrame searchFrame = new SearchFrame(curYear, true, foundYears);
    }//GEN-LAST:event_searchButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        YearFrame yearFrame = new YearFrame();
    }//GEN-LAST:event_exitButtonActionPerformed

    private void prevYearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevYearButtonActionPerformed
        // TODO add your handling code here:
        MainFrame mainFrame = new MainFrame(prevYear, foundYears);
        this.dispose();
    }//GEN-LAST:event_prevYearButtonActionPerformed

    private void nextYearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextYearButtonActionPerformed
        // TODO add your handling code here:
        MainFrame mainFrame = new MainFrame(nextYear, foundYears);
        this.dispose();
    }//GEN-LAST:event_nextYearButtonActionPerformed

    private ArrayList<String> getCurrentTransactions() {
        try {
            Scanner sc = new Scanner(new File("months.txt"));

            String line = "";
            ArrayList<String> currentTransactions = new ArrayList<>();
            while (sc.hasNextLine()) {
                line = sc.nextLine();

                if (line.charAt(0) == '#') {
                    //Remove #
                    String formattedLine = line.substring(1, line.length());
                    currentTransactions.add(formattedLine);
                }
            }

            sc.close();

            return currentTransactions;
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        }
        return null;
    }

    private void importTransactions(File fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            ArrayList<String> acceptedTransactions = new ArrayList<>();
            ArrayList<String> rejectedTransactions = new ArrayList<>();
            String line = "";
            ArrayList<String> currentCategories = getCurrentCategories();

            if (currentCategories == null) {
                JOptionPane.showMessageDialog(null, "Error 406");
            }

            ArrayList<String> currentTransactions = getCurrentTransactions();

            if (currentTransactions == null) {
                JOptionPane.showMessageDialog(null, "Error 407");
            }

            boolean foundDuplicate = false;
            boolean includeDuplicates = false;
            while ((line = br.readLine()) != null) {
                //Check for correct format of line
                //If the line is correctly formatted, add the transaction
                //Check if the month is valid
                //Check if the amount is valid
                //Check if the category is valid
                //"Date (tab) Description (tab) Amount (tab) Category";
                try {
                    String[] lineInfo = line.split("\t");
                    String date = lineInfo[0].trim();
                    String description = lineInfo[1].trim();
                    String amountStr = lineInfo[2].trim();
                    String category = lineInfo[3].trim().toUpperCase();

                    String marker = "1";
                    if (category.equalsIgnoreCase("income")) {
                        marker = "2";
                    }

                    String formattedLine = date + "\t" + description + "\t" + amountStr + "\t" + category + "\t" + marker;

                    //Check if the date is valid
                    DateFormat df = new SimpleDateFormat("MMM " + "dd " + "yyyy");
                    df.setLenient(false);
                    df.parse(date);

                    //CHECK IF THE MONTH/YEAR IS VALID
                    getMonths();

                    //Want to get month and year from date
                    int indexOfSpace = date.indexOf(" ", date.indexOf(" ") + 1);
                    String month = date.substring(0, 3).trim();
                    String year = date.substring(indexOfSpace, date.length()).trim();
                    System.out.println("MONTH: " + month);
                    System.out.println("YEAR: " + year);
                    String monthAndYear = month + "\t" + year;
                    if (!(months.contains(monthAndYear))) {
                        throw new Exception("INVALID DATE");
                    }

                    //Check if the description is at least one character
                    if (description.length() < 1) {
                        throw new Exception("INVALID DESCRIPTION");
                    }

                    int indexOfForbiddenCharacters = description.indexOf("--");
                    if (indexOfForbiddenCharacters != -1) {
                        throw new Exception("INVALID DESCRIPTION");
                    }

                    //Check if the amount is valid
                    double amount = Double.parseDouble(amountStr);

                    if (amount <= 0.0) {
                        throw new Exception("INVALID AMOUNT");
                    }

                    //Check if the category is valid
                    //If category is not valid
                    if (!(currentCategories.contains(category))) {
                        if (!(category.equalsIgnoreCase("income"))) {
                            throw new Exception("INVALID CATEGORY");
                        }
                    }

                    //If a duplicate has not been found yet
                    if (!foundDuplicate) {
                        if (currentTransactions.contains(formattedLine)) {
                            foundDuplicate = true;

                            //Ask user if they want to include duplicate transactions
                            String input = JOptionPane.showInputDialog("Duplicate transaction(s) found. Do you wish to include these? Type Yes or No:");

                            if (input.equalsIgnoreCase("yes")) {
                                includeDuplicates = true;
                            }
                        }
                    }

                    if (currentTransactions.contains(formattedLine)) {
                        if (includeDuplicates == true) {
                            acceptedTransactions.add(formattedLine);
                        }
                    } else {
                        acceptedTransactions.add(formattedLine);
                    }
                } catch (Exception e) {
                    rejectedTransactions.add(line);
                }
            }
            Collections.sort(acceptedTransactions);
            System.out.println("ACCEPTED TRANSACTIONS: " + acceptedTransactions);
            System.out.println("REJECTED TRANSACTIONS: " + rejectedTransactions);

            if (acceptedTransactions.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Import failed. No valid transactions found.");
                return;
            }

            ArrayList<String> monthsToAdd = new ArrayList<>();
            //Get months needed to be added
            for (String s : acceptedTransactions) {
                //Get new months being added, sort by months
                String[] transactionInfo = s.split("\t");
                String date = transactionInfo[0];

                int indexOfSpace = date.indexOf(" ", date.indexOf(" ") + 1);
                String month = date.substring(0, 3).trim();
                String year = date.substring(indexOfSpace, date.length()).trim();
                System.out.println("MONTH: " + month);
                System.out.println("YEAR: " + year);

                String monthAndYear = month + "\t" + year;

                if (!monthsToAdd.contains(monthAndYear)) {
                    monthsToAdd.add(monthAndYear);
                }

            }

            Scanner sc = new Scanner(new File("months.txt"));

            line = "";
            ArrayList<String> lines = new ArrayList<>();

            //Copy previous data
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                lines.add(line);
            }

            sc.close();

            PrintWriter printer = new PrintWriter(new File("months.txt"));

            //Print previous data
            for (String s : lines) {
                printer.println(s);
            }

            //Need to add the months in monthsToAdd in the appropriate location
            //Print imported transactions
            String prevDate = "";
            int indexTracker = 0;
            for (String s : acceptedTransactions) {
                String currDate = s.substring(0, 3);

                if (!(prevDate.equals(currDate))) {
                    printer.println(monthsToAdd.get(indexTracker));
                    indexTracker++;
                }

                printer.println("#" + s);

                prevDate = currDate;
            }

            printer.close();

            JOptionPane.showMessageDialog(null, "Import successful.");

            if (!rejectedTransactions.isEmpty()) {
                //Split up output dialogs
                int maxTransactionsInOutput = 20;

                indexTracker = 0;

                int numDialogBoxes = rejectedTransactions.size() / maxTransactionsInOutput;

                for (int i = 0; i <= numDialogBoxes; i++) {
                    String output = "Rejected Transactions: (" + (i + 1) + " of " + (numDialogBoxes + 1) + ")\n";

                    try {
                        for (int j = 0; j < maxTransactionsInOutput; j++) {
                            output += rejectedTransactions.get(indexTracker) + "\n";
                            indexTracker++;
                        }
                    } catch (IndexOutOfBoundsException e) {

                    }
                    JOptionPane.showMessageDialog(null, output);
                }
            }

            //Check for duplicate transactions
            //findDuplicateTransactions();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method looks through all transactions and determines if there are
     * any duplicates
     */
    private void findDuplicateTransactions() {
        try {
            Scanner sc = new Scanner(new File("months.txt"));

            ArrayList<String> transactions = new ArrayList<>();
            ArrayList<String> duplicateTransactions = new ArrayList<>();
            String line = "";
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                //Look at lines with #
                if (line.charAt(0) == '#') {
                    //If the transaction is already added
                    if (transactions.contains(line)) {
                        //Add to duplicate transactions

                        //Remove # and end of line marker
                        String formattedLine = line.substring(1, line.length() - 1);
                        duplicateTransactions.add(formattedLine);
                    } else {
                        transactions.add(line);
                    }
                }
            }

            sc.close();

            if (duplicateTransactions.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No duplicate transactions found.");
                return;
            }

            Collections.sort(duplicateTransactions);

            //Split up output dialogs
            int maxTransactionsInOutput = 20;

            int indexTracker = 0;

            int numDialogBoxes = duplicateTransactions.size() / maxTransactionsInOutput;

            for (int i = 0; i <= numDialogBoxes; i++) {
                String output = "Duplicate Transactions (" + (i + 1) + " of " + (numDialogBoxes + 1) + "):\n";

                try {
                    for (int j = 0; j < maxTransactionsInOutput; j++) {
                        output += duplicateTransactions.get(indexTracker) + "\n";
                        indexTracker++;
                    }
                } catch (IndexOutOfBoundsException e) {

                }
                JOptionPane.showMessageDialog(null, output);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        }
    }

    private ArrayList<String> getCurrentCategories() {
        try {
            Scanner sc = new Scanner(new File("categories.txt"));

            String line = "";
            ArrayList<String> currentCategories = new ArrayList<>();
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                currentCategories.add(line);
            }

            sc.close();

            return currentCategories;

        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        }
        return null;
    }

    private void addMonth(String monthAndYear, boolean showConfirmation) {
        try {
            //Copy over all previous data
            Scanner sc = new Scanner(new File("months.txt"));

            ArrayList<String> lines = new ArrayList<>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }

            sc.close();

            PrintWriter printer = new PrintWriter(new File("months.txt"));

            //Print previous data
            for (String s : lines) {
                printer.println(s);
            }

            //Print new month
            printer.println(monthAndYear);

            printer.close();

            updateList();

            String[] output = monthAndYear.split("\t");

            if (showConfirmation) {
                JOptionPane.showMessageDialog(null, output[0] + " " + output[1] + " successfully added.");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
            JOptionPane.showMessageDialog(null, "Error 404.");
        }
    }

    private void updateList() {
        getMonthsByYear(curYear);

        //Need to sort months
        Collections.sort(months, new Comparator<String>() {
            public int compare(String o1, String o2) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM" + "\t" + "yyyy");
                    return sdf.parse(o1).compareTo(sdf.parse(o2));  //sdf.parse returns date - So, Compare Date with date
                } catch (ParseException ex) {
                    return o1.compareTo(o2);
                }
            }
        });
        System.out.println("MONTHS: " + months);

        String[] listArr = new String[months.size()];

        updateNumMonthsLabel(months.size());

        int index = 0;
        for (String s : months) {
            listArr[index] = s.replaceAll("\t", " ");
            index++;
        }

        monthsList.setListData(listArr);

        if (monthsList.getModel().getSize() == 12) {
            autoCompleteYearButton.setEnabled(false);
            addMonthButton.setEnabled(false);
        } else {
            autoCompleteYearButton.setEnabled(true);
            addMonthButton.setEnabled(true);
        }
    }

    private void updateNumMonthsLabel(int numMonths) {
        numMonthsLabel.setText("Number of Months: " + numMonths);
    }

    public ArrayList<String> getMonths() {
        try {
            Scanner sc = new Scanner(new File("months.txt"));

            String line = "";
            months.clear();
            while (sc.hasNextLine()) {
                line = sc.nextLine();

                if (line.charAt(0) != '#') {
                    if (!(months.contains(line))) {
                        months.add(line);
                    }
                }
            }

            sc.close();

            return months;
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        }
        return null;
    }

    private void getMonthsByYear(String year) {
        try {
            Scanner sc = new Scanner(new File("months.txt"));

            String line = "";
            months.clear();
            while (sc.hasNextLine()) {
                line = sc.nextLine();

                if (line.charAt(0) != '#') {
                    if (!(months.contains(line))) {
                        String[] lineInfo = line.split("\t");
                        String foundYear = lineInfo[1];
                        if (foundYear.equals(year)) {
                            months.add(line);
                        }
                    }
                }
            }

            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addMonthButton;
    private javax.swing.JButton autoCompleteYearButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton importTransactionsButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> monthsList;
    private javax.swing.JButton nextYearButton;
    private javax.swing.JLabel numMonthsLabel;
    private javax.swing.JButton prevYearButton;
    private javax.swing.JButton removeMonthButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton viewTransactionsButton;
    private javax.swing.JButton viewYearlyStatisticsButton;
    // End of variables declaration//GEN-END:variables
}
