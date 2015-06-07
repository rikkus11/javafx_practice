package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * メッセージ投稿システム メッセージ等後者テーブルのエンティティクラスです。
 *
 * @author リックス
 *
 */
@Entity
public class Member extends Model {

    /** レコードID */
    @Id
    public Long id;

    /** 名前 */
    @Required(message = "必須項目です")
    public String name;

    @Email(message = "メールアドレスを記入してください")
    public String mail;

    /** 電話番号 */
    public String tel;

    /**
     * メッセージレコード。MESSAGEテーブルと多 - 1で紐付けるためOneToManyを付与。もしMemberのUpdate時にMessageが代わってればそれも全てUpdateする
     * Member側はOneToMany、つまり1人のユーザが投稿した全部のメッセージを管理する。こっちはMemberなので、全部のメッセージを持つ
     */
    @OneToMany(cascade = CascadeType.ALL)
    public List<Message> message;

    /** 検索用Finder */
    public static Finder<Long, Member> find = new Finder<>(Long.class, Member.class);

    /* (非 Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Member [id=" + id + ", name=" + name + ", mail=" + mail + ", tel=" + tel + "]";
    }

    /**
     * NAMEを指定して、レコードを取得します。複数レコード存在する場合先頭1件を取得します。
     *
     * @param name 検索する名前
     * @return 検索結果、存在しない場合はnull
     */
    public static Member findByName(String name) {
        List<Member> resultList = find.where().eq("name", name).orderBy("id asc").findList();

        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
}
