package howlongtobeat.cwftw.me.howlongtobeat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by colin on 2015-12-02.
 */
public class HLTBSearcher {
    private static final Url BASE_SEARCH_URL = "http://howlongtobeat.com/search_main.php";

    public HLTBSearcher () throws IOException {
        Document doc = Jsoup.connect("http://google.com/").get();
        String title = doc.title();
    }
}
