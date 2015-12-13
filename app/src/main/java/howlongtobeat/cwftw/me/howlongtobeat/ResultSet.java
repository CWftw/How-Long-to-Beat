package howlongtobeat.cwftw.me.howlongtobeat;

import java.io.IOException;
import java.util.ArrayList;

import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

/**
 * Created by colin on 2015-12-02.
 */
public class ResultSet {
    private int pages;
    private ArrayList<Game> page;

    public ResultSet (int pages, ArrayList<Game> page) {
        this.pages = pages;
        this.page = page;
    }
}
