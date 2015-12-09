package howlongtobeat.cwftw.me.howlongtobeat;

import java.io.IOException;

/**
 * Created by colin on 2015-12-02.
 */
public class ResultSet {
    private int pages;
    private List<Game> page;

    public ResultSet (int pages, List<Game> page) {
        this.pages = pages;
        this.page = page;
    }
}
