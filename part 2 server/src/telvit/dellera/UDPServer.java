package telvit.dellera;
import java.io.*;
import java.net.*;
import java.util.HashMap;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(9876);
            System.out.println("Сервер запущен. Ожидание запросов...");

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String clientData = new String(receivePacket.getData(), 0, receivePacket.getLength());
                String[] parts = clientData.split(",");
                if (parts.length != 2) {
                    System.err.println("Ошибка: Неверный формат данных от клиента.");
                    continue;
                }

                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);

                double result = calculateFunction(x, y);


                saveToLogFile(x, y, result);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                byte[] sendData = Double.toString(result).getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double calculateFunction(double x, double y) {
        return 5 * Math.atan(x) - 1 / 4 * Math.cos((x + 3 * Math.abs(x - y) + x * x) / (Math.pow(Math.abs(x + y * y), 3) + Math.pow(x, 3)));
    }

    private static void saveToLogFile(double x, double y, double result) {
        try {
            FileWriter writer = new FileWriter("log.txt", true);
            writer.write("x: " + x + ", y: " + y + ", Результат: " + result + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}