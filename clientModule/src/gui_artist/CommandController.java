package gui_artist;

import client.Client;
import client.ClientStarter;

import javax.swing.*;
import java.util.concurrent.Exchanger;

public class CommandController implements MyFrame {
    private String user_name;

    private Exchanger<String> exchanger;

    private final JFrame main_frame;

    private final JPanel panel = new JPanel();

    public CommandController(Exchanger<String> exchanger, JFrame frame, String user_name){
        this.exchanger = exchanger;
        this.main_frame = frame;
        this.user_name = user_name;
        ClientStarter.getDrowed_frames().add(CommandController.class.getName());
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
        main_frame.add(new JButton("test"));
        main_frame.revalidate();
    }

    @Override
    public void setDefaultFrameSettings() {
        main_frame.getContentPane().removeAll();
        main_frame.repaint();
    }

    @Override
    public void setRepeatFrameSettings() {
        setDefaultFrameSettings();
    }

    @Override
    public void showInfoDialog() {

    }
}
