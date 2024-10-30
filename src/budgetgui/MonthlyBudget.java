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
public class MonthlyBudget {
    private String category;
    private double actualSpending;
    private double budgetedSpending;
    
    public MonthlyBudget(String category, double actualSpending, double budgetedSpending) {
        this.category = category;
        this.actualSpending = actualSpending;
        this.budgetedSpending = budgetedSpending;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getActualSpending() {
        return actualSpending;
    }

    public void setActualSpending(double actualSpending) {
        this.actualSpending = actualSpending;
    }

    public double getBudgetedSpending() {
        return budgetedSpending;
    }

    public void setBudgetedSpending(double budgetedSpending) {
        this.budgetedSpending = budgetedSpending;
    }

    @Override
    public String toString() {
        return "MonthlyBudget{" + "category=" + category + ", actualSpending=" + actualSpending + ", budgetedSpending=" + budgetedSpending + '}';
    }
    
}
