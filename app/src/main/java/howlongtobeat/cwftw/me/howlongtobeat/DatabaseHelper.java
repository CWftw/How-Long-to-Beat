package howlongtobeat.cwftw.me.howlongtobeat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.sql.Time;
import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "HLTB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_GAME =
                    "CREATE TABLE games ( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title VARCHAR" +
                    "mainHours INTEGER," +
                    "extraHours INTEGER," +
                    "completionistHours INTEGER" +
                    "combinedHours INTEGER" +
                    "image BLOB" +
                    ")";
    private static DatabaseHelper instance;

    /**
     * Constructor should be private to prevent direct instantiation.
     */
    private DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            db.execSQL(CREATE_TABLE_GAME);
        }
        catch (SQLException e)
        {
            Log.e("DatabaseHelper", "onCreate");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        try
        {
            db.execSQL("DROP TABLE IF EXISTS game");
        }
        catch (SQLException e)
        {
            Log.e("DatabaseHelper", "onUpgrade");
        }
    }

    // Can't surround this with try/catch, need method to throw for the UI
    public long insertGame(Game game)
    {
        long id = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", game.getTitle());
        values.put("mainHours", game.getMainHours().getTime());
        values.put("extraHours", game.getMainExtraHours().getTime());
        values.put("completionistHours", game.getCompletionistHours().getTime());
        values.put("combinedHours", game.getCombinedHours().getTime());
        values.put("image", game.getImageBytes());

        id = db.insertOrThrow("games", null, values);
        db.close();
        return id;
    }

    public ArrayList<Game> selectGames()
    {
        ArrayList<Game> games = new ArrayList<Game>();

        try
        {
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM games";
            Cursor c = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (c.moveToFirst())
            {
                do
                {
                    Game g = new Game
                    (
                            c.getString(c.getColumnIndex("title")),
                            new Time(c.getLong(c.getColumnIndex("mainHours"))),
                            new Time(c.getLong(c.getColumnIndex("extraHours"))),
                            new Time(c.getLong(c.getColumnIndex("completionistHours"))),
                            new Time(c.getLong(c.getColumnIndex("combinedHours"))),
                            c.getBlob(c.getColumnIndex("image"))
                    );

                    // adding to games list
                    games.add(g);
                } while (c.moveToNext());
            }
        }
        catch (Exception e)
        {
            Log.e("DatabaseHelper", "selectGames");
        }
        
        return games;
    }

    public boolean deleteGame(int id)
    {
        boolean deleteSuccessful = false;

        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            deleteSuccessful = db.delete("games", "id ='" + id + "'", null) > 0;
            db.close();
        }
        catch (Exception e)
        {
            Log.e("DatabaseHelper", "deleteGame");
        }

        return deleteSuccessful;
    }
}