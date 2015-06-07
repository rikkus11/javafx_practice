package controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import models.Member;
import models.Message;
import models.Secured;
import models.User;
import play.data.Form;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.add_member;
import views.html.login;
import views.html.message_detail_search;
import views.html.message_edit;
import views.html.message_post;
import views.html.message_search;
import views.html.messageboard;
import views.html.newUser;
import forms.DetailSearchForm;
import forms.LoginForm;

public class MessageBoard extends Controller {

    /**
     * <p>メッセージボードトップページにアクセスした際の処理</p>
     * <p>全メッセージを読み込み、messageboardを出力します。</p>
     *
     * @return ok
     */
    @Authenticated(Secured.class)   // Securedによる認証ＯＫ時のみ表示できる(クラスにつければ全メソッド対象になる)
    public static Result index() {

        // Messageを全て検索する。構文が凄い分かりやすいい！！いいね！
        List<Message> messageDatas = Message.find.all();

        // Memberも。
        List<Member> memberDatas = Member.find.all();

        return ok(messageboard.render(messageDatas, memberDatas));

    }

    /**
     * <p>メッセージボード投稿ページにアクセスした際の処理</p>
     * <p>message_postを出力します。</p>
     *
     * @return ok
     */
    public static Result postPage() {

        // 空フォームでmessage_postを出力
        Form<Message> defaultForm = new Form<>(Message.class);

        return ok(message_post.render(defaultForm));
    }

    /**
     * <p>メッセージボード投稿ページからの送信処理</p>
     * <p>入力値のバリデーションチェック後、Messageテーブルに登録します。</p>
     *
     * @return バリデーションエラー時：badrequest。正常終了時：ok
     */
    public static Result create() {

        // フォーム情報を取得
        Form<Message> input = Form.form(Message.class).bindFromRequest();

        // バインド時にアノテーションチェックを行い、エラーがある場合はtrue
        // Form#hasErrorsはバインドエラーの検知であって、バインド系メソッドを呼ばなければtrueにはならない。
        if (!input.hasErrors()) {

            // エラーが無い場合、入力値をテーブルに格納(Model#saveを呼ぶだけ)
            Message data = input.get();

            // Member紐付け化。Memberを同じ名前で探してひも付けてから保存する
            data.member = Member.findByName(data.name);
            data.save();

            // 登録完了メッセージを出しても良いが、ひとまず一覧ページに戻す
            return redirect(routes.MessageBoard.index());
        } else {

            // エラーの場合、再表示
            return badRequest(message_post.render(input));
        }
    }

    /**
     * <p>メンバー登録ページにアクセスした際の処理</p>
     * <p>add_memberを出力します。</p>
     *
     * @return ok
     */
    public static Result addMemberPage() {

        // 空フォームでmessage_postを出力
        Form<Member> defaultForm = new Form<>(Member.class);

        return ok(add_member.render(defaultForm));
    }

    /**
     * <p>メンバー登録ページからの送信処理</p>
     * <p>入力値のバリデーションチェック後、Messageテーブルに登録します。</p>
     *
     * @return バリデーションエラー時：badrequest。正常終了時：ok
     */
    public static Result createMember() {

        // フォーム情報を取得
        Form<Member> input = Form.form(Member.class).bindFromRequest();

        // バインド時にアノテーションチェックを行い、エラーがある場合はtrue
        // Form#hasErrorsはバインドエラーの検知であって、バインド系メソッドを呼ばなければtrueにはならない。
        if (!input.hasErrors()) {

            // エラーが無い場合、入力値をテーブルに格納(Model#saveを呼ぶだけ)
            Member data = input.get();
            data.save();

            // 登録完了メッセージを出しても良いが、ひとまず一覧ページに戻す
            return redirect(routes.MessageBoard.index());
        } else {

            // エラーの場合、再表示
            return badRequest(add_member.render(input));
        }
    }

    /**
     * <p>メッセージ検索ページにアクセスした際の処理</p>
     * <p>message_searchを出力します。</p>
     *
     * @return ok
     */
    public static Result searchPage() {
        Form<Message> f = new Form<>(Message.class);

        return ok(message_search.render("IDを入力", f));
    }

