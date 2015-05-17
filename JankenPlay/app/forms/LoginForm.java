package forms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import models.User;
import play.data.validation.Constraints.Required;

/**
 * ログイン画面用のフォームモデルです。
 *
 * @author リックス
 *
 */
public class LoginForm {

    /** ユーザ名 */
    @Required
    private String username;

    /** パスワード */
    @Required
    private String password;

    /**
     * usernameを取得します。
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * usernameを設定します。
     *
     * @param username セットする username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * passwordを取得します。
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * passwordを設定します。
     *
     * @param password セットする password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * ログイン時のバリデーションチェックを行います。このメソッドはSecurityヘルパー使用時に必要です。
     *
     * @return エラーメッセージ。バリデーション結果が正常の場合はnull
     * @throws NoSuchAlgorithmException パスワードのハッシュ化失敗時
     */
    public String validate() {
        try {
            if (authenticate(username, password) == null) {
                return "ユーザ名かパスワードが誤っています。";
            }

            return null;
        } catch (NoSuchAlgorithmException e) {
            return "認証時エラーが発生しました。";
        }
    }

    /**
     * 指定されたユーザ名とパスワードのユーザを返します。ユーザが存在しない場合nullを返します。
     *
     * @param username ユーザ名
     * @param password パスワード
     * @return 該当ユーザ。存在しない場合はnull
     * @throws NoSuchAlgorithmException パスワードのハッシュ化失敗時
     */
    public User authenticate(String username, String password) throws NoSuchAlgorithmException {

        // パスワード入力がある場合、ハッシュ化する
        String hashedPassword = "";

        if (password != null) {
            hashedPassword = sha512(password);
        }

        // ユーザ検索して返す。
        return User.find.where().eq("username", username).eq("password", hashedPassword).findUnique();
    }

    /**
     * 文字列をSHA-512アルゴリズムでハッシュ化します。
     *
     * @param message ハッシュ対象文字列
     * @return SHA-512アルゴリズムによりハッシュ化された文字列
     * @throws NoSuchAlgorithmException ハッシュ化失敗時
     */
    public static String sha512(String message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        StringBuffer sb = new StringBuffer();

        // 渡されたメッセージのバイトを渡してハッシュ、バイト列を受け取る
        md.update(message.getBytes());
        byte[] mb = md.digest();

        // StringBufferに並列で詰めてく
        for (byte b : mb) {

            // 1バイトずつ受け取り、2桁を文字列に戻して格納する
            String hex = String.format("%02x", b);
            sb.append(hex);
        }

        return sb.toString();
    }
}
