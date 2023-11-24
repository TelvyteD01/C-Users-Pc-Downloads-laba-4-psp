package telvit.dellera;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);

            System.out.print("Введите значение x: ");
            double x = scanner.nextDouble();
            System.out.print("Введите значение y: ");
            double y = scanner.nextDouble();

            String message = x + "," + y;
            byte[] sendData = message.getBytes();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 9876;

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String result = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Расчетное значение функции: " + result);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}