package howlongtobeat.cwftw.me.howlongtobeat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * Created by colin on 2015-12-02.
 */
public class HLTBSearcher {
    private static final String BASE_SEARCH_URL = "http://howlongtobeat.com/search_main.php";
//    private URL url;

    public HLTBSearcher () {
//        url = new URL(BASE_SEARCH_URL);
//        Document doc = Jsoup.connect(BASE_SEARCH_URL).;
//        String title = doc.title();
    }

    public ResultSet search() throws IOException {
        return search("", 1);
    }

    public ResultSet search(String query) throws IOException{
        return  search(query, 1);
    }

    public ResultSet search (String query, int page) throws IOException {
        // Post search form
        Document doc = Jsoup.connect(BASE_SEARCH_URL).data("queryString", query).data("t", "games").data("page", Integer.toString(page)).data("sorthead", "popular").data("sortd", "Normal Order").data("plat", "").data("detail", "1").post();

        // Get game divs
        Elements gameElements = doc.select(".back_white shadow_box");

        for (Element game : gameElements) {
//            String title = game.select(".")
        }

        return null;
    }
}
