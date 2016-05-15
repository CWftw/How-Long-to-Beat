/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
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
    private static final int DATABASE_VERSION = 2;
    private static final String CREATE_TABLE_GAME =
            "CREATE TABLE games ( " +
                    "id INTEGER," +
                    "title VARCHAR," +
                    "mainHours REAL," +
                    "extraHours REAL," +
                    "completionistHours REAL," +
                    "combinedHours REAL," +
                    "polled REAL," +
                    "rated REAL," +
                    "backlog REAL," +
                    "playing REAL," +
                    "retired REAL," +
                    "image BLOB" +
                    ")";
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
            db.execSQL(CREATE_TABLE_GAME);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "onCreate");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS games");
            db.execSQL(CREATE_TABLE_GAME);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "onUpgrade");
        }
    }

    // Can't surround this with try/catch, need method to throw for the UI
    public long insertGame(Game game) {
        long id = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", game.getId());
        values.put("title", game.getTitle());
        values.put("mainHours", game.getMainHours());
        values.put("extraHours", game.getMainExtraHours());
        values.put("completionistHours", game.getCompletionistHours());
        values.put("combinedHours", game.getCombinedHours());
        values.put("polled", game.getPolled());
        values.put("rated", game.getRatedPercent());
        values.put("backlog", game.getBacklogCount());
        values.put("playing", game.getPlaying());
        values.put("retired", game.getRetired());
        values.put("image", game.getImageBytes());

        id = db.insertOrThrow("games", null, values);
        db.close();
        return id;
    }

    public ArrayList<Game> selectGames(String query) {
        ArrayList<Game> games = new ArrayList<Game>();
        Cursor c = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery;

            if (query == "") {
                // Select all
                selectQuery = "SELECT * FROM games";
                c = db.rawQuery(selectQuery, null);
            } else {
                selectQuery = "SELECT * FROM games WHERE title LIKE '%?%'";
                c = db.rawQuery(selectQuery, new String[]{query});
            }

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Game game = new Game();
                    game.setId(c.getInt(c.getColumnIndex("id")));
                    game.setTitle(c.getString(c.getColumnIndex("title")));
                    game.setMainHours(c.getDouble(c.getColumnIndex("mainHours")));
                    game.setMainExtraHours(c.getDouble(c.getColumnIndex("extraHours")));
                    game.setCompletionistHours(c.getDouble(c.getColumnIndex("completionistHours")));
                    game.setCombinedHours(c.getDouble(c.getColumnIndex("combinedHours")));
                    game.setPolled(c.getDouble(c.getColumnIndex("polled")));
                    game.setRatedPercent(c.getDouble(c.getColumnIndex("rated")));
                    game.setBacklogCount(c.getDouble(c.getColumnIndex("backlog")));
                    game.setPlaying(c.getDouble(c.getColumnIndex("playing")));
                    game.setRetired(c.getDouble(c.getColumnIndex("retired")));
                    game.setImageBytes(c.getBlob(c.getColumnIndex("image")));

                    // adding to games list
                    games.add(game);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "selectGames");
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return games;
    }

    public Game selectGame(int id) {
        Game game = null;
        Cursor c = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM games WHERE id = " + id;
            c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                game = new Game();

                game.setId(c.getInt(c.getColumnIndex("id")));
                game.setTitle(c.getString(c.getColumnIndex("title")));
                game.setMainHours(c.getDouble(c.getColumnIndex("mainHours")));
                game.setMainExtraHours(c.getDouble(c.getColumnIndex("extraHours")));
                game.setCompletionistHours(c.getDouble(c.getColumnIndex("completionistHours")));
                game.setCombinedHours(c.getDouble(c.getColumnIndex("combinedHours")));
                game.setPolled(c.getDouble(c.getColumnIndex("polled")));
                game.setRatedPercent(c.getDouble(c.getColumnIndex("rated")));
                game.setBacklogCount(c.getDouble(c.getColumnIndex("backlog")));
                game.setPlaying(c.getDouble(c.getColumnIndex("playing")));
                game.setRetired(c.getDouble(c.getColumnIndex("retired")));
                game.setImageBytes(c.getBlob(c.getColumnIndex("image")));
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "selectGame");
            return null;
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return game;
    }

    public int updateGame(int id, Game game) {
        int rowCount = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            String[] whereArgs = {Integer.toString(game.getId())};

            values.put("title", game.getTitle());
            values.put("mainHours", game.getMainHours());
            values.put("extraHours", game.getMainExtraHours());
            values.put("completionistHours", game.getCompletionistHours());
            values.put("combinedHours", game.getCombinedHours());
            values.put("polled", game.getPolled());
            values.put("rated", game.getRatedPercent());
            values.put("backlog", game.getBacklogCount());
            values.put("playing", game.getPlaying());
            values.put("retired", game.getRetired());

            db.update("games", values, "id = ?", whereArgs);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "selectGame");
        }

        return rowCount;
    }

    public boolean deleteGame(int id) {
        boolean deleteSuccessful = false;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            deleteSuccessful = db.delete("games", "id ='" + id + "'", null) > 0;
            db.close();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "deleteGame");
        }

        return deleteSuccessful;
    }
}