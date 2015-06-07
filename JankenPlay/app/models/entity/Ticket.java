package models.entity;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import models.service.impl.TicketService;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

@Entity
@Table(name = "tickets")
public class Ticket extends Model {

    /** ID */
    @Id
    public Long id;

    /** タイトル */
    @Required
    @MaxLength(64)
    @Column(nullable = false, length = 64)
    public String title;

    /** 開始日 */
    @Required
    public Date startDate;

    /** 終了日 */
    @Required
    public Date endDate;

    /** 内容 */
    @Required
    @Lob
    public String content;

    /** レコード作成日 */
    @CreatedTimestamp
    public Date createdDatetime;

    /** レコード更新日 */
    @UpdatedTimestamp
    public Date updatedDatetime;

    public static final Finder<Long, Ticket> find = new Finder<>(Long.class, Ticket.class);

    private TicketService service = new TicketService();

    /** デフォルトコンストラクタ、特に何も処理しません。 */
    public Ticket() { }

    /**
     * IDを指定して初期化します。このコンストラクタは通常、IDによる検索用に使用されます。
     *
     * @param id ID
     */
    public Ticket(Long id) {
        this.id = id;
    }

    /**
     * 独自バリデーションチェック
     *
     * @return エラーメッセージ
     */
    public String validate() {
        if (startDate.after(endDate)) {
            return "開始日は終了日より過去の日付を選択してください";
        } else {
            return null;
        }
    }

    /**
     * 現在のインスタンスをDBに挿入します。
     *
     * @return 挿入結果
     */
    public boolean store() {
        return service.store(this);
    }

    /**
     * 現在設定されているIDに紐づくレコードをDBから取得します
     *
     * @return IDに紐づくレコード
     */
    public Optional<Ticket> unique() {
        return service.findById(id);
    }
}
