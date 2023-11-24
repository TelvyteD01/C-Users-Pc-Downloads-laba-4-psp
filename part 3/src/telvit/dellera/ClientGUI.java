package telvit.dellera;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientGUI {
    private static final String DEFAULT_SERVER_IP = "127.0.0.1"; // IP-адрес сервера по умолчанию
    private static final int DEFAULT_SERVER_PORT = 12345; // Порт сервера по умолчанию

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private JFrame frame;
    private JTextField serverIpField;
    private JTextField serverPortField;
    private JTextField mField;
    private JTextField nField;
    private JLabel resultLabel;

    public ClientGUI() {
        frame = new JFrame("Клиент");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel serverIpLabel = new JLabel("IP-адрес сервера:");
        serverIpField = new JTextField(DEFAULT_SERVER_IP, 10);
        JLabel serverPortLabel = new JLabel("Порт сервера:");
        serverPortField = new JTextField(String.valueOf(DEFAULT_SERVER_PORT), 10);
        JLabel mLabel = new JLabel("m:");
        mField = new JTextField(10);
        JLabel nLabel = new JLabel("n:");
        nField = new JTextField(10);
        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ConnectButtonListener());
        JButton calculateButton = new JButton("Вычислить");
        calculateButton.addActionListener(new CalculateButtonListener());
        resultLabel = new JLabel("");

        panel.add(serverIpLabel);
        panel.add(serverIpField);
        panel.add(serverPortLabel);
        panel.add(serverPortField);
        panel.add(connectButton);
        panel.add(new JLabel("")); // Пустая метка для выравнивания
        panel.add(mLabel);
        panel.add(mField);
        panel.add(nLabel);
        panel.add(nField);
        panel.add(new JLabel("")); // Пустая метка для выравнивания
        panel.add(calculateButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(resultLabel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientGUI());
    }

    private class ConnectButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String serverIp = serverIpField.getText();
            int serverPort = Integer.parseInt(serverPortField.getText());
            try {
                socket = new Socket(serverIp, serverPort);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                JOptionPane.showMessageDialog(frame, "Успешное подключение к серверу.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Ошибка подключения к серверу: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (socket == null || !socket.isConnected()) {
                JOptionPane.showMessageDialog(frame, "Подключение к серверу не установлено.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int m = Integer.parseInt(mField.getText());
                int n = Integer.parseInt(nField.getText());

                out.println(m);
                out.println(n);

                long result = Long.parseLong(in.readLine());
                resultLabel.setText("Результат: " + result);

                // Закрываем соединение после завершения запроса
                out.close();
                in.close();
                socket.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Ошибка при обмене данными с сервером: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}