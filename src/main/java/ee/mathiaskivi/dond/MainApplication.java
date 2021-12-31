package ee.mathiaskivi.dond;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scene_main.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Deal or No Deal");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

        stage.requestFocus();
    }

    public static void main(String[] arguments) {
        launch();
    }
}
