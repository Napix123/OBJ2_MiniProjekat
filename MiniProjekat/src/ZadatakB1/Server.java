package ZadatakB1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            // Stvaranje ServerSocket-a
            ServerSocket serverSocket = new ServerSocket(12345);

            // Slusanje konekcija
            System.out.println("Cekanje povezivanja sa klijentom...");
            Socket socket = serverSocket.accept();

            // Uzmi input stream
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            // Dobijanje fajla od Klijenta
            File file = (File) objectInputStream.readObject();

            // Cuvanje fajla u folderu
            File directory = new File("C:\\Users\\nikpo\\OneDrive\\Desktop\\DATA_SERVER");
            if (!directory.exists()) {
                directory.mkdir();
            }
            File savedFile = new File(directory, file.getName());
            byte[] buffer = new byte[1024];
            FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream fileOutputStream = new FileOutputStream(savedFile);
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileInputStream.close();
            fileOutputStream.close();
            // Slanje odgovora klijentu
            System.out.println("Fajl dobijen: " + file.getName());
            System.out.println("Cuvanje fajla u : " + savedFile.getAbsolutePath());
            socket.getOutputStream().write("Fajl uspesno preuzet.".getBytes());

            // Zatvaranje
            objectInputStream.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}