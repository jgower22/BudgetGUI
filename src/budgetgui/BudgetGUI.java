/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budgetgui;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import java.util.prefs.Preferences;

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
