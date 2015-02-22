package stage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 起動時に取得されるStageを保持し、ページ遷移機能を提供するクラスです。
 *
 * @author リックス。
 *
 */
public final class StageManager {

    /**
     * 保持するStageインスタンス
     */
    private static Stage stage;

    /**
     * このマネージャが保持するStageインスタンスを設定します。
     *
     * @param primaryStage このマネージャが保持するStageインスタンス
     */
    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * このマネージャが保持するStageインスタンスを取得します。
     *
     * @return このマネージャが保持するStageインスタンス
     */
    public static Stage getStage() {
        return stage;
    }

    public static void moveScene(String sceneTitle, Parent controller) {

        // Sceneを取得し、無ければ新規作成する。
        Scene scene = stage.getScene();

        if (scene == null) {
            scene = new Scene(controller);
            stage.setScene(scene);
        } else {
            scene.setRoot(controller);
        }

        stage.sizeToScene();
        stage.show();
    }
}
