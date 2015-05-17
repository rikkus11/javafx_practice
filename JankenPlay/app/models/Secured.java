package models;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;
import controllers.routes;

/**
 * USER_IDテーブルデータによる、認証用Securityヘルパークラスです。
 *
 * @author リックス
 *
 */
public class Secured extends Authenticator {

    /** セッション上のユーザ名キー */
    public static final String KEY_USER_NAME = "username";

    /**
     * ユーザ名を取得する。セッション上のユーザ名キーで取得する。
     */
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get(KEY_USER_NAME);
    }

    /**
     * 未認証時の処理を行います。未認証時ログイン後戻り先ＵＲＬをセッションに設定して、ログイン画面へ繊維します。
     */
    @Override
    public Result onUnauthorized(Context ctx) {

        // 戻り先ＵＲＬは、リクエスト先のＵＲＬを使用するため取得
        String returnUrl = ctx.request().uri();

        if (returnUrl == null) {

            // NULLの場合はメッセージボードトップへ
            returnUrl = "/message_board";
        }

        // 戻り先をセッションに設定
        ctx.session().put("returnUrl", returnUrl);

        // ログインページ表示用メソッドヘリダイレクト
        return redirect(routes.MessageBoard.showLogin());
    }

}
