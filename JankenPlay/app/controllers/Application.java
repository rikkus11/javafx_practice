package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.formpage;
import views.html.index;

public class Application extends Controller {

    /**
     * メッセージを受け取るサンプルフォーム用フォームクラスです。
     *
     * @author リックス
     *
     */
    public static class SampleForm {

        /** メッセージ */
        public String message;

    }

    /**
     * index.scala.htmlの初期表示処理
     *
     * @return ok。引数として1, 2, 3を引き渡す
     */
    public static Result index() {
        return ok(index.render("1", "2", "3"));
    }

    /**
     * formpage.scala.htmlの初期表示処理
     *
     * @return ok。引数としてメッセージをどうぞ！及び空のフォームを引き渡す
     */
    public static Result formPage() {
        return ok(formpage.render("メッセージをどうぞ！", new Form<SampleForm>(SampleForm.class)));
    }

    public static Result send() {

        // リクエストパラメータをSampleForm二バインドする
        Form<SampleForm> f = Form.form(SampleForm.class).bindFromRequest();

        if (!f.hasErrors()) {

            // 入力にエラーが無い場合
            SampleForm inputData = f.get();
            String message = "you typed: " + inputData.message;

            return ok(formpage.render(message, f));
        } else {

            // 入力にエラーがある場合、badRequestとして元の画面へ受け取ったフォーム情報を返す。
            return badRequest(formpage.render("Error", Form.form(SampleForm.class)));
        }
    }
}
