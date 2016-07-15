package com.yolonerds.dstreasurechest;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created using http://blog.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.yolonerds.dstreasurechest/databases/";
    private static String DB_NAME = "treasuredata.sqlite3";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException{

        boolean dbExist = false;

        if(dbExist){
            //do nothing - database already exist
        }else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase().close();

            try {
                copyDataBase();
            } catch (IOException ex) {
                throw new Error(ex.toString());
            }
        }
    }

    /*private boolean checkDataBase(){

        File databasePath = this.myContext.getDatabasePath(DB_NAME);
        return databasePath.exists();
    }*/

    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[4096];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public Cursor getList() {

        String expansions = "(";
        for(String expac : GeneratorOptions.getInstance().getExpansions()){
            expansions += "'" + expac + "',";
        }

        expansions = expansions.substring(0, expansions.length() - 1);
        expansions += ")";

        String query;

        if (GeneratorOptions.getInstance().getLimitLevels()){
            query = "SELECT * FROM items WHERE itemset IN " + expansions + " AND itemlevel <= " + GeneratorOptions.getInstance().getLevels();
        } else {
            query = "SELECT * FROM items WHERE itemset IN " + expansions;
        }

        return myDataBase.rawQuery(query, null);
    }
}
