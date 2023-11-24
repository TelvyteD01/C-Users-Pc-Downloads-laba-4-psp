package telvit.dellera;
import java.io.*;
import java.net.*;
import java.util.Random;

public class RandomNumberServer {
    public static void main(String[] args) {
        int port = 12345;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен и ожидает подключения клиентов...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключился: " + clientSocket.getInetAddress());


                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);


            int n = Integer.parseInt(in.readLine());
            System.out.println("Клиент запросил случайные числа в диапазоне от 1 до " + n);


            Random random = new Random();
            for (int i = 0; i < n; i++) {
                int randomNumber = random.nextInt(n) + 1;
                out.println(randomNumber);
            }


            clientSocket.close();
            System.out.println("Соединение с клиентом закрыто.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}