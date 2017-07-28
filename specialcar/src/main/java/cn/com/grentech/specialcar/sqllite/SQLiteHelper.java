package cn.com.grentech.specialcar.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.GpsInfo;

import static android.R.attr.author;
import static android.R.attr.id;
import static android.R.id.list;

/**
 * Created by Administrator on 2017/7/26.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public final static String databaseName = "loadlineDatabase";

    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "loadline";

    public SQLiteHelper(Context context) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        sql = "CREATE TABLE " + TABLE_NAME
                + "(_id INTEGER PRIMARY KEY,"
                + " orderId INTEGER  NOT NULL,"
                + " lat REAL NOT NULL,"
                + " lng REAL NOT NULL,"
                + " speed REAL NOT NULL,"
                + " angle REAL NOT NULL,"
                + " accuracy REAL NOT NULL,"
                + " predistance REAL NOT NULL,"
                + " distance REAL NOT NULL,"
                + " createTime INTEGER NOT NULL )";
        db.execSQL(sql);
        db.execSQL("create index idx_orderId on loadline(orderId) ");
        db.execSQL("create index idx_createTime on loadline(createTime) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }


    //插入一条记录
    private long insert(int orderId, double lat, double lng, float speed, double angle, float accuracy, double predistance, double distance, long createTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("orderId", String.valueOf(orderId));
        cv.put("lat", String.valueOf(lat));
        cv.put("lng", String.valueOf(lng));

        cv.put("speed", String.valueOf(speed));
        cv.put("angle", String.valueOf(angle));
        cv.put("accuracy", String.valueOf(accuracy));
        cv.put("predistance", String.valueOf(predistance));
        cv.put("distance", String.valueOf(distance));
        cv.put("createTime", String.valueOf(createTime));
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

    public  long insert(int orderId,GpsInfo gi)
    {
        return insert(orderId,gi.getLat(),gi.getLng(),gi.getSpeed(),gi.getAngle(),gi.getAccuracy(),gi.getPredistance(),gi.getDistance(),gi.getCreateTime());
    }

    //根据条件查询
    private Cursor queryByOrderid(int orderId) {
        String[] args = new String[1];
        args[0] = String.valueOf(orderId);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE orderId = ? order by createTime", args);
        return cursor;
    }

    //根据条件查询
    private Cursor queryMaxCreateTimeByOrderid(int orderId) {
        String[] args = new String[2];
        args[0] = String.valueOf(orderId);
        args[1] = String.valueOf(orderId);
        SQLiteDatabase db = this.getReadableDatabase();
        ///  "SELECT * FROM "+TABLE_NAME+" WHERE orderId =  "+String.valueOf(orderId)+" and createTime = "+"SELECT max(createTime) FROM "+TABLE_NAME+" WHERE orderId =  "+String.valueOf(orderId)+" "
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE orderId = ? and createTime = ( SELECT max(createTime) FROM loadline  WHERE orderId = ? )", args);
        return cursor;
    }

    //删除记录
    public void delete(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "orderId = ?";
        String[] whereValue = {Integer.toString(orderId)};
        db.delete(TABLE_NAME, where, whereValue);
    }

    //删除记录
    public void deleteTenDaysAgo() {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "createTime < ?";
        long tendaysBefore=System.currentTimeMillis()-10*24*60*60*1000;
        String[] whereValue = {Long.toString(tendaysBefore)};
        db.delete(TABLE_NAME, where, whereValue);
    }

//    //更新记录
//    public void update(int id, String bookName,String author,String publisher) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String where = "_id = ?";
//        String[] whereValue = { Integer.toString(id) };
//        ContentValues cv = new ContentValues();
//        cv.put("BookName", bookName);
//        cv.put("Author", author);
//        cv.put("Publisher", publisher);
//        db.update(TABLE_NAME, cv, where, whereValue);
//    }


    //获取游标
    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    public GpsInfo getMaxCreateTimeGPSInfo(int orderid) {
        GpsInfo gpsInfo = null;
        Cursor cursor = this.queryMaxCreateTimeByOrderid(orderid);
        while (cursor.moveToNext()) {
            gpsInfo = this.getGpsInfo(cursor);
            break;
        }
        return gpsInfo;
    }


    public int getCount()
    {
        String[] args = new String[1];
        args[0] = String.valueOf(0);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE orderId >= ? order by createTime", args);

        return cursor.getCount();
    }

    public List<GpsInfo> getGpsInfoList(int orderId)
    {
        Cursor cursor= this.queryByOrderid(orderId);
        List<GpsInfo> list=new ArrayList<>();
        while (cursor.moveToNext())
        {
            GpsInfo gpsInfo=this.getGpsInfo(cursor);
            list.add(gpsInfo);
        }
        return list;
    }

    private GpsInfo getGpsInfo(Cursor cursor) {

        double lat = cursor.getDouble(cursor.getColumnIndex("lat"));
        double lng = cursor.getDouble(cursor.getColumnIndex("lng"));
        double angle = cursor.getDouble(cursor.getColumnIndex("angle"));
        float speed = cursor.getFloat(cursor.getColumnIndex("speed"));
        float accuracy = cursor.getFloat(cursor.getColumnIndex("accuracy"));
        long createTime = cursor.getLong(cursor.getColumnIndex("createTime"));
        ;
        double distance = cursor.getDouble(cursor.getColumnIndex("distance"));
        double predistance = cursor.getDouble(cursor.getColumnIndex("predistance"));
        GpsInfo gpsInfo = new GpsInfo(lat, lng);
        gpsInfo.setAngle(angle);
        gpsInfo.setAccuracy(accuracy);
        gpsInfo.setSpeed(speed);
        gpsInfo.setCreateTime(createTime);
        gpsInfo.setDistance(distance);
        gpsInfo.setPredistance(predistance);
        return gpsInfo;
    }
}
