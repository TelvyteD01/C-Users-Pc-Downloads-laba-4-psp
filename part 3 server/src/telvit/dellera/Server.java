package telvit.dellera;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        final int PORT = 12345;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер ожидает подключения...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Подключился клиент: " + clientSocket.getInetAddress().getHostAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                int m = Integer.parseInt(in.readLine());
                int n = Integer.parseInt(in.readLine());

                long result = factorial(m) + factorial(n);
                out.println(result);

                clientSocket.close();
                System.out.println("Соединение закрыто.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long factorial(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}
