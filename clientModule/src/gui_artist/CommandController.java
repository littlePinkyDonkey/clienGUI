package gui_artist;

import client.ClientStarter;
import controller.CommandCreator;
import model.CommandExchanger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.Exchanger;

public class CommandController implements MyFrame {
    private String server_answer;

    private Exchanger<String> exchanger;
    private CommandExchanger command_exchanger;

    private final JFrame main_frame;
    private final JPanel pane = new JPanel();
    private final JButton send_command = new JButton("Send");

    private final JComboBox<String> command_name = new JComboBox<>();
    private final JComboBox<String> position = new JComboBox<>();
    private final JTextField name_field = new JTextField(15);
    private final JTextField tired_level = new JTextField(15);
    private final JTextField quan_actions = new JTextField(15);

    private final JLabel command_label = new JLabel("Choose command: ");
    private final JLabel position_label = new JLabel("Choose position: ");
    private final JLabel name_label = new JLabel("Name: ");
    private final JLabel tired_label = new JLabel("Tired level: ");
    private final JLabel quan_label = new JLabel("Actions");

    private final ActionListener execute_command = event -> {
        try{
            new CommandCreator(command_exchanger).createCommand((String)command_name.getSelectedItem(),
                    name_field.getText(),tired_level.getText(),
                    quan_actions.getText(),(String)position.getSelectedItem());
            server_answer = exchanger.exchange(null);
            showInfoDialog();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    };

    public CommandController(Exchanger<String> exchanger, JFrame frame, String user_name){
        this.exchanger = exchanger;
        this.main_frame = frame;
        this.command_exchanger = CommandExchanger.getInstance(exchanger);
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
        command_name.addItem("clear");
        command_name.addItem("add");
        command_name.addItem("add_if_max");
        position.addItem("north");
        position.addItem("south");
        position.addItem("west");
        position.addItem("east");
        send_command.addActionListener(execute_command);

        GridBagLayout gridBagLayout = new GridBagLayout();
        pane.setLayout(gridBagLayout);
        pane.add(command_label,createConstrains(0,0));
        pane.add(command_name,createConstrains(1,0));
        pane.add(new JPanel(),createConstrains(0,1));
        pane.add(name_label,createConstrains(0,2));
        pane.add(name_field,createConstrains(1,2));
        pane.add(new JPanel(),createConstrains(0,3));
        pane.add(tired_label,createConstrains(0,4));
        pane.add(tired_level,createConstrains(1,4));
        pane.add(new JPanel(),createConstrains(0,5));
        pane.add(quan_label,createConstrains(0,6));
        pane.add(quan_actions,createConstrains(1,6));
        pane.add(new JPanel(),createConstrains(0,7));
        pane.add(position_label,createConstrains(0,8));
        pane.add(position,createConstrains(1,8));
        pane.add(new JPanel(),createConstrains(0,9));
        pane.add(send_command,createConstrains(1,10));

        main_frame.add(pane, BorderLayout.CENTER);
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
        if (server_answer.equals("successful")){
            JOptionPane.showMessageDialog(
                    main_frame.getContentPane(),"Command has been executed!",
                    null,JOptionPane.INFORMATION_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(
                    main_frame.getContentPane(),"Something wrong!",null,JOptionPane.ERROR_MESSAGE
            );
        }
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
