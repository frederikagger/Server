import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GUI extends JFrame implements ActionListener {
    private JFrame frame;
    private JButton send;
    private JButton connect;
    private JTextField textField;
    private JTextArea jTextArea;
    private JPanel jPanel;
    private MigLayout migLayout;
    private Server server;
    private Client client;
    private ExecutorService executor;
    private JScrollPane scroll;

    public GUI() {
        this.migLayout = new MigLayout();
        this.frame = new JFrame("Chat");
        this.send = new JButton("Send");
        this.connect = new JButton("Connect");
        this.textField = new JTextField(60);
        this.jTextArea = new JTextArea(10,60);
        this.scroll = new JScrollPane(jTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.jPanel = new JPanel();
        jPanel.setLayout(migLayout);
        jPanel.add(jTextArea, "span 2");
        jPanel.add(connect, "wrap");
        jPanel.add(textField, "span 2");
        jPanel.add(send);
        frame.add(scroll);
        frame.getContentPane().add(jPanel);
       // frame.getContentPane().add(scroll);
        frame.setSize(500,250);
        frame.setLocation(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        send.setBackground(Color.GRAY);
        connect.setBackground(Color.GRAY);
        send.addActionListener(this::actionPerformed);
        connect.addActionListener(this::actionPerformed);
        send.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"),"sendMessage");
        send.getActionMap().put("sendMessage", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.sendMessage(textField.getText());
                    jTextArea.append("Me: "+textField.getText()+"\n");
                    textField.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        executor = Executors.newFixedThreadPool(2);
        server = new Server(6000, this);
        executor.execute(server);
    }

    public JTextArea getjTextArea() {
        return jTextArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connect){
            client = new Client("127.0.0.1", 5000);
            executor.execute(client);
        }
        if (e.getSource() == send){
            try {
                client.sendMessage(textField.getText());
                jTextArea.append("Me: "+textField.getText()+"\n");
                textField.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
