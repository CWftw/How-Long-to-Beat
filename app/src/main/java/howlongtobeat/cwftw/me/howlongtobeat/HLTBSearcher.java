package howlongtobeat.cwftw.me.howlongtobeat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by colin on 2015-12-02.
 */
public class HLTBSearcher {
    public HLTBSearcher () throws IOException {
        Document doc = Jsoup.connect("http://google.com/").get();
        String title = doc.title();
    }
}
