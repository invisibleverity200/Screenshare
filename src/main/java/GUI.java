import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class GUI extends JFrame {
    volatile String ip = "192.168.1.102";
    volatile int port = 1337;
    volatile long fps = 30;

    GUI() {
        super();

        JPanel panel = new JPanel();

        JMenu menu = new JMenu("Settings");
        JMenuItem item = new JMenuItem("Settings");
        JMenuBar jMenuBar = new JMenuBar();
        item.addActionListener((ActionEvent e) -> {
            JDialog jDialog = new JDialog();
            jDialog.setLayout(new GridLayout(0, 2));

            JLabel ipLabel = new JLabel("IP:");
            JLabel portLabel = new JLabel("Port:");
            JLabel fpsLabel = new JLabel("Fps:");
            JLabel spacer = new JLabel();

            JTextField ip = new JTextField("192.168.1.102");
            JTextField port = new JTextField("1337");
            JTextField fps = new JTextField("30");

            JButton apply = new JButton("Apply!");
            apply.addActionListener((ActionEvent e2) -> {
                this.port = Integer.valueOf(port.getText());
                this.ip = ip.getText();
                this.fps = Long.valueOf(fps.getText());
            });

            jDialog.add(ipLabel);
            jDialog.add(ip);
            jDialog.add(portLabel);
            jDialog.add(port);
            jDialog.add(fpsLabel);
            jDialog.add(fps);
            jDialog.add(spacer);
            jDialog.add(apply);

            jDialog.setSize(400, 400);
            jDialog.setVisible(true);
        });

        menu.add(item);
        jMenuBar.add(menu);
        setJMenuBar(jMenuBar);

        JButton connectButton = new JButton("Connect!");
        connectButton.addActionListener((ActionEvent e) -> {
            try {
                this.setSize(1920, 1080);
                this.setResizable(false);
                new Thread(new ReceiveSocket(ip, port, new MyCanvas(this))).start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.remove(connectButton);
        });

        panel.add(connectButton);

        this.setContentPane(panel);

        this.setVisible(true);
        this.setSize(200, 100);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
