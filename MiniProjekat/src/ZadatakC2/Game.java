package ZadatakC2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Random;

public class Game extends Application {

    private final int size = 4;
    private final int buttonSize = 50;
    private final int gap = 5;
    private Button[] buttons = new Button[size * size];
    private Random random = new Random();

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setHgap(gap);
        grid.setVgap(gap);

        for (int i = 0; i < size * size; i++) {
            final int index = i;
            buttons[i] = new Button(String.valueOf(i));
            buttons[i].setMinSize(buttonSize, buttonSize);
            buttons[i].setMaxSize(buttonSize, buttonSize);
            buttons[i].setOnAction(e -> handleButtonClick(index));
            grid.add(buttons[i], i % size, i / size);
        }

        Button startButton = new Button("START");
        startButton.setOnAction(e -> startGame());
        grid.add(startButton, size, size);

        primaryStage.setScene(new Scene(grid, (size * buttonSize) + ((size + 1) * gap), (size * buttonSize) + ((size + 1) * gap)));
        primaryStage.show();
    }

    private void startGame() {
        shuffle();
        for (int i = 0; i < size * size; i++) {
            if (buttons[i].getText().equals("0")) {
                buttons[i].requestFocus();
                break;
            }
        }
    }

    private void shuffle() {
        for (int i = 0; i < size * size; i++) {
            int j = random.nextInt(size * size);
            swap(i, j);
        }
    }

    private void swap(int i, int j) {
        String temp = buttons[i].getText();
        buttons[i].setText(buttons[j].getText());
        buttons[j].setText(temp);
    }

    private void handleButtonClick(int i) {
        int zeroIndex = -1;
        for (int j = 0; j < size * size; j++) {
            if (buttons[j].getText().equals("0")) {
                zeroIndex = j;
                break;
            }
        }

        if (isNeighbor(i, zeroIndex)) {
            swap(i, zeroIndex);
            if (isSolved()) {
                System.out.println("Pobedili ste!");
            }
        }
    }

    private boolean isNeighbor(int i, int j) {
        int rowI = i / size;
        int colI = i % size;
        int rowJ = j / size;
        int colJ = j % size;

        return (rowI == rowJ && Math.abs(colI - colJ) == 1) || (colI == colJ && Math.abs(rowI - rowJ) == 1);
    }

    private boolean isSolved() {
        for (int i = 0; i < size * size - 1; i++) {
            if (Integer.parseInt(buttons[i].getText()) != i) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}