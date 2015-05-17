package forms;

import play.data.validation.Constraints.Required;

public class DetailSearchForm {

    /** Name検索欄 */
    @Required
    public String name;

    /** 部分検索フラグ */
    public boolean isLike;
}
