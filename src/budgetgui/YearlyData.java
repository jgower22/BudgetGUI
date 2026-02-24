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
public class YearlyData {
    private String year;
    public double spending;
    public double income;
    
    public YearlyData(String year, double spending, double income) {
        this.year = year;
        this.spending = spending;
        this.income = income;
    }
    
    public String getYear() {
        return year;
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
