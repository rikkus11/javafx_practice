package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.annotation.CreatedTimestamp;

/**
 * メッセージ投稿システム・メッセージテーブルのエンティティクラスです。
 *
 * @author リックス
 *
 */
@Entity
public class Message extends Model {

    /** 投稿ID、プライマリキーとしてユニークIDが割り振られます。 */
    @Id
    public Long id;

    /** 投稿者名 */
    @Required(message = "必須項目です。")
    @MaxLength(16)
    public String name;

    /** メッセージ本文 */
    @Required(message = "必須項目です。")
    @Pattern(message = "半角英数字のみ入力してください", value = "[a-zA-Z]+")
    public String message;

    /** 投稿日時 */
    @CreatedTimestamp
    public Date postDate;

    /**
     * 投稿者。MEMBERテーブルと1 - 多で紐付けるためManyToOneを付与。もしMessageのUpdate時にMemberが代わってればそれも全てUpdateする
     * Message側はManyToOne、つまり1人のユーザが投稿した全部のメッセージを管理する。こっちはMessageなので、1人のユーザを持つ
     */
    @ManyToOne(cascade = CascadeType.ALL)
    public Member member;

    /** Messageレコード検索用Finder。主キーLongと取得されるエンティティ(Message)を設定しています */
    public static Finder<Long, Message> find = new Finder<>(Long.class, Message.class);

    /**
     * インスタンスの設定値を文字列として返します。
     */
    @Override
    public String toString() {
        return "Message [id=" + id + ", name=" + name + ", message=" + message + ", postDate="
                + postDate + "]";
    }

    /**
     * NAMEを指定して、レコードを取得します。複数レコード存在する場合先頭1件を取得します。
     *
     * @param name 検索する名前
     * @return 検索結果、存在しない場合はnull
     */
    public static Message findByName(String name) {
        List<Message> resultList = find.where().eq("name", name).orderBy("id asc").findList();

        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
}
