package org.raul;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.raul.ticTacToe.TicTacToe;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    private static TicTacToe ticTacToe;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        scene = new Scene(loadFXML("mainMenu"));

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest((windowEvent) -> Platform.exit());
        stage.show();
    }

    public static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
            stage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getClassLoader().getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void newGame() {
        ticTacToe = new TicTacToe();
    }

    public static TicTacToe getGame() {
        return ticTacToe;
    }

}
