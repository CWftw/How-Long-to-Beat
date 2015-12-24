/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.gamelength;

import java.util.ArrayList;

import howlongtobeat.cwftw.me.gamelength.models.Game;

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
