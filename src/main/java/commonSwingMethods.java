import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;

public class commonSwingMethods {
    
    //Method to remove button listneers
    public static void removeButtonListeners(JButton button) {
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
    }

    //mehtod to remove the JList listener
    public static void removeJListListeners(JList<String> list) {

        //get the list of listeners
        ListSelectionListener[] listeners = list.getListSelectionListeners();

        //remove the listeners
        for (ListSelectionListener listener : listeners) {
            list.removeListSelectionListener(listener);
        }

    }

    //method to remove the listeners from the JComboBox
    public static void removeJComboBoxListeners(JComboBox<String> combobox) {

        //get the list of listeners
        ActionListener[] listeners = combobox.getActionListeners();

        //remove the listeners
        for (ActionListener listener : listeners) {
            combobox.removeActionListener(listener);
        }

    }

    //method to remove the checkbox listeners
    public static void removeJCkeckBoxListeners(JCheckBox checkbox) {

        //get the state listeners
        ChangeListener[] listeners = checkbox.getChangeListeners();
    
        //remove the listeners
        for (ChangeListener listener : listeners) {
            checkbox.removeChangeListener(listener);
        }

    }

    //method to remove the JSlider listeners
    public static void removeJSliderListeners(JSlider slider) {

        //get the state listeners
        ChangeListener[] listeners = slider.getChangeListeners();
    
        //remove the listeners
        for (ChangeListener listener : listeners) {
            slider.removeChangeListener(listener);
        }

    }

    //method to remove the JSpinner listeners
    public static void removeJSpinnerListeners(JSpinner spinner) {

        //get the state listeners
        ChangeListener[] listeners = spinner.getChangeListeners();
    
        //remove the listeners
        for (ChangeListener listener : listeners) {
            spinner.removeChangeListener(listener);
        }

    }

}
