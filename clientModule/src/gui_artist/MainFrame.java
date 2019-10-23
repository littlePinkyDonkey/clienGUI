package gui_artist;

import javax.swing.*;
import java.util.concurrent.Exchanger;

public class MainFrame implements MyFrame {
    private Exchanger<String> command_exchanger;

    private final JFrame main_frame;
    private final JMenuBar menu_bar = new JMenuBar();
    private final JMenu options = new JMenu("Options");
    private final JMenu file = new JMenu("File");

    public MainFrame(Exchanger<String> command_exchanger, JFrame frame)throws InterruptedException{
        this.command_exchanger = command_exchanger;
        this.main_frame = frame;

        drawFrame();
    }

    @Override
    public void drawFrame() throws InterruptedException {
        main_frame.getContentPane().removeAll();
        main_frame.repaint();

        menu_bar.add(file);
        menu_bar.add(options);

        main_frame.setJMenuBar(menu_bar);
        main_frame.revalidate();
    }
}