    /**
     * <p>メッセージ検索ページからの送信処理</p>
     * <p>メッセージ編集画面となるmessage_editを出力します。
     *
     * @return ok。データが見つかった場合はmessage_editを、見つからない場合はmessage_searchを出力<br />
     */
    public static Result editPage() {
        Form<Message> f = Form.form(Message.class).bindFromRequest();

        if (!f.hasErrors()) {
            Message input = f.get();

            // 入力されたIDでMessageを検索
            Message searchResult = Message.find.byId(input.id);

            if (searchResult != null) {

                // 検索結果がある場合、message_edit用フォームに設定して引き渡す
                // Form#fillにオブジェクトを渡すとその値で埋めてくれる。
                Form<Message> editForm = new Form<>(Message.class).fill(searchResult);
                return ok(message_edit.render("データを編集し、送信ボタンを押してください。", editForm));
            } else {

                // 結果が無い場合、message_searchへ戻す。
                return ok(message_search.render("ERROR：IDの投稿が見つかりません。", f));
            }

        } else {
            return ok(message_search.render("ERROR：入力に問題があります。", f));
        }
    }

    /**
     * 更新処理を行います。
     *
     * @return ok
     */
    public static Result update() {
        Form<Message> f = Form.form(Message.class).bindFromRequest();

        if (!f.hasErrors()) {

            // 取得したフォームに該当するレコードをアップデート、ID、更新値は画面入力済み
            Message input = f.get();
            input.update();

            return redirect(routes.MessageBoard.index());
        } else {
            return ok(message_edit.render("ERROR：入力に問題があります。", f));
        }
    }

    /**
     * 詳細検索ページの表示
     *
     * @return ok
     */
    public static Result showDetailSearch() {
        Form<DetailSearchForm> f = new Form<DetailSearchForm>(DetailSearchForm.class);

        // 初期表示時は条件無しで全件表示する
        List<Message> datas = Message.find.where().orderBy("name desc").findList();

        return ok(message_detail_search.render("検索条件を入力してください。", f, datas));
    }

    /**
     * 詳細検索処理
     *
     * @return ok
     */
    public static Result detailSearch() {

        // フォーム復元
        Form<DetailSearchForm> f = Form.form(DetailSearchForm.class).bindFromRequest();

        List<Message> datas = null;

        if (!f.hasErrors()) {

            DetailSearchForm input = f.get();

            // 名前が一致するものを検索、部分検索の場合は大小文字区別しないilike検索を使用する
            if (input.isLike) {

                datas = Message.find.where().ilike("name", "%" + input.name + "%").orderBy("name desc").findList();
                // 試しに、2こ1ページの最初のページ取得をしてみる
                // datas = Message.find.where().ilike("name", "%" + input.name + "%").findPagingList(2).getPage(0).getList();
            } else {
                datas = Message.find.where().eq("name", input.name).orderBy("name desc").findList();
            }

            return ok(message_detail_search.render("名前：" + input.name + "で検索しました", f, datas));
        } else {
            return badRequest(message_detail_search.render("名前を入力してください。", f, datas));
        }
    }

    /**
     * ログイン画面表示処理
     *
     * @return ok
     */
    @AddCSRFToken
    public static Result showLogin() {
        return ok(login.render("ログイン情報を入力してください。", new Form<>(LoginForm.class)));
    }

    /**
     * ログイン処理
     *
     * @return ok or badrequest
     */
    @RequireCSRFCheck
    public static Result login() {
        Form<LoginForm> f = Form.form(LoginForm.class).bindFromRequest();

        if (!f.hasErrors()) {

            // ユーザを検索し、一致すればログイン。ユーザ名設定する事で、Secured.javaで認識できる
            session(Secured.KEY_USER_NAME, f.get().getUsername());

            // Securedでいれた戻り先URL
            String returnUrl = ctx().session().get("returnUrl");

            if (returnUrl == null || returnUrl.isEmpty()) {
                returnUrl = routes.MessageBoard.index().url();
            }

            return redirect(returnUrl);

        } else {

            // フォームエラー、認証エラー時。FormのvalidateがSecurityヘルパーに呼ばれて、認証してくれてるのかな。
            return badRequest(login.render("入力エラーがあります。", f));
        }
    }

    /**
     * ログアウト処理
     *
     * @return redirect
     */
    public static Result logout() {
        session().clear();
        return redirect(routes.MessageBoard.showLogin());
    }

    /**
     * ユーザ新規作成ページ表示
     * @return
     */
    public static Result showNewUser() {
        return ok(newUser.render(new Form<>(User.class)));
    }

    /**
     * ユーザ新規作成処理
     * @return
     */
    public static Result newUser() {
        Form<User> f = Form.form(User.class).bindFromRequest();

        if (!f.hasErrors()) {
            User user = f.get();

            try {
                user.password = LoginForm.sha512(user.password);
            } catch (NoSuchAlgorithmException e) {
                return badRequest(newUser.render(f));
            }
            user.save();

            return redirect(routes.MessageBoard.showLogin());
        } else {
            return badRequest(newUser.render(f));
        }
    }
}
