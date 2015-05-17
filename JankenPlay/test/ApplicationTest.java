import static org.fest.assertions.Assertions.*;
import static play.test.Helpers.*;

import org.junit.Test;

import play.twirl.api.Content;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
* Applicationコントローラの処理をテストします。
*
*/
public class ApplicationTest {

    @Test
    public void Indexメソッドが3つの引数を画面表示する() {
        Content html = views.html.index.render("１", "２", "３");

        // Content-Typeがhtmlであること
        assertThat(contentType(html)).isEqualTo("text/html");

        // <li>で１、２、３が表示されていること
        assertThat(contentAsString(html)).contains("<li>１</li>");
        assertThat(contentAsString(html)).contains("<li>２</li>");
        assertThat(contentAsString(html)).contains("<li>３</li>");
    }


}
