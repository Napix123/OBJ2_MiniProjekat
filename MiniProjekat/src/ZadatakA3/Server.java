package ZadatakA3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class Server {
    public static void main(String[] args)  {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);
            System.out.println("Server je pokrenut...\n");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Klijent povezan: " + socket);

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                String input = inputStream.readUTF();

                String response = isPalindrom(input) ? "Rec je palindrom" : "Rec nije palindrom";

                outputStream.writeUTF(response);
                outputStream.flush();

                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isPalindrom(String str) {
        String ciscenje = str.replace("\\s", "").toLowerCase();
        return ciscenje.equals(new StringBuilder(ciscenje).reverse().toString());
    }
}
