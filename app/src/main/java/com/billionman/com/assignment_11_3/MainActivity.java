package com.billionman.com.assignment_11_3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String SQL_QUERY = "SELECT * FROM EMP_TABLE";
    SQLiteDatabase sqlDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmployeeDB empDb = new EmployeeDB(this);
        sqlDb = empDb.getWritableDatabase();
        byte[] image = fetchImage();
        if(!isTableExists(sqlDb)) {
            insertEmployeeDetails("Mithun",26,image);
        }
        renderDatafromDB();
    }

    private void renderDatafromDB() {
        Cursor c = sqlDb.rawQuery(SQL_QUERY,null);
        c.moveToFirst();
        TextView tv = (TextView) findViewById(R.id.nameValue);
        System.out.println(c.getColumnCount());
        tv.setText(c.getString(0));
        TextView tv1 = (TextView) findViewById(R.id.ageValue);
        tv1.setText(String.valueOf(c.getInt(1)));
        ImageView iv = (ImageView) findViewById(R.id.emp_image);
        byte[] byteArray = c.getBlob(2);
        Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
        iv.setImageBitmap(bm);
        sqlDb.close();
    }

    private void insertEmployeeDetails(String name, int age, byte[] image) {
        String sql1 = "INSERT INTO EMP_TABLE (EMP_NAME,EMP_AGE,EMP_PHOTO) values(?,?,?)";
        SQLiteStatement sqLiteStatement = sqlDb.compileStatement(sql1);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1,name);
        sqLiteStatement.bindLong(2,age);
        sqLiteStatement.bindBlob(3,image);
        sqLiteStatement.executeInsert();
    }

    private byte[] fetchImage() {
        Bitmap bmap = BitmapFactory.decodeResource(getResources(),R.drawable.bm_corp);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private boolean isTableExists(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery(SQL_QUERY, null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                System.out.println("-----------------------------------------------Table exists");
                return true;
            }
            cursor.close();
        }
        return false;
    }
}
