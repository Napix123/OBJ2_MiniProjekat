package ZadatakB1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Application {

    private Label responseLabel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Stvaranje File Chooser-a
        FileChooser fileChooser = new FileChooser();

        // Dugme za biranje fajla
        Button chooseFileButton = new Button("Izaberi Fajl");
        chooseFileButton.setOnAction(event -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                sendFileToServer(selectedFile);
            }
        });

        // Label za odgovor od servera
        responseLabel = new Label("Odgovor servera: ");

        // Dugme i Label u layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(chooseFileButton, responseLabel);

        // Scena
        Scene scene = new Scene(layout, 300, 200);


        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void sendFileToServer(File file) {
        try {
            // Povezivanje sa serverom
            Socket socket = new Socket("localhost", 12345);

            // Uzimanje output stream-a
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            // Slanje fajla na server
            objectOutputStream.writeObject(file);

            // Zatvaranje
            objectOutputStream.close();
            outputStream.close();
            socket.close();

            // Prikazivanje odgovor servera
            responseLabel.setText("Fajl uspesno poslat!");
        } catch (UnknownHostException e) {
            responseLabel.setText("Error: Unknown host");
        } catch (IOException e) {
            responseLabel.setText("Error: IOException");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}