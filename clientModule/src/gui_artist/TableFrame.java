package gui_artist;

import client.ClientStarter;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.concurrent.Exchanger;

public class TableFrame implements MyFrame {
    private String user_name;

    private Exchanger<String> command_exchanger;

    private JFrame main_frame;
    private final JMenuBar menu_bar = new JMenuBar();

    private final JMenu user_account = new JMenu("Account");
    private final JMenu options = new JMenu("Options");

    private final JMenuItem command_controller = new JMenuItem("Controller");
    private final JMenuItem persons_table = new JMenuItem("Table");
    private final JMenuItem canvas = new JMenuItem("Canvas");
    private final JMenuItem exit = new JMenuItem("Exit");
    private final JMenuItem settings = new JMenuItem("Settings");
    private final JMenuItem change_account = new JMenuItem("Change Account");

    private final ActionListener exit_action = event -> {
        System.exit(0);
    };
    private final ActionListener authorization_action = event -> {
        try{
            new LogInFrame(main_frame,command_exchanger).redrawFrame();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    };
    private final ActionListener draw_settings_frame = event -> {

        //отрисовка фрейма настроек
        System.out.println("settings");
    };
    private final ActionListener draw_controller_frame = event -> {
        try{
            if (ClientStarter.getDrowed_frames().contains(CommandController.class.getName())){
                new CommandController(command_exchanger,main_frame,user_name).redrawFrame();
                System.out.println("redraw");
            }else {
                new CommandController(command_exchanger,main_frame,user_name).drawFrame();
                System.out.println("draw");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    };
    private final ActionListener draw_table_frame = event -> {
        try{
            if (ClientStarter.getDrowed_frames().contains(this.getClass().getName())){
                new TableFrame(command_exchanger,main_frame,user_name).redrawFrame();
                System.out.println("redraw");
            }else {
                new TableFrame(command_exchanger,main_frame,user_name).drawFrame();
                System.out.println("draw");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    };
    private final ActionListener draw_canvas_frame = event -> {

        //отрисовка фрейма с отображением
        System.out.println("canvas frame");
    };

    public TableFrame(Exchanger<String> command_exchanger, JFrame frame, String user_name)throws InterruptedException{
        this.command_exchanger = command_exchanger;
        this.main_frame = frame;
        this.user_name = user_name;
        ClientStarter.getDrowed_frames().add(TableFrame.class.getName());
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
        options.add(settings);
        settings.addActionListener(draw_settings_frame);
        options.add(change_account);
        change_account.addActionListener(authorization_action);
        options.addSeparator();
        options.add(exit);
        exit.addActionListener(exit_action);
        menu_bar.add(options);

        user_account.add(command_controller);
        command_controller.addActionListener(draw_controller_frame);
        user_account.add(persons_table);
        persons_table.addActionListener(draw_table_frame);
        user_account.add(canvas);
        canvas.addActionListener(draw_canvas_frame);
        menu_bar.add(user_account);

        main_frame.setJMenuBar(menu_bar);
        main_frame.revalidate();
    }

    @Override
    public void setDefaultFrameSettings() {
        main_frame.getContentPane().removeAll();
        main_frame.repaint();
        main_frame.setTitle(user_name);
        main_frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        main_frame.setSize(500,500);
    }

    @Override
    public void setRepeatFrameSettings() {
        setDefaultFrameSettings();
    }

    @Override
    public void showInfoDialog() {

    }
}
