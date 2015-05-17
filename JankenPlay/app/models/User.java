package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * ユーザ認証用USERテーブルのモデルクラスです。
 *
 * @author リックス
 *
 */
@Entity
@Table(name = "user_id")
public class User extends Model {

    /** ユーザ検索用Finder */
    public static final Finder<Long, User> find = new Finder<>(Long.class, User.class);

    /** 主キーとなる連番 */
    @Id
    public Long id;

    /** ユーザ名(ログインID) */
    @Required
    @Pattern(message = "半角英数字で入力してください", value = "[a-zA-Z0-9]+")
    public String username;

    /** パスワード */
    @Required
    @Pattern(message = "半角英数字で入力してください", value = "[a-zA-Z0-9]+")
    public String password;
}
