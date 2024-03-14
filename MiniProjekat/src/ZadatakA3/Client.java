package ZadatakA3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Client extends Application {

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Klijent - Zadatak A3");

        TextField inputTextField = new TextField();
        Button checkButton = new Button("Posalji");
        Label resultLabel = new Label();

        checkButton.setOnAction(e -> {
            String input = inputTextField.getText().trim();
            if (!input.isEmpty()) {
                sendToServer(input, resultLabel);
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(inputTextField, checkButton, resultLabel);

        Scene scene = new Scene(layout, 300, 150);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void sendToServer(String input, Label resultLabel)  {
        try {
            Socket socket = new Socket("127.0.0.1", 9000);

            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            outputStream.writeUTF(input);
            outputStream.flush();

            String response = inputStream.readUTF();

            resultLabel.setText("Odgovor od servera: " + response);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
