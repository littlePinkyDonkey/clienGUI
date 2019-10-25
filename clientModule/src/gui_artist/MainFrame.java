package gui_artist;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.concurrent.Exchanger;

public class MainFrame implements MyFrame {
    private String user_name;

    private Exchanger<String> command_exchanger;

    private JFrame main_frame;
    private final JMenuBar menu_bar = new JMenuBar();

    private final JMenu settings = new JMenu("Settings");
    private final JMenu user_command_center = new JMenu("Commands");
    private final JMenu user_account = new JMenu("Account");
    private final JMenu go_to_auth = new JMenu("Authorization");

    private final ActionListener authorization_action = event ->{
        try{
            LogInFrame frame = new LogInFrame(main_frame,command_exchanger);
            frame.redrawFrame();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    };

    public MainFrame(Exchanger<String> command_exchanger, JFrame frame, String user_name)throws InterruptedException{
        this.command_exchanger = command_exchanger;
        this.main_frame = frame;
        this.user_name = user_name;

        drawFrame();
    }

    @Override
    public void drawFrame() throws InterruptedException {
        setDefaultFrameSettings();
        drawElements();
    }

    @Override
    public void redrawFrame() throws InterruptedException {
        setRepeatFrameSettings();
        drawElements();
    }

    @Override
    public void drawElements() {
        menu_bar.add(settings);
        menu_bar.add(user_command_center);
        menu_bar.add(user_account);
        menu_bar.add(go_to_auth);

        JButton test = (JButton) main_frame.add(new JButton("test"));
        test.addActionListener(authorization_action);

        main_frame.setJMenuBar(menu_bar);
        main_frame.revalidate();
    }

    @Override
    public void setDefaultFrameSettings() {
        main_frame.getContentPane().removeAll();
        main_frame.repaint();
        main_frame.setTitle(user_name);
        main_frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
    }

    @Override
    public void setRepeatFrameSettings() {
        setDefaultFrameSettings();
    }

    @Override
    public void showInfoDialog() {
        
    }
}
