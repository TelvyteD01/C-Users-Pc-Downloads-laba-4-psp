package telvit.dellera;
import java.io.*;
import java.net.*;

public class RandomNumberClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        try (Socket clientSocket = new Socket(serverAddress, serverPort);
             BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in)) ) {

            System.out.print("Введите целое положительное число N: ");
            int n = Integer.parseInt(userInput.readLine());

            output.println(n);

            System.out.println("Случайные числа от сервера:");
            for (int i = 0; i < n; i++) {
                String randomNumber = input.readLine();
                System.out.println(randomNumber);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}