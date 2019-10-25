package gui_artist;

import controller.Windows;
import controller.LogInCreator;
import model.CommandExchanger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.Exchanger;

public class LogInFrame implements MyFrame {
    private Exchanger<String> command_exchanger;
    private Exchanger<Boolean> connection_exchanger;
    private CommandExchanger command_sender;

    private JFrame frame;
    private Dimension screen_size;
    private final JPanel sign_in_panel = new JPanel();
    private final JPanel sign_up_panel = new JPanel();
    private final JPanel empty_panel = new JPanel();
    private final Font font = new Font("Arial",Font.PLAIN,20);
    private final Border border = BorderFactory.createEtchedBorder();
    private final Color panes_background = new Color(255,250,250);
    private final Color frame_background = new Color(248,248,255);

    private final JTextField reg_login = new JTextField(15);
    private final JTextField email = new JTextField(15);
    private final JTextField auth_login = new JTextField(15);
    private final JPasswordField password = new JPasswordField(15);
    private final JLabel login_label = new JLabel("Login: ");
    private final JLabel email_label = new JLabel("Email: ");
    private final JLabel password_label = new JLabel("Password: ");
    private final JLabel sign_in_label = new JLabel("Login: ");

    private final JButton sing_in_button = new JButton("Sign in");
    private final JButton sign_up_button = new JButton("Sing up");

    private final Windows window_event;

    private String server_answer;

    private final ActionListener sign_up_action = event -> {
        try{
            String reg_login = this.reg_login.getText();
            String email = this.email.getText();

            new LogInCreator(command_sender).registration(reg_login,email);
            if (command_exchanger.exchange(null).equals("successful"))
                server_answer = "You've got a new account! Please, check your email to get the password";
            showInfoDialog();

            this.reg_login.setText(null);
            this.email.setText(null);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    };
    private final ActionListener sing_in_action = event -> {
        try{
            String auth_login = this.auth_login.getText();
            String password = new String(this.password.getPassword());

            new LogInCreator(command_sender).authorization(auth_login,password);

            server_answer = command_exchanger.exchange(null);
            if (server_answer.equals("successful"))
                new MainFrame(command_exchanger,frame,auth_login);

            this.auth_login.setText(null);
            this.password.setText(null);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    };

    public LogInFrame(Exchanger<String> command, JFrame frame, Dimension screen, Exchanger<Boolean> connect){
        this.command_exchanger = command;
        this.frame = frame;
        this.screen_size = screen;
        this.connection_exchanger = connect;
        this.window_event = new Windows(frame);
        this.command_sender = CommandExchanger.getInstance(command_exchanger);
    }
    public LogInFrame(JFrame frame,Exchanger<String> command_exchanger){
        this.command_exchanger = command_exchanger;
        this.frame = frame;
        this.window_event = new Windows(frame);
        this.command_sender = CommandExchanger.getInstance(command_exchanger);
        this.screen_size = Toolkit.getDefaultToolkit().getScreenSize();
    }

    @Override
    public void drawFrame() throws InterruptedException{
        setDefaultFrameSettings();
        drawElements();

        try{
            while (connection_exchanger.exchange(null))
                showErrorDialog();
        }catch (NullPointerException e){}
    }

    @Override
    public void redrawFrame() throws InterruptedException {
        setRepeatFrameSettings();
        drawElements();
    }

    @Override
    public void drawElements(){
        reg_login.setFont(font);
        auth_login.setFont(font);
        email.setFont(font);
        password.setFont(font);
        login_label.setFont(font);
        email_label.setFont(font);
        password_label.setFont(font);
        sign_in_label.setFont(font);

        GridBagLayout gridBagLayout = new GridBagLayout();
        sign_up_panel.setLayout(gridBagLayout);
        sign_up_panel.add(login_label,createConstrains(0,0));
        sign_up_panel.add(reg_login,createConstrains(1,0));
        sign_up_panel.add(email_label,createConstrains(0,1));
        sign_up_panel.add(email,createConstrains(1,1));
        sign_up_panel.add(sign_up_button,createConstrains(1,2));
        sign_up_button.addActionListener(sign_up_action);
        sign_up_panel.setBackground(panes_background);
        sign_up_panel.setBorder(border);
        frame.add(sign_up_panel,BorderLayout.WEST);

        empty_panel.setBackground(panes_background);
        frame.add(empty_panel,BorderLayout.CENTER);

        sign_in_panel.setLayout(gridBagLayout);
        sign_in_panel.add(sign_in_label,createConstrains(0,0));
        sign_in_panel.add(auth_login,createConstrains(1,0));
        sign_in_panel.add(password_label,createConstrains(0,1));
        sign_in_panel.add(password,createConstrains(1,1));
        sign_in_panel.add(sing_in_button,createConstrains(1,2));
        sign_in_panel.setBackground(panes_background);
        sign_in_panel.setBorder(border);
        sing_in_button.addActionListener(sing_in_action);
        frame.add(sign_in_panel,BorderLayout.EAST);

        frame.pack();
    }

    @Override
    public void setDefaultFrameSettings(){
        frame.setVisible(true);
        frame.setBounds(screen_size.width/2-250,screen_size.height/2-150,500,300);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setTitle("Authorization");
        if (frame.getWindowListeners().length < 1) frame.addWindowListener(window_event.getClosing());
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        frame.setBackground(frame_background);
        frame.getContentPane().setBackground(panes_background);
        frame.setResizable(false);
    }

    @Override
    public void setRepeatFrameSettings() {
        frame.getJMenuBar().setVisible(false);
        frame.getContentPane().removeAll();
        frame.repaint();
        setDefaultFrameSettings();
    }

    @Override
    public void showInfoDialog() {
        JOptionPane.showMessageDialog(
                frame.getContentPane(),server_answer,"Welcome!",JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void showErrorDialog() throws InterruptedException{
        int selected = JOptionPane.showConfirmDialog(
                frame.getContentPane(),"Reconnect?","Bad connection",JOptionPane.YES_NO_OPTION
        );

        if (selected == 0)
            command_exchanger.exchange("connect");
        else if (selected == 1 || selected == -1)
            command_exchanger.exchange("exit");
    }

    private GridBagConstraints createConstrains(int x, int y){
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;

        return gridBagConstraints;
    }
}
