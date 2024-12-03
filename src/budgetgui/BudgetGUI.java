/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budgetgui;

import java.util.prefs.Preferences;
import javax.swing.JOptionPane;

/**
 *
 * @author jacksongower
 */
public class BudgetGUI {

    private boolean isDarkMode = false;
    private static final String DARK_MODE_PREF_KEY = "darkModeEnabled";
    private Preferences prefs;

    /**
     * @param args the command line arguments
     */
    public BudgetGUI() {
        prefs = Preferences.userNodeForPackage(SettingsFrame.class);
        isDarkMode = prefs.getBoolean(DARK_MODE_PREF_KEY, false);
    }

    public static void main(String[] args) {
        SingleInstanceChecker checker = new SingleInstanceChecker();
        if (checker.isAppRunning()) {
            JOptionPane.showMessageDialog(null, "Application is already running.");
            System.exit(1);
        } else {
            BudgetGUI budgetGUI = new BudgetGUI();
            SettingsFrame settings = new SettingsFrame(false);
            if (budgetGUI.isDarkMode) {
                settings.displayDarkMode();
            } else {
                settings.displayLightMode();
            }
            YearFrame yearFrame = new YearFrame();
        }
    }

}
