/*
* Colin Willson
* Mobile Applications II
* Assignment 2
* November 4, 2015
 */

package howlongtobeat.cwftw.me.howlongtobeat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HLTB.db";
    private static final int DATABASE_VERSION = 1;
//    private static final String CREATE_TABLE_PLAYER =
//            "CREATE TABLE player ( " +
//                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    "name TEXT NOT NULL UNIQUE" +
//                    ")";
//    private static final String CREATE_TABLE_GAME =
//            "CREATE TABLE game ( " +
//                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    "player1 INTEGER," +
//                    "player2 INTEGER," +
//                    "winner INTEGER," +
//                    "FOREIGN KEY (player1) REFERENCES player (id)," +
//                    "FOREIGN KEY (player2) REFERENCES player (id)," +
//                    "FOREIGN KEY (winner) REFERENCES player (id)" +
//                    ")";
    private static DatabaseHelper instance;

    /**
     * Constructor should be private to prevent direct instantiation.
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
//            db.execSQL(CREATE_TABLE_PLAYER);
//            db.execSQL(CREATE_TABLE_GAME);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "onCreate");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
//            db.execSQL("DROP TABLE IF EXISTS player");
//            db.execSQL("DROP TABLE IF EXISTS game");
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "onUpgrade");
        }
    }
//
//    // Can't surround this with try/catch, need method to throw for the UI
//    public long insertGame(Game game) {
//        long id = -1;
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put("player1", game.getPlayer1());
//        values.put("player2", game.getPlayer2());
//        values.put("winner", game.getWinner());
//
//        id = db.insertOrThrow("game", null, values);
//        db.close();
//        return id;
//    }
//
//    public ArrayList<Game> selectGames() {
//        ArrayList<Game> games = new ArrayList<Game>();
//        try {
//            SQLiteDatabase db = this.getReadableDatabase();
//            String selectQuery = "SELECT * FROM game";
//            Cursor c = db.rawQuery(selectQuery, null);
//
//            // looping through all rows and adding to list
//            if (c.moveToFirst()) {
//                do {
//                    Game g = new Game();
//                    g.setId(c.getInt(c.getColumnIndex("id")));
//                    g.setPlayer1(c.getInt(c.getColumnIndex("player1")));
//                    g.setPlayer2(c.getInt(c.getColumnIndex("player2")));
//                    g.setWinner(c.getInt(c.getColumnIndex("winner")));
//
//                    // adding to games list
//                    games.add(g);
//                } while (c.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.e("DatabaseHelper", "selectGames");
//        }
//        return games;
//    }
//
//    public boolean deleteGame(int id) {
//        boolean deleteSuccessful = false;
//        try {
//            SQLiteDatabase db = this.getWritableDatabase();
//            deleteSuccessful = db.delete("game", "id ='" + id + "'", null) > 0;
//            db.close();
//        } catch (Exception e) {
//            Log.e("DatabaseHelper", "deleteGame");
//        }
//        return deleteSuccessful;
//    }
//
//    // Can't surround this with try/catch, need method to throw for the UI
//    public long insertPlayer(Player player) {
//        long id = -1;
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put("name", player.getName());
//
//        id = db.insertOrThrow("player", null, values);
//        db.close();
//        return id;
//    }
//
//    public ArrayList<Player> selectPlayers() {
//        ArrayList<Player> players = new ArrayList<Player>();
//        try {
//            SQLiteDatabase db = this.getReadableDatabase();
//            String selectQuery = "SELECT * FROM player";
//            Cursor c = db.rawQuery(selectQuery, null);
//
//            // looping through all rows and adding to list
//            if (c.moveToFirst()) {
//                do {
//                    Player p = new Player();
//                    p.setId(c.getInt(c.getColumnIndex("id")));
//                    p.setName(c.getString(c.getColumnIndex("name")));
//
//                    // adding to player list
//                    players.add(p);
//                } while (c.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.e("DatabaseHelper", "selectPlayers");
//        }
//        return players;
//    }
//
//    public Player selectPlayer(int id) {
//        Player player = new Player();
//        // Use the passed id instead to handle deletes
//        player.setId(id);
//        try {
//            SQLiteDatabase db = this.getReadableDatabase();
//            String selectQuery = "SELECT * FROM player WHERE id = " + id;
//            Cursor c = db.rawQuery(selectQuery, null);
//
//            // looping through all rows and adding to list
//            if (c.moveToFirst()) {
////                player.setId(c.getInt(c.getColumnIndex("id")));
//                player.setName(c.getString(c.getColumnIndex("name")));
//            }
//        } catch (Exception e) {
//            Log.e("DatabaseHelper", "selectPlayer");
//        }
//        return player;
//    }
//
//    public boolean deletePlayer(int id) {
//        boolean deleteSuccessful = false;
//        try {
//            SQLiteDatabase db = this.getWritableDatabase();
//            deleteSuccessful = db.delete("player", "id ='" + id + "'", null) > 0;
//            db.close();
//        } catch (Exception e) {
//            Log.e("DatabaseHelper", "deletePlayer");
//        }
//        return deleteSuccessful;
//    }
}