/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budgetgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.RenderingHints.Key;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author jacksongower
 */
public class StatisticsFrame extends javax.swing.JFrame {

    private String year;
    private double totalIncome = 0.0;
    private double totalSpending = 0.0;
    private double highestIncomeAmount = 0.0;
    private double lowestIncomeAmount = 0.0;
    private String highestIncomeMonth = null;
    private String lowestIncomeMonth = null;
    private int numMonths = 0;
    private int indexOfYear = 0;
    private HashMap<String, Double> budgetsSpending = new HashMap<>();
    private boolean isShowingSpendingSummary;
    private MonthlyData[] monthlyDataArray = new MonthlyData[12];
    private HashMap<String, Double> spendingPerCategory = new HashMap<>();

    //Category -- Spending
    /**
     * Creates new form StatisticsFrame
     */
    public StatisticsFrame(String year) {
        initComponents();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        this.setVisible(true);
        this.setTitle("Statistics");
        this.setResizable(false);

        //Ask for year
        //Or set year
        this.year = year;

        ArrayList<String> savedYears = getSavedYears();
        if (savedYears.size() <= 1) {
            changeYearButton.setEnabled(false);
        }

        setLayout(new BorderLayout());

        getStatistics(false);

    }

    private void openBarChart() {
        CategoryDataset dataset = createBarChartDataset(monthlyDataArray);

        // Create a bar chart using the dataset
        JFreeChart barChart = ChartFactory.createBarChart(
                "Monthly Spending & Income (" + year + ")", // Chart title
                "Month", // X-axis label
                "Amount ($)", // Y-axis label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Orientation
                true, // Include legend
                true, // Tooltips
                false // URLs
        );

        //Colors
        CategoryPlot catPlot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) catPlot.getRenderer();
        renderer.setSeriesPaint(0, new Color(244, 67, 54));
        renderer.setSeriesPaint(1, new Color(76, 175, 80));

