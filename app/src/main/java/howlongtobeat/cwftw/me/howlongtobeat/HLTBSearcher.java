package howlongtobeat.cwftw.me.howlongtobeat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

/**
 * Created by colin on 2015-12-02.
 */
public class HLTBSearcher {
    /*
     * Selector Constants
     */
    private static final String SELECTOR_GAME_CARD = ".back_white.shadow_box";
    // 'title' attribute of 'a' tag
    private static final String SELECTOR_TITLE = ".search_list_image a";
    // base image url + 'src' attribute of img
    private static final String SELECTOR_IMAGE_URL = ".search_list_image a img";
    // Each div has data in a specific order
    private static final String SELECTOR_DATA_MULTI = ".search_list_tidbit.center";

//    private static final String SELECTOR_MAIN_HOURS = ".search_list_details_block.search_list_tidbit.center time_100";
//    private static final String SELECTOR_MAIN_EXTRA_HOURS = ".search_list_details_block.search_list_tidbit center time_100";
//    private static final String SELECTOR_COMPLETIONIST_HOURS = ".search_list_details_block";
//    private static final String SELECTOR_COMBINED_HOURS = ".search_list_details_block";
//    private static final String SELECTOR_IMAGE_URL = ".search_list_details_block";

    // Detailed game info
//    private static final String SELECTOR_POLLED = ".search_list_details_block";
//    private static final String SELECTOR_RATED_PERCENT = ".search_list_details_block";
//    private static final String SELECTOR_BACKLOG_COUNT = ".search_list_details_block";
//    private static final String SELECTOR_PLAYING = ".search_list_details_block";
//    private static final String SELECTOR_SPEEDRUNS = ".search_list_details_block";
//    private static final String SELECTOR_RETIRED = ".search_list_details_block";

    // General Results
    private static final String SELECTOR_PAGES = "span.search_list_page.back_darkish shadow_box";
    private static final String SELECTOR_TOTAL_RESULTS = "h3.head_padding.shadow_box back_blue";
    /*
     * End Selector Constants
     */

    /*
     * Search URL
     */
    private static final String BASE_SEARCH_URL = "http://howlongtobeat.com/search_main.php";
    private static final String BASE_IMAGE_URL = "http://howlongtobeat.com/";


    private String query;
    private int page;
//    private URL url;


//    public String getQuery() {
//        return query;
//    }
//
//    public void setQuery(String query) {
//        this.query = query;
//    }
//
//    public int getPage() {
//        return page;
//    }
//
//    public void setPage(int page) {
//        this.page = page;
//    }

    public HLTBSearcher () {
        this.query = "";
        this.page = 1;
    }

    public HLTBSearcher (String query) {
        this.query = query;
        this.page = 1;
    }

    public HLTBSearcher (String query, int page) {
        this.query = query;
        this.page = page;
    }

//    public ResultSet search() throws IOException {
//        return search("", 1);
//    }
//
//    public ResultSet search(String query) throws IOException{
//        return  search(query, 1);
//    }

//    public ResultSet search (String query, int page) throws IOException {
//        // Post search form
//        Document doc = Jsoup.connect(BASE_SEARCH_URL).data("queryString", query).data("t", "games").data("page", Integer.toString(page)).data("sorthead", "popular").data("sortd", "Normal Order").data("plat", "").data("detail", "1").post();
//
//        // Get game divs
//        Elements gameElements = doc.select(".back_white shadow_box");
//
//        for (Element game : gameElements) {
////            String title = game.select(".")
//        }
//
//        return new ResultSet();
//    }

    public ResultSet search () throws IOException {
        ResultSet resultSet = new ResultSet();
        ArrayList<Game> games = new ArrayList<Game>();

        // Post search form
        Document doc = Jsoup.connect(BASE_SEARCH_URL).data("queryString", this.query).data("t", "games").data("page", Integer.toString(this.page)).data("sorthead", "popular").data("sortd", "Normal Order").data("plat", "").data("detail", "1").post();

        // Get general result values
//        Element pages =
//        resultSet.setPages();

        // Get game divs
        Elements gameElements = doc.select(SELECTOR_GAME_CARD);
        // For each game div, parse html and add to game object
        for (Element gameElement : gameElements) {
            Game game = new Game();

            game.setTitle(gameElement.select(SELECTOR_TITLE).first().attr("title"));
            game.setImageUrl(gameElement.select(SELECTOR_IMAGE_URL).first().attr("src"));

            // Get all data elements
            Elements gameData = gameElement.select(SELECTOR_DATA_MULTI);
            // Parse and convert all data fields
            game.setMainHours(parseString(gameData.get(0).text()));
            game.setMainExtraHours(parseString(gameData.get(1).text()));
            game.setCompletionistHours(parseString(gameData.get(2).text()));
            game.setCombinedHours(parseString(gameData.get(3).text()));
            game.setPolled(parseString(gameData.get(4).text()));
            game.setRatedPercent(parseString(gameData.get(5).text()));
            game.setBacklogCount(parseString(gameData.get(6).text()));
            game.setPlaying(parseString(gameData.get(7).text()));
            game.setRetired(parseString(gameData.get(8).text()));

            games.add(game);
        }

        resultSet.setPage(games);
        resultSet.setPages(1);
        resultSet.setTotalResults(100);
        return resultSet;
    }

    private double parseString(String input) {
        String charsRemoved = input.replaceAll("[^0-9]", "");
        double returnValue;

        try {
            returnValue = Double.parseDouble(charsRemoved);

            // If string contains 1/2, add 0.5 to return value
            if (input.contains("½")) {
                returnValue += 0.5;
            }
        } catch (NumberFormatException e) {
            returnValue = 0;
        }

        return returnValue;
    }
}
