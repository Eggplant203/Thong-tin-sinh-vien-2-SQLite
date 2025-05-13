package com.example.thongtinsinhvien2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class StudentListActivity extends AppCompatActivity {
    private ListView listViewStudents;
    private Button btnBackToSecond, btnDeleteStudent;
    private DatabaseHelper dbHelper;
    private List<Student> studentList;
    private StudentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Ánh xạ
        listViewStudents = findViewById(R.id.listViewStudents);
        btnBackToSecond = findViewById(R.id.btnBackToSecond);
        btnDeleteStudent = findViewById(R.id.btnDeleteStudent);

        // Tải danh sách sinh viên
        loadStudents();

        listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student selectedStudent = studentList.get(position);

                // Mở SecondActivity và truyền thông tin sinh viên
                Intent intent = new Intent(StudentListActivity.this, SecondActivity.class);
                intent.putExtra("student", selectedStudent);
                startActivity(intent);
            }
        });

        btnBackToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(77, resultIntent);
                finish();
            }
        });

        btnDeleteStudent.setOnClickListener(v -> {
            Intent intent = new Intent(StudentListActivity.this, SelectStudentActivity.class);
            intent.putExtra("action", "delete");
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload students data when returning to this activity
        loadStudents();
    }

    private void loadStudents() {
        studentList = dbHelper.getAllStudents();

        adapter = new StudentListAdapter(this, studentList);
        listViewStudents.setAdapter(adapter);
    }
}