/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.howlongtobeat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

public class HLTBSearcher {
    /*
     * Selector Constants
     */
    private static final String SELECTOR_GAME_CARD = ".back_white.shadow_box";
    // href attribute of 'a' tag, example format 'game.php?id=3340'
    private static final String SELECTOR_ID = ".search_list_details h3 a";
    // 'title' attribute of 'a' tag
    private static final String SELECTOR_TITLE = ".search_list_image a";
    // base image url + 'src' attribute of img
    private static final String SELECTOR_IMAGE_URL = ".search_list_image a img";
    // Each div has data in a specific order
    private static final String SELECTOR_DATA_MULTI = ".search_list_tidbit";

    private static final String HEADER_MAIN_STORY = "Main Story";
    private static final String HEADER_MAIN_EXTRA = "Main + Extra";
    private static final String HEADER_COMPLETIONIST = "Completionist";
    private static final String HEADER_COMBINED = "Combined";
    private static final String HEADER_POLLED = "Polled";
    private static final String HEADER_RATED = "Rated";
    private static final String HEADER_BACKLOG = "Backlog";
    private static final String HEADER_PLAYING = "Playing";
    private static final String HEADER_RETIRED = "Retired";

    // General Results
    private static final String SELECTOR_PAGES = "span.search_list_page.shadow_box";
    // Get last page element text
    private static final String SELECTOR_TOTAL_RESULTS = ".head_padding.shadow_box.back_blue";
    /*
     * End Selector Constants
     */

    /*
     * Search URL
     */
    private static final String BASE_SEARCH_URL = "http://howlongtobeat.com/search_main.php";
    private static final String BASE_IMAGE_URL = "http://howlongtobeat.com/";

    private ResultSet resultSet;

    private String query;
    private int page;

    public HLTBSearcher() {
        this.query = "";
        this.page = 0;
        resultSet = new ResultSet();
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.page = 0;
        this.query = query;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Game getGame(String name, int matchId) throws IOException {
        Document doc = Jsoup.connect(BASE_SEARCH_URL).data("queryString", name).data("t", "games").data("sorthead", "name").data("sortd", "Normal Order").data("plat", "").data("detail", "1").timeout(10 * 1000).post();

        // Get game divs
        Elements gameElements = doc.select(SELECTOR_GAME_CARD);

        for (Element gameElement : gameElements) {
            int id = (int) parseString(gameElement.select(SELECTOR_ID).first().attr("href"));

            if (id == matchId) {
                Game game = new Game();
                game.setId((int) parseString(gameElement.select(SELECTOR_ID).first().attr("href")));
                game.setTitle(gameElement.select(SELECTOR_TITLE).first().attr("title"));
                game.setImageUrl(BASE_IMAGE_URL + gameElement.select(SELECTOR_IMAGE_URL).first().attr("src"));

                // Get all data elements
                Elements gameData = gameElement.select(SELECTOR_DATA_MULTI);

                // Parse and convert all data fields
                for (int i = 0; i < gameData.size(); i += 2) {
                    switch (gameData.get(i).text().trim()) {
                        case HEADER_MAIN_STORY:
                            game.setMainHours(parseString(gameData.get(i + 1).text()));
                            break;
                        case HEADER_MAIN_EXTRA:
                            game.setMainExtraHours(parseString(gameData.get(i + 1).text()));
                            break;
                        case HEADER_COMPLETIONIST:
                            game.setCompletionistHours(parseString(gameData.get(i + 1).text()));
                            break;
                        case HEADER_COMBINED:
                            game.setCombinedHours(parseString(gameData.get(i + 1).text()));
                            break;
                        case HEADER_POLLED:
                            game.setPolled(parseString(gameData.get(i + 1).text()));
                            break;
                        case HEADER_RATED:
                            game.setRatedPercent(parseString(gameData.get(i + 1).text()));
                            break;
                        case HEADER_BACKLOG:
                            game.setBacklogCount(parseString(gameData.get(i + 1).text()));
                            break;
                        case HEADER_PLAYING:
                            game.setPlaying(parseString(gameData.get(i + 1).text()));
                            break;
                        case HEADER_RETIRED:
                            game.setRetired(parseString(gameData.get(i + 1).text()));
                            break;
                        default:
                            // Unknown field
                            break;
                    }
                }
                return game;
            }
        }
        // Game not found
        return null;
    }

    public ResultSet search() throws IOException {
        ArrayList<Game> games = new ArrayList<>();

        // Post search form
        Document doc = Jsoup.connect(BASE_SEARCH_URL + "?page=" + Integer.toString(this.page)).data("queryString", this.query).data("t", "games").data("sorthead", "popular").data("sortd", "Normal Order").data("plat", "").data("detail", "1").timeout(10 * 1000).post();

        Element pages = doc.select(SELECTOR_PAGES).last();
        Element results = doc.select(SELECTOR_TOTAL_RESULTS).first();

        if (pages != null && results != null) {
            resultSet.setPages((int) parseString(doc.select(SELECTOR_PAGES).last().text()));
            resultSet.setTotalResults((int) parseString(doc.select(SELECTOR_TOTAL_RESULTS).first().text()));
        }

        // Get game divs
        Elements gameElements = doc.select(SELECTOR_GAME_CARD);
        // For each game div, parse html and add to game object
        for (Element gameElement : gameElements) {
            Game game = new Game();

            if (gameElement.select(SELECTOR_ID).first() == null) {
                break;
            }

            game.setId((int) parseString(gameElement.select(SELECTOR_ID).first().attr("href")));
            game.setTitle(gameElement.select(SELECTOR_TITLE).first().attr("title"));
            game.setImageUrl(BASE_IMAGE_URL + gameElement.select(SELECTOR_IMAGE_URL).first().attr("src"));

            // Get all data elements
            Elements gameData = gameElement.select(SELECTOR_DATA_MULTI);

            // Parse and convert all data fields
            for (int i = 0; i < gameData.size(); i += 2) {
                switch (gameData.get(i).text().trim()) {
                    case HEADER_MAIN_STORY:
                        game.setMainHours(parseString(gameData.get(i + 1).text()));
                        break;
                    case HEADER_MAIN_EXTRA:
                        game.setMainExtraHours(parseString(gameData.get(i + 1).text()));
                        break;
                    case HEADER_COMPLETIONIST:
                        game.setCompletionistHours(parseString(gameData.get(i + 1).text()));
                        break;
                    case HEADER_COMBINED:
                        game.setCombinedHours(parseString(gameData.get(i + 1).text()));
                        break;
                    case HEADER_POLLED:
                        game.setPolled(parseString(gameData.get(i + 1).text()));
                        break;
                    case HEADER_RATED:
                        game.setRatedPercent(parseString(gameData.get(i + 1).text()));
                        break;
                    case HEADER_BACKLOG:
                        game.setBacklogCount(parseString(gameData.get(i + 1).text()));
                        break;
                    case HEADER_PLAYING:
                        game.setPlaying(parseString(gameData.get(i + 1).text()));
                        break;
                    case HEADER_RETIRED:
                        game.setRetired(parseString(gameData.get(i + 1).text()));
                        break;
                    default:
                        // Unknown field
                        break;
                }
            }
            games.add(game);
        }
        resultSet.setPage(games);
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
            returnValue = -1;
        }

        return returnValue;
    }
}