        ChartPanel chartPanel = new ChartPanel(barChart);
        JFrame frame = new JFrame("Monthly Spending & Income (" + year + ")");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the popup without exiting the application
        frame.getContentPane().add(chartPanel); // Add the ChartPanel to the frame
        frame.pack(); // Size the frame according to its content
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true); // Make the frame visible
    }

    private CategoryDataset createBarChartDataset(MonthlyData[] arr) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Add data to the dataset
        for (int i = 0; i < arr.length; i++) {
            MonthlyData monthData = arr[i];
            dataset.addValue(monthData.getSpending(), "Spending", monthData.getMonth());
            dataset.addValue(monthData.getIncome(), "Income", monthData.getMonth());
        }

        return dataset;
    }

    private void openPieChart() {
        DefaultPieDataset dataset = createPieChartDataset(spendingPerCategory);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Annual Spending Breakdown (" + year + ")",
                dataset,
                true,
                true,
                false
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();
        DecimalFormat dollarFormat = new DecimalFormat("$#,##0.00");
        DecimalFormat percentFormat = new DecimalFormat("0.0%"); // Format for percentages

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})", dollarFormat, percentFormat));
        // {0} - Category name, {1} - Formatted value, {2} - Formatted percentage

        ChartPanel chartPanel = new ChartPanel(pieChart);
        JFrame frame = new JFrame("Annual Spending Breakdown (" + year + ")");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the popup without exiting the application
        frame.getContentPane().add(chartPanel); // Add the ChartPanel to the frame
        frame.pack(); // Size the frame according to its content
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true); // Make the frame visible
    }

    private DefaultPieDataset createPieChartDataset(HashMap<String, Double> map) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (String key : map.keySet()) {
            dataset.setValue(key, map.get(key));
        }
        return dataset;
    }

    /**
     * This method obtains all of the statistics for any given year Total Income
     * Total Spending Total Cash Flow Avg Spending/Month Avg Income/Month
     */
    private void getStatistics(boolean getAllYears) {
        try {
            Scanner sc = new Scanner(new File("months.txt"));

            String line = "";
            boolean foundMonth = false;

            //Reset stats
            totalIncome = 0.0;
            totalSpending = 0.0;
            numMonths = 0;
            budgetsSpending.clear();
            lowestIncomeAmount = 0.0;
            highestIncomeAmount = 0.0;
            lowestIncomeMonth = "N/A";
            highestIncomeMonth = "N/A";
            spendingPerCategory.clear();
            if (!getAllYears) {
                monthlyDataArray = new MonthlyData[12];
            }

            ArrayList<String> monthsArrList = new ArrayList<>();
            HashMap<String, Double> spendingPerMonth = new HashMap<>();
            HashMap<String, Double> incomePerMonth = new HashMap<>();
            while (sc.hasNextLine()) {
                line = sc.nextLine();

                //MONTHS DONT HAVE MARKERS
                //ONLY TRANSACTIONS HAVE MARKERS
                if (line.charAt(0) != '#') {
                    foundMonth = false;
                    //Skip over these lines when getAllYears is true
                    if (getAllYears) {
                        String[] lineInfo = line.split("\t");
                        String foundMonthStr = lineInfo[0].trim();
                        String foundYear = lineInfo[1].trim();

                        String monthAndYear = foundMonthStr + " " + foundYear;
                        if (!monthsArrList.contains(monthAndYear)) {
                            monthsArrList.add(monthAndYear);
                        }
                        continue;
                    }
                }

                //Get all transactions if getAllYears is true
                if (foundMonth || getAllYears) {
                    //Look at these lines
                    //Look at lines with marker 1 for spending
                    //Look at lines with marker 2 for income
                    String[] transactionInfo = line.split("\t");
                    String dateStr = transactionInfo[0];
                    String amountStr = transactionInfo[2];
                    String categoryName = transactionInfo[3];
                    String type = transactionInfo[4];

                    //Remove all commas from amountStr
                    amountStr = amountStr.replaceAll(",", "");

                    String tempAmountStr = amountStr.replaceAll(",", "");
                    String curMonth = dateStr.substring(1, 4);
                    //1 = spending
                    //2 = income
                    if (type.equals("1")) {
                        double spendingDouble = Double.parseDouble(tempAmountStr);
                        spendingPerCategory.put(categoryName, spendingPerCategory.getOrDefault(categoryName, 0.0) + spendingDouble);
                        totalSpending += spendingDouble;
                        if (!spendingPerMonth.containsKey(curMonth)) {
                            spendingPerMonth.put(curMonth, spendingDouble);
                        } else {
                            double prevSpendingForMonth = spendingPerMonth.get(curMonth);
                            spendingPerMonth.put(curMonth, prevSpendingForMonth + spendingDouble);
                        }
                    }
                    if (type.equals("2")) {
                        double incomeDouble = Double.parseDouble(tempAmountStr);
                        totalIncome += incomeDouble;
                        if (!incomePerMonth.containsKey(curMonth)) {
                            incomePerMonth.put(curMonth, incomeDouble);
                        } else {
                            double prevIncomeForMonth = incomePerMonth.get(curMonth);
                            incomePerMonth.put(curMonth, prevIncomeForMonth + incomeDouble);
                        }
                    }

                    //Budgets List Data
                    //Exclude income
                    if (!(categoryName.equalsIgnoreCase("income"))) {

                        //If the hashmap does not have the category added already
                        if (!(budgetsSpending.containsKey(categoryName))) {
                            budgetsSpending.put(categoryName, Double.parseDouble(tempAmountStr));
                        } else {
                            //Update the value
                            double oldValue = budgetsSpending.get(categoryName);
                            double newValue = oldValue + Double.parseDouble(amountStr);

                            //Update hashmap
                            budgetsSpending.replace(categoryName, newValue);
                        }
                    }

                }

                if (line.charAt(0) != '#') {
                    String[] lineInfo = line.split("\t");
                    String foundMonthStr = lineInfo[0].trim();
                    String foundYear = lineInfo[1].trim();

                    if (foundYear.equals(year) || getAllYears) {
                        foundMonth = true;

                        String monthAndYear = foundMonthStr + " " + foundYear;
                        if (!(monthsArrList.contains(monthAndYear)) || getAllYears) {
                            monthsArrList.add(monthAndYear);
                            System.out.println("MONTHS ARR LIST: " + monthsArrList);
                        }
                    }
                }
            }

            sc.close();

            System.out.println("BUDGET SPENDING: " + budgetsSpending);

            System.out.println("SPENDING PER MONTH: " + spendingPerMonth);
            System.out.println("INCOME PER MONTH: " + incomePerMonth);
            System.out.println("SPENDING PER CATEGORY: " + spendingPerCategory);

            if (!incomePerMonth.isEmpty()) {
                Map.Entry<String, Double> min = Collections.min(incomePerMonth.entrySet(), new Comparator<Map.Entry<String, Double>>() {
                    public int compare(Map.Entry<String, Double> entry1, Map.Entry<String, Double> entry2) {
                        return entry1.getValue().compareTo(entry2.getValue());
                    }
                });

                Map.Entry<String, Double> max = Collections.max(incomePerMonth.entrySet(), new Comparator<Map.Entry<String, Double>>() {
                    public int compare(Map.Entry<String, Double> entry1, Map.Entry<String, Double> entry2) {
                        return entry1.getValue().compareTo(entry2.getValue());
                    }
                });

                lowestIncomeMonth = min.getKey();
                highestIncomeMonth = max.getKey();

                lowestIncomeAmount = min.getValue();
                highestIncomeAmount = max.getValue();
            }

            numMonths = monthsArrList.size();

            //Array for bar chart
            int index = 0;
            String[] allMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            for (String month : allMonths) {
                double spending = spendingPerMonth.getOrDefault(month, 0.0);
                double income = incomePerMonth.getOrDefault(month, 0.0);
                MonthlyData monthData = new MonthlyData(month, spending, income);
                monthlyDataArray[index++] = monthData;
            }

            updateSpendingSummary();
            updateAllLabels();

            //Bar chart/pie chart buttons
            if (totalSpending == 0.0) {
                viewPieChartButton.setEnabled(false);
            } else {
                viewPieChartButton.setEnabled(true);
            }

            if (totalSpending != 0.0 || totalIncome != 0.0) {
                viewBarChartButton.setEnabled(true);
            } else {
                viewBarChartButton.setEnabled(false);
            }

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error: File not found.");
            this.dispose();
        }
    }

    private void updateAllLabels() {
        updateTitleLabel();
        updateTotalSpendingLabel();
        updateTotalIncomeLabel();

        updateLowestIncomeLabel(lowestIncomeMonth, lowestIncomeAmount);
        updateHighestIncomeLabel(highestIncomeMonth, highestIncomeAmount);

        double totalCashFlow = totalIncome - totalSpending;

        updateTotalCashFlowLabel(totalCashFlow);
        updatePercentageIncomeSavedLabel();

        if (numMonths > 0) {
            double averageSpending = totalSpending / numMonths;
            updateAverageSpendingLabel(averageSpending);

            double averageIncome = totalIncome / numMonths;
            updateAverageIncomeLabel(averageIncome);

            double averageCashFlow = totalCashFlow / numMonths;
            updateAverageCashFlowLabel(averageCashFlow);
        } else {
            double averageSpending = 0.0;
            updateAverageSpendingLabel(averageSpending);

            double averageIncome = 0.0;
            updateAverageIncomeLabel(averageIncome);

            double averageCashFlow = 0.0;
            updateAverageCashFlowLabel(averageCashFlow);
        }

        if (year.equals("All Years")) {
            projectedIncomeLabel.setText("Projected Income: N/A");
        } else if (numMonths > 0 && numMonths <= 12) {
            double averageIncome = totalIncome / numMonths;
            double projectedIncome = averageIncome * 12;
            updateProjectedIncomeLabel(projectedIncome);
        } else {
            updateProjectedIncomeLabel(0.0);
        }

        updateNumMonthsLabel();
    }

    private void updateTitleLabel() {
        titleLabel.setText("Statistics For " + year);
    }

    private void updateTotalSpendingLabel() {
        String formattedSpending = formatNumber(totalSpending);

        totalSpendingLabel.setText("Total Spending: $" + formattedSpending);
    }

    private void updateTotalIncomeLabel() {
        String formattedIncome = formatNumber(totalIncome);

        totalIncomeLabel.setText("Total Income: $" + formattedIncome);
    }

    private void updateTotalCashFlowLabel(double totalCashFlow) {
        String formattedCashFlow = formatNumber(totalCashFlow);

        totalCashFlowLabel.setText("Total Cash Flow: $" + formattedCashFlow);
    }

    private void updateAverageSpendingLabel(double averageSpending) {
        String formattedSpending = formatNumber(averageSpending);

        averageSpendingLabel.setText("Average Spending per Month: $" + formattedSpending);
    }

    private void updateAverageIncomeLabel(double averageIncome) {
        String formattedIncome = formatNumber(averageIncome);

        averageIncomeLabel.setText("Average Income per Month: $" + formattedIncome);
    }

    private void updateAverageCashFlowLabel(double averageCashFlow) {
        String formattedCashFlow = formatNumber(averageCashFlow);

        averageCashFlowLabel.setText("Average Cash Flow per Month: $" + formattedCashFlow);
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

    private void updateNumMonthsLabel() {
        numMonthsLabel.setText("Number of Months: " + numMonths);
    }

    private void updateProjectedIncomeLabel(double projectedIncome) {
        String projectedIncomeStr = formatNumber(projectedIncome);
        projectedIncomeLabel.setText("Projected Income: $" + projectedIncomeStr);
    }

    private void updateHighestIncomeLabel(String month, double highestIncome) {
        String highestIncomeInfo = "Highest Income Month: " + month + ", $" + formatNumber(highestIncome) + "";
        if (month == "N/A") {
            highestIncomeInfo = "Highest Income Month: N/A";
        }
        highestIncomeLabel.setText(highestIncomeInfo);
    }

    private void updateLowestIncomeLabel(String month, double lowestIncome) {
        String lowestIncomeInfo = "Lowest Income Month: " + month + ", $" + formatNumber(lowestIncome) + "";
        if (month == "N/A") {
            lowestIncomeInfo = "Lowest Income Month: N/A";
        }
        lowestIncomeLabel.setText(lowestIncomeInfo);
    }

    private String formatNumber(double num) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(num);
    }

    /**
     * This method updates the budgets list data It shows each category and the
     * spending in each category It also includes a goal (num months * category
     * limit)
     */
    private void updateSpendingSummary() {

        ArrayList<String> arrList = new ArrayList<>();
        String[] listArr = new String[budgetsSpending.size() + 2];

        HashMap<String, Double> budgetGoals = getBudgetGoals();

        boolean emptyBudgetGoals = false;
        if (budgetGoals == null || budgetGoals.size() == 0) {
            emptyBudgetGoals = true;
        }
        System.out.println("EMPTY BUDGET GOALS: " + emptyBudgetGoals);

        if (budgetsSpending.size() != 0) {
            for (String s : budgetsSpending.keySet()) {
                String formattedNumber = formatNumber(budgetsSpending.get(s));

                if (!emptyBudgetGoals) {
                    if (s.equalsIgnoreCase("uncategorized")) {
                        arrList.add(s + ": $" + formattedNumber);
                    } else {
                        double goalNum = budgetGoals.get(s) * numMonths;

                        String tempFormattedNumber = formattedNumber.replaceAll(",", "");
                        String budgetMetric = "";

                        if (Double.parseDouble(tempFormattedNumber) > goalNum) {
                            budgetMetric = "Over by $" + formatNumber(Double.parseDouble(tempFormattedNumber) - goalNum);
                        } else if (Double.parseDouble(tempFormattedNumber) < goalNum) {
                            budgetMetric = "Under by $" + formatNumber(goalNum - Double.parseDouble(tempFormattedNumber));
                        } else {
                            budgetMetric = "Met Goal";
                        }
                        arrList.add(s + ": $" + formattedNumber + " -- (Goal: $" + formatNumber(goalNum) + ") -- " + budgetMetric);
                    }
                } else {
                    arrList.add(s + ": $" + formattedNumber);
                }
            }
            Collections.sort(arrList);
            arrList.add("----------------- ---------------------------------------------------------------------------------------");
            arrList.add("Total Spending: $" + formatNumber(totalSpending));
        } else {
            arrList.add("You have no spending transactions!");
        }

        int index = 0;
        for (String s : arrList) {
            listArr[index] = s;
            index++;
        }

        summaryLabel.setText("Spending Summary");
        summaryButton.setText("View Income Summary");
        summaryList.setListData(listArr);
        isShowingSpendingSummary = true;
    }

    private HashMap<String, Double> getBudgetGoals() {
        try {
            Scanner sc = new Scanner(new File("categorylimits.txt"));

            HashMap<String, Double> budgetGoals = new HashMap<>();

            String line = "";
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] lineInfo = line.split("\t");
                String categoryName = lineInfo[0];
                String amountStr = lineInfo[1];

                budgetGoals.put(categoryName, Double.parseDouble(amountStr));
            }

            sc.close();

            return budgetGoals;
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

        totalSpendingLabel = new javax.swing.JLabel();
        totalIncomeLabel = new javax.swing.JLabel();
        totalCashFlowLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        changeYearButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        averageSpendingLabel = new javax.swing.JLabel();
        averageIncomeLabel = new javax.swing.JLabel();
        numMonthsLabel = new javax.swing.JLabel();
        averageCashFlowLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        summaryList = new javax.swing.JList<>();
        summaryLabel = new javax.swing.JLabel();
        percentageIncomeSavedLabel = new javax.swing.JLabel();
        projectedIncomeLabel = new javax.swing.JLabel();
        summaryButton = new javax.swing.JButton();
        lowestIncomeLabel = new javax.swing.JLabel();
        highestIncomeLabel = new javax.swing.JLabel();
        viewBarChartButton = new javax.swing.JButton();
        viewPieChartButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        totalSpendingLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        totalSpendingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalSpendingLabel.setText("Total Spending: $0.00");

        totalIncomeLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        totalIncomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalIncomeLabel.setText("Total Income: $0.00");

        totalCashFlowLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        totalCashFlowLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalCashFlowLabel.setText("Total Cash Flow: $0.00");

        titleLabel.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Statistics For ");

        changeYearButton.setText("Change Year");
        changeYearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeYearButtonActionPerformed(evt);
            }
        });

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        averageSpendingLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        averageSpendingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        averageSpendingLabel.setText("Avg. Spending/Month: $0.00");

        averageIncomeLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        averageIncomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        averageIncomeLabel.setText("Avg. Income/Month: $0.00");

        numMonthsLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        numMonthsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numMonthsLabel.setText("Number of Months: 0");

        averageCashFlowLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        averageCashFlowLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        averageCashFlowLabel.setText("Avg. Cash Flow/Month: $0.00");

        summaryList.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jScrollPane1.setViewportView(summaryList);

        summaryLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        summaryLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        summaryLabel.setText("Spending Summary");

        percentageIncomeSavedLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        percentageIncomeSavedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        percentageIncomeSavedLabel.setText("Percentage of Income Saved: 0.00%");

        projectedIncomeLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        projectedIncomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        projectedIncomeLabel.setText("Projected Income: $0.00");

        summaryButton.setText("View Income Summary");
        summaryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                summaryButtonActionPerformed(evt);
            }
        });

        lowestIncomeLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lowestIncomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lowestIncomeLabel.setText("Lowest Income Month: January ($700)");

        highestIncomeLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        highestIncomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highestIncomeLabel.setText("Highest Income Month: June ($1700)");

        viewBarChartButton.setText("View Monthly Spending & Income");
        viewBarChartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewBarChartButtonActionPerformed(evt);
            }
        });

        viewPieChartButton.setText("View Annual Spending Breakdown");
        viewPieChartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewPieChartButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(changeYearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(highestIncomeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titleLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(averageIncomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(averageSpendingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalCashFlowLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalIncomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalSpendingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(numMonthsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(averageCashFlowLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addComponent(projectedIncomeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(percentageIncomeSavedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(summaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(summaryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lowestIncomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(viewBarChartButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewPieChartButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(exitButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changeYearButton)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewBarChartButton)
                    .addComponent(viewPieChartButton))
                .addGap(18, 18, 18)
                .addComponent(totalSpendingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalIncomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalCashFlowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(averageSpendingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(averageIncomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(averageCashFlowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(percentageIncomeSavedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numMonthsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(projectedIncomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(highestIncomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lowestIncomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(summaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(summaryButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        if (year.equals("All Years")) {
            YearFrame yearFrame = new YearFrame();
        } else {
            MainFrame mainFrame = new MainFrame(year);
        }

    }//GEN-LAST:event_exitButtonActionPerformed

    private void changeYearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeYearButtonActionPerformed
        // TODO add your handling code here:

        //Ask for year
        ArrayList<String> yearsArrList = getSavedYears();
        String[] yearsArr = new String[yearsArrList.size() + 1];
        int index = 0;
        yearsArr[0] = "All Years";
        index++;
        for (String s : yearsArrList) {
            yearsArr[index] = s;
            index++;
        }

        for (int i = 0; i < yearsArr.length; i++) {
            if (year.equals(yearsArr[i])) {
                indexOfYear = i;
                break;
            }
        }

        Object[] yearsObjArr = yearsArr;
        Object selection = JOptionPane.showInputDialog(null,
                "Select a Year", "Years",
                JOptionPane.INFORMATION_MESSAGE, null,
                yearsObjArr, yearsObjArr[indexOfYear]);

        String tempSelection = (String) selection;
        if (selection == null) {
            return;
        }
        if (tempSelection != null) {
            year = (String) selection;
        }

        if (year == null) {
            return;
        }

        if (year.equals("All Years")) {
            getStatistics(true);
        } else {
            getStatistics(false);
        }
    }//GEN-LAST:event_changeYearButtonActionPerformed

    private void summaryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_summaryButtonActionPerformed
        // TODO add your handling code here:
        if (isShowingSpendingSummary) {
            updateIncomeSummary();
        } else {
            updateSpendingSummary();
        }
    }//GEN-LAST:event_summaryButtonActionPerformed

    private void viewBarChartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewBarChartButtonActionPerformed
        // TODO add your handling code here:
        openBarChart();
    }//GEN-LAST:event_viewBarChartButtonActionPerformed

    private void viewPieChartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewPieChartButtonActionPerformed
        // TODO add your handling code here:
        openPieChart();
    }//GEN-LAST:event_viewPieChartButtonActionPerformed

    private void updateIncomeSummary() {

        //Get all income transactions from given year
        HashMap<String, Double> groupedIncome = getIncomeFromCurrentYear();

        //Format data and add to array
        ArrayList<String> arrList = new ArrayList<>();
        if (groupedIncome.size() != 0) {
            for (String i : groupedIncome.keySet()) {
                arrList.add(i.toUpperCase() + ": $" + formatNumber(groupedIncome.get(i)));
            }
            Collections.sort(arrList);
            arrList.add("--------------------------------------------------------------------------------------------------------");
            arrList.add("Total Income: $" + formatNumber(totalIncome));
        } else {
            arrList.add("You have no income transactions!");
        }
        String[] listArr = new String[groupedIncome.size() + 2];
        int index = 0;
        for (String s : arrList) {
            listArr[index] = s;
            System.out.println(s);
            index++;
        }
        summaryList.setListData(listArr);
        summaryLabel.setText("Income Summary");
        summaryButton.setText("View Spending Summary");
        isShowingSpendingSummary = false;
    }

    private HashMap<String, Double> getIncomeFromCurrentYear() {
        HashMap<String, Double> groupedIncome = new HashMap<>();
        try {
            Scanner sc = new Scanner(new File("months.txt"));

            String line = "";
            boolean foundYear = false;
            boolean getAllYears = year.equals("All Years");

            while (sc.hasNextLine()) {
                line = sc.nextLine();

                //MONTHS DONT HAVE MARKERS
                //TRANSACTIONS DO HAVE THE # MARKERS
                if (line.charAt(0) != '#') {
                    foundYear = false;
                }

                if (foundYear) {
                    //Remove #
                    String formattedLine = line.substring(1, line.length());

                    String[] lineInfo = formattedLine.split("\t");
                    String date = lineInfo[0];
                    String description = lineInfo[1];
                    String amount = lineInfo[2];
                    String category = lineInfo[3];
                    String marker = lineInfo[4];

                    //1 is spending
                    //2 is income
                    //Add --
                    formattedLine = description + " -- $" + amount;

                    if (marker.equals("2")) {
                        //Remove commas from amount
                        String amountStr = amount.replaceAll(",", "");

                        //Add to hashmap
                        if (groupedIncome.containsKey(description.toLowerCase())) {
                            //Add income to existing key
                            double updatedIncome = groupedIncome.get(description.toLowerCase()) + Double.parseDouble(amountStr);
                            groupedIncome.replace(description.toLowerCase(), updatedIncome);
                        } else {
                            //Create new key/value for new income group
                            groupedIncome.put(description.toLowerCase(), Double.parseDouble(amountStr));
                        }
                    }
                }

                if (line.charAt(0) != '#') {
                    //Remove month from line
                    String[] lineInfo = line.split("\t");
                    String yearInFile = lineInfo[1];
                    if (yearInFile.equals(year) || getAllYears) {
                        foundYear = true;
                        System.out.println("FOUND YEAR");
                    }
                }
            }

            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        }
        return groupedIncome;
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel averageCashFlowLabel;
    private javax.swing.JLabel averageIncomeLabel;
    private javax.swing.JLabel averageSpendingLabel;
    private javax.swing.JButton changeYearButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel highestIncomeLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lowestIncomeLabel;
    private javax.swing.JLabel numMonthsLabel;
    private javax.swing.JLabel percentageIncomeSavedLabel;
    private javax.swing.JLabel projectedIncomeLabel;
    private javax.swing.JButton summaryButton;
    private javax.swing.JLabel summaryLabel;
    private javax.swing.JList<String> summaryList;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel totalCashFlowLabel;
    private javax.swing.JLabel totalIncomeLabel;
    private javax.swing.JLabel totalSpendingLabel;
    private javax.swing.JButton viewBarChartButton;
    private javax.swing.JButton viewPieChartButton;
    // End of variables declaration//GEN-END:variables
}
