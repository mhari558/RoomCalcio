package calcio.com.room_calcio.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import calcio.com.room_calcio.models.RecordsModel;

/**
 * Created by hari on 8/6/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "GUYS_143";

    private SQLiteDatabase sqLiteDatabase;

    public static final String TABLE_NAME = "Room_Expenses";
    public static final String COLUMN_NAME_ENTRY_ID = "ID";
    public static final String COLUMN_NAME_DATE = "Date";
    public static final String COLUMN_NAME_TOTAL = "TotalAmount";
    public static final String COLUMN_NAME_HARI = "Hari";
    public static final String COLUMN_NAME_MEERA = "Meera";
    public static final String COLUMN_NAME_BABA = "Baba";
    public static final String COLUMN_NAME_SURESH = "Suresh";
    public static final String COLUMN_NAME_SIVA = "Siva";
    public static final String COLUMN_NAME_CHAKRI = "Chakri";

    private static final String CREATE_TABLE_ROOM = "CREATE TABLE "
            + TABLE_NAME + "(" + COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME_DATE
            + " TEXT," + COLUMN_NAME_TOTAL + " INTEGER," + COLUMN_NAME_HARI
            + " INTEGER," +COLUMN_NAME_CHAKRI+" INTEGER," +COLUMN_NAME_BABA+" INTEGER," +COLUMN_NAME_SIVA+" INTEGER," +COLUMN_NAME_MEERA+" INTEGER," +COLUMN_NAME_SURESH+" INTEGER)";


    public DbHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.e("Insdie","===========");

        db.execSQL(CREATE_TABLE_ROOM);

    }
   public Cursor getAllRecords(){

       String countQuery = "SELECT  * FROM " + TABLE_NAME;
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery(countQuery, null);

       return cursor;

    }
    public ArrayList<RecordsModel> getAllRecords(String name){

       ArrayList<RecordsModel> getAllrecords = new ArrayList<>();
        String countQuery = "SELECT  "+COLUMN_NAME_DATE+","+name+" FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.moveToFirst()){
            do {
                RecordsModel model = new RecordsModel();
                if(cursor.getString(1)!=null) {
                    model.setDate(cursor.getString(0));
                    model.setShareAmount(cursor.getString(1));
                    getAllrecords.add(model);
                }


            }while (cursor.moveToNext());
        }
        return getAllrecords;

    }
    public ArrayList<String> getAllColumns(String type){

        ArrayList<String> colArray =  new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (c != null) {
                int num = c.getColumnCount();
                for (int i = 0; i < num; ++i) {
                    String colname = c.getColumnName(i);

                    System.out.println("Column"+colname);
                    if(type.equalsIgnoreCase("fragment")){

                        colArray.add(colname);

                    }else{
                        if(i>2) {
                            colArray.add(colname);
                        }
                    }


                }
            }

            return colArray;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return colArray;
    }


    public boolean insertData(ArrayList<String> mSelectedItems,int total, int shareAmount,String date){

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_DATE,date);
        contentValues.put(COLUMN_NAME_TOTAL,total);

        for (int i=0;i<mSelectedItems.size();i++){

            contentValues.put(mSelectedItems.get(i),shareAmount);
        }
        db.insert(TABLE_NAME,null,contentValues);

        return true;

    }
    public void getRecordsByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();

       String[] str = new String[] {name};

        Cursor c = db.query(
                TABLE_NAME,
                str,
                null,
                null,
                null,
                null,
                null
        );

        c.moveToFirst();

    }
    public  int getIndividualColumnCount(String columName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT SUM("+columName+") FROM "+TABLE_NAME+"", null);
        if(cur.moveToFirst())
        {
            return cur.getInt(0);
        }
        return 0;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

       // sqLiteDatabase.create();
    }
}
