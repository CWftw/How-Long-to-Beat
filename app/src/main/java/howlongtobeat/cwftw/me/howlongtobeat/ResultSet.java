package howlongtobeat.cwftw.me.howlongtobeat;

import java.io.IOException;
import java.util.ArrayList;

import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

/**
 * Created by colin on 2015-12-02.
 */
public class ResultSet {
    private int pages;
    private int totalResults;
    private ArrayList<Game> page;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<Game> getPage() {
        return page;
    }

    public void setPage(ArrayList<Game> page) {
        this.page = page;
    }
}
