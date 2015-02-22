package controller;

import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import controller.base.AbstractController;

public class InquiryController extends AbstractController<AnchorPane> {

    private String name;

    @FXML
    private TextField txtName;

    @FXML
    public void initialize() {
        txtName.setText(name);
    }

    @FXML
    protected void clickBtnSend(ActionEvent ev) {

    }

    @FXML
    protected void clickBtnBack(ActionEvent ev) {

    }

    @Override
    protected String getFXMLName() {
        return "Inquiry";
    }

    @Override
    protected String getCssName() {
        return null;
    }

    @Override
    protected String getSceneTitle() {
        return "お問い合わせ";
    }

    /**
     * パラメータから名前を取得し、フィールドに設定します。
     *
     * @param parameterMap パラメータマップ
     */
    @Override
    public void setParameterMap(Map<String, String> parameterMap) {
        this.name = parameterMap.getOrDefault("name", "");
    }
}
