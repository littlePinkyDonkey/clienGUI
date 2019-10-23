package controller;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Windows {
    private final JFrame frame;

    public Windows(JFrame frame){
        this.frame = frame;
    }

    private WindowListener closing = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            int choose = JOptionPane.showConfirmDialog(
                    frame.getContentPane(),"Are you sure?","Confirm exit",JOptionPane.YES_NO_OPTION
            );

            if (choose == 0)
                System.exit(0);
        }

    };

    public WindowListener getClosing(){
        return closing;
    }
}
