package gui_artist;

import controller.Windows;
import controller.LogInCreator;
import model.CommandExchanger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.Exchanger;

public class LogInFrame implements MyFrame {
    private Exchanger<String> command_exchanger;
    private Exchanger<Boolean> connection_exchanger;
    private CommandExchanger command_sender;

    private JFrame frame;
    private final Dimension screen_size;
    private final JPanel sign_in_panel = new JPanel();
    private final JPanel sign_up_panel = new JPanel();
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
        String reg_login = this.reg_login.getText();
        String email = this.email.getText();

        server_answer = new LogInCreator(command_sender).registration(reg_login,email);

        this.reg_login.setText(null);
        this.email.setText(null);
    };
    private final ActionListener sing_in_action = event -> {
        try{
            String auth_login = this.auth_login.getText();
            String password = new String(this.password.getPassword());

            //не ответ сервера, пофиксить
            new LogInCreator(command_sender).authorization(auth_login,password);

            server_answer = command_exchanger.exchange(null);
            System.out.println(server_answer);
            if (server_answer.equals("successful"))
                new MainFrame(command_exchanger,frame);

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

    @Override
    public void drawFrame() throws InterruptedException{
        frame.setVisible(true);
        frame.setBounds(screen_size.width/2-400,screen_size.height/2-400,800,800);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setTitle("Authorization");
        frame.addWindowListener(window_event.getClosing());
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        frame.setBackground(frame_background);
        frame.getContentPane().setBackground(panes_background);
        frame.setResizable(false);

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
        while (connection_exchanger.exchange(null))
            showDialog();
    }

    private void showDialog() throws InterruptedException{
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
