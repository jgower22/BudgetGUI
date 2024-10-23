/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budgetgui;

/**
 *
 * @author jgower17
 */
public class MonthlyData {
    private String month;
    private double spending;
    private double income;
    
    public MonthlyData(String month, double spending, double income) {
        this.month = month;
        this.spending = spending;
        this.income = income;
    }
    
    public String getMonth() {
        return month;
    }
    
    public double getSpending() {
        return spending;
    }
    
    public double getIncome() {
        return income;
    }
    
    public void addIncome(double amount) {
        this.income += amount;
    }
    
    public void addSpending(double amount) {
        this.spending += amount;
    }
}
