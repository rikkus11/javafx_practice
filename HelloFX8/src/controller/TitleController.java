package controller;

import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import controller.base.AbstractController;

/**
 * Title画面のイベント処理が記述されるコントローラクラスです。
 *
 * @author リックス
 *
 */
public class TitleController extends AbstractController<AnchorPane> {

    // FXMLアノテーションをつけないと、fxmlで指定していても注入されたり、イベントとして認識されないので注意する。

    @FXML
    private TextField txtName;

    @FXML
    private Button btnSend;

    @FXML
    private Label lblAnswer;

    public TitleController() {

        // コンストラクタで他の画面からデータを受け取ったりできる。Title君は最初の画面なので何も無いですが一応。

    }

    /**
     * <p>
     * Sendボタン押下時の処理を行います。
     * </p>
     * <p>
     * 入力された名前を取得し、lblAnswerのメッセージを設定します。
     * </p>
     * @param ev
     */
    @FXML
    protected void clickBtnSend(ActionEvent ev) {
        String name = txtName.getText();
        lblAnswer.setText("Hello, " + name + "さん！！");
    }

    @FXML
    protected void clickBtnInquiry(ActionEvent ev) {

        // お問い合わせ画面へ遷移する。パラメータとして入力された名前を設定
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("name", txtName.getText());

        InquiryController inquiryController = new InquiryController();
        inquiryController.setParameterMap(parameterMap);
        inquiryController.loadFXML();
    }

    @FXML
    public void initialize() {
        // 初期化処理。引数は使わなくても良い。コントローラにJava側で何か値を設定したりする時に使用

    }

    @Override
    protected String getFXMLName() {
        return "Title";
    }

    @Override
    protected String getCssName() {
        return null;
    }

    @Override
    protected String getSceneTitle() {
        return "Title君";
    }

    /**
     * パラメータを使用しないため処理しません。
     *
     * @param parameterMap
     */
    @Override
    public void setParameterMap(Map<String, String> parameterMap) {}
}
