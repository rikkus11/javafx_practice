package stage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
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

    /**
     * 別画面へ遷移、あるいは別ウィンドウとして開きます。<br />
     * 別ウィンドウの場合、モーダルウィンドウとして開きます。
     *
     * @param sceneTitle 画面タイトル
     * @param controller
     * @param newWindow
     */
    public static void moveScene(String sceneTitle, Parent controller, boolean newWindow) {

        // Sceneを取得し、無ければ新規作成する。
        Scene scene = stage.getScene();

        if (newWindow) {
            // 別ウィンドウの場合

            // 新しいモーダルウィンドウのStageを作成する。
            Stage newStage = new Stage();

            newStage.initModality(Modality.APPLICATION_MODAL);  // モーダル
            newStage.initOwner(stage);                          // 親ウィンドウ
            newStage.setTitle(sceneTitle);
            newStage.setScene(new Scene(controller));
            newStage.show();

        } else {
            // 同ウィンドウでの画面遷移の場合

            if (scene == null) {
                scene = new Scene(controller);
                stage.setScene(scene);
            } else {
                scene.setRoot(controller);
            }

            stage.setTitle(sceneTitle);
            stage.sizeToScene();
            stage.show();
        }
    }
}
