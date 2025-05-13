package com.example.thongtinsinhvien2;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SelectStudentActivity extends AppCompatActivity {

    private ListView listViewStudents;
    private List<Student> studentList;
    private StudentListAdapter adapter;
    private DatabaseHelper dbHelper;
    private Bitmap receivedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_student);

        listViewStudents = findViewById(R.id.listViewStudents);
        dbHelper = new DatabaseHelper(this);
        studentList = dbHelper.getAllStudents();
        adapter = new StudentListAdapter(this, studentList);
        listViewStudents.setAdapter(adapter);

        // Xác định hành động từ Intent
        String action = getIntent().getStringExtra("action");
        receivedBitmap = getIntent().getParcelableExtra("photo");

        listViewStudents.setOnItemClickListener((parent, view, position, id) -> {
            Student selectedStudent = studentList.get(position);

            if ("delete".equals(action)) {
                // Xóa ảnh (nếu có)
                deleteImage(selectedStudent.getMssv());

                // Xóa sinh viên khỏi DB
                dbHelper.deleteStudent(selectedStudent.getMssv());

                Toast.makeText(this, "Đã xóa " + selectedStudent.getName(), Toast.LENGTH_SHORT).show();
            } else {
                // Gán ảnh
                saveImageToInternalStorage(receivedBitmap, selectedStudent.getMssv());
                Toast.makeText(this, "Đã gán ảnh cho " + selectedStudent.getName(), Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }

    private void saveImageToInternalStorage(Bitmap bitmap, String fileName) {
        try {
            FileOutputStream fos = openFileOutput(fileName + ".jpg", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteImage(String fileName) {
        String path = getFilesDir().getAbsolutePath() + "/" + fileName + ".jpg";
        java.io.File file = new java.io.File(path);
        if (file.exists()) {
            file.delete();
        }
    }
}
