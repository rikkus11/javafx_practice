package settings;

import play.GlobalSettings;
import play.api.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;

public class Global extends GlobalSettings {

    /**
     * CSRFフィルター
     *
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends EssentialFilter> Class<T>[] filters() {
        // TODO 自動生成されたメソッド・スタブ
        return new Class[] {
                CSRFFilter.class
        };
    }

}
