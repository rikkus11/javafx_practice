package controller.base;

import java.io.IOException;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import stage.StageManager;

public abstract class AbstractController<T extends Parent> {

    /**
     * {@link #getFXMLName()}で取得されるFXMLを読み込みます。<br />
     * cssとして{@link #getCssName()}で取得されるcssファイルを読み込みます。
     *
     * @param newWindow 新ウィンドウで開くかどうかの真偽値
     */
    public void loadFXML(boolean newWindow) {

        String fxmlName = getFXMLName();
        if (fxmlName == null || fxmlName.isEmpty()) {
            throw new IllegalArgumentException("FXMLファイル名を指定してください");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/base/" + fxmlName + ".fxml"));

        loader.setController(this);

        T pane;

        try {

            // 読み込む。
             pane = loader.load();

            // CSSが指定されていれば設定する。
            String cssName = getCssName();
            if (cssName != null && !cssName.isEmpty()) {
                pane.getStylesheets().add(cssName);
            }

        } catch (IOException e) {

            // エラー処理はとりあえず適当。
            throw new RuntimeException(e);
        }

        // 読み込んだらそのまま画面を遷移させる。
        StageManager.moveScene(getSceneTitle(), pane, newWindow);

        // Paneを返す必要は無さそうなのでとりあえずvoid
    }

    /**
     * 継承先で、パラメータ設定処理を行います。
     *
     * @param parameterMap パラメータマップ
     */
    public abstract void setParameterMap(Map<String, String> parameterMap);

    /**
     * コントローラが使用するFXMLファイル名を取得します。
     *
     * @return FXMLファイル名
     */
    protected abstract String getFXMLName();

    /**
     * コントローラが使用するcssファイル名を取得します。<br />
     * 使用しない場合はnullを返却します。
     *
     * @return cssファイル名
     */
    protected abstract String getCssName();

    /**
     * コントローラが使用する画面のタイトルを取得します。
     *
     * @return 画面タイトル
     */
    protected abstract String getSceneTitle();
}
