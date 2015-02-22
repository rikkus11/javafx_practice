package application;

import javafx.application.Application;
import javafx.stage.Stage;
import stage.StageManager;
import controller.TitleController;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            // Stageをマネージャに引き渡す
            StageManager.setStage(primaryStage);

            // 最初はTitle君を読み込む
            new TitleController().loadFXML();

            // AnchorPane titlePane = FXMLLoader.load(getClass().getResource("Title.fxml"));
            // Scene scene = new Scene(titlePane);
            /*
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root,400,400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());*/
            //primaryStage.setScene(scene);
            //primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
