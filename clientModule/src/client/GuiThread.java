package client;

import gui_artist.LogInFrame;
import gui_artist.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Exchanger;

public class GuiThread implements Runnable {
    private static JFrame main_frame = new JFrame();
    private final Exchanger<String> command_exchanger;
    private final Exchanger<Boolean> connection_exchanger;

    private static Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static Dimension screen_size = toolkit.getScreenSize();

    public GuiThread(Exchanger<String> command_exchanger, Exchanger<Boolean> connection_exchanger){
        this.command_exchanger = command_exchanger;
        this.connection_exchanger = connection_exchanger;
    }

    public void run(){
        try{
            MyFrame auth_frame = new LogInFrame(command_exchanger,main_frame,screen_size,connection_exchanger);
            auth_frame.drawFrame();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
