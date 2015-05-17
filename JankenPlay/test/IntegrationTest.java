import static org.fest.assertions.Assertions.*;
import static play.test.Helpers.*;

import org.junit.Test;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     * Indexページ初期表示のテストを実行します。
     */
    @Test
    public void indexTest() {

        // 仮想サーバ(ポート3333)で仮想アプリをインメモリデータベースを使用して起動します
        // WebDriverはHtmlJUnit、HTMLのテスト用？FireFoxもあるみたい。
        // 3番目引数は実際のテスト内容、Callbackインタフェースは関数型になっているのでラムダで置き換えられる
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
                browser.goTo("http://localhost:3333");

                // Application#index()が渡している1,2,3が表示されていること
                assertThat(browser.pageSource()).contains("1");
                assertThat(browser.pageSource()).contains("2");
                assertThat(browser.pageSource()).contains("3");
            }
        );
    }

}
