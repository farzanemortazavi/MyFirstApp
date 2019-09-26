package com.example.myfirstapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myfirstapp.imdb.ImdbClass;
import com.example.myfirstapp.imdb.Search;

import java.util.ArrayList;
import java.util.List;

import static com.loopj.android.http.AsyncHttpClient.log;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateQuery="CREATE TABLE Movies ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "imdbID TEXT,"+
                "title TEXT,"+
                "year TEXT,"+
                "type TEXT,"+
                "poster TEXT)";
        db.execSQL(CreateQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE Movies ADD COLUMN test INTEGER DEFAULT 0");
        }

    }

    public boolean insertMovie(Search movie)
    {
        boolean result=true;
        String insertQuery="INSERT INTO Movies(imdbID,title,year,type,poster) VALUES ("+
                "'"+movie.getImdbID()+"',"+
                "'"+ movie.getTitle()+"',"+
                "'"+movie.getYear()+"',"+
                "'"+movie.getType()+"',"+
                "'"+movie.getPoster()+"')";
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(insertQuery);
            db.close();

        }catch (Exception e)
        {
            e.printStackTrace();
            result=false;
        }
        return  result;
    }

    public List<Search> GetMovieList(){
        List<Search> lst=new ArrayList<Search>();
        Search film=new Search();
        SQLiteDatabase db= this.getReadableDatabase();
        String selectQuery="SELECT * FROM Movies";
        Cursor cursor=db.rawQuery(selectQuery,null);
        while (cursor.moveToNext()){
            film=new Search();
            film.setImdbID(cursor.getString(1));
            film.setTitle(cursor.getString(2));
            film.setYear(cursor.getString(3));
            film.setType(cursor.getString(4));
            film.setPoster(cursor.getString(5));
            lst.add(film);
        }
        db.close();


        return lst;
    }

    public boolean isExistMovie(String ImdbId)
    {
        boolean result=false;
        SQLiteDatabase db= this.getReadableDatabase();
        String selectQuery="SELECT * FROM Movies WHERE imdbID='"+ImdbId+"'";
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.getCount()>0){
            result=true;
        }
        db.close();

        return result;
    }

    public boolean ClearImdbDatabase()
    {
        boolean result=true;
        String query="DELETE FROM Movies";
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(query);
            db.close();

        }catch (Exception e)
        {
            e.printStackTrace();
            result=false;
        }

        return  result;
    }
}
