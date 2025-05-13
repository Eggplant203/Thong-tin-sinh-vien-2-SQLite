package com.example.thongtinsinhvien2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_STUDENTS = "students";

    // Các cột trong bảng sinh viên
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MSSV = "mssv";
    private static final String KEY_CLASS = "class";
    private static final String KEY_YEAR = "year";
    private static final String KEY_MAJORS = "majors";
    private static final String KEY_PLAN = "plan_text";

    // Tạo bảng sinh viên
    private static final String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT, "
            + KEY_MSSV + " TEXT, "
            + KEY_CLASS + " TEXT, "
            + KEY_YEAR + " TEXT, "
            + KEY_MAJORS + " TEXT, "
            + KEY_PLAN + " TEXT"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public long addStudent(Student student) {
        if (getStudentByMSSV(student.getMssv()) != null) {
            return -1; // MSSV đã tồn tại
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_MSSV, student.getMssv());
        values.put(KEY_CLASS, student.getClassName());
        values.put(KEY_YEAR, student.getYear());
        values.put(KEY_MAJORS, student.getMajors());
        values.put(KEY_PLAN, student.getPlan());

        long id = db.insert(TABLE_STUDENTS, null, values);
        db.close();
        return id;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                int idIndex = cursor.getColumnIndexOrThrow(KEY_ID);
                int nameIndex = cursor.getColumnIndexOrThrow(KEY_NAME);
                int mssvIndex = cursor.getColumnIndexOrThrow(KEY_MSSV);
                int classIndex = cursor.getColumnIndexOrThrow(KEY_CLASS);
                int yearIndex = cursor.getColumnIndexOrThrow(KEY_YEAR);
                int majorsIndex = cursor.getColumnIndexOrThrow(KEY_MAJORS);
                int planIndex = cursor.getColumnIndexOrThrow(KEY_PLAN);

                student.setId(cursor.getInt(idIndex));
                student.setName(cursor.getString(nameIndex));
                student.setMssv(cursor.getString(mssvIndex));
                student.setClassName(cursor.getString(classIndex));
                student.setYear(cursor.getString(yearIndex));
                student.setMajors(cursor.getString(majorsIndex));
                student.setPlan(cursor.getString(planIndex));

                students.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return students;
    }

    public Student getStudentByMSSV(String mssv) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENTS, null, KEY_MSSV + "=?",
                new String[]{mssv}, null, null, null);

        Student student = null;
        if (cursor != null && cursor.moveToFirst()) {
            student = new Student();
            student.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
            student.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
            student.setMssv(cursor.getString(cursor.getColumnIndexOrThrow(KEY_MSSV)));
            student.setClassName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CLASS)));
            student.setYear(cursor.getString(cursor.getColumnIndexOrThrow(KEY_YEAR)));
            student.setMajors(cursor.getString(cursor.getColumnIndexOrThrow(KEY_MAJORS)));
            student.setPlan(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PLAN)));
            cursor.close();
        }
        db.close();
        return student;
    }

    public void deleteStudent(String mssv) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, KEY_MSSV + " = ?", new String[]{mssv});
        db.close();
    }
}