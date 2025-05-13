package com.example.thongtinsinhvien2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private TextView tvThongTin;
    private Button btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Ánh xạ view
        tvThongTin = findViewById(R.id.tvThongTin);
        btnBack = findViewById(R.id.btnBack);
        tvThongTin.setMovementMethod(new ScrollingMovementMethod());

        dbHelper = new DatabaseHelper(this);
        // Nhận dữ liệu từ Intent
        Student student = (Student) getIntent().getSerializableExtra("student");
        if (student != null) {
            tvThongTin.setText(student.getFullInfo());
        } else {
            String mssv = getIntent().getStringExtra("mssv");
            student = dbHelper.getStudentByMSSV(mssv);
            if (student != null) {
                tvThongTin.setText(student.getFullInfo());
            } else {
                tvThongTin.setText("Không tìm thấy sinh viên.");
            }
        }

        btnBack.setOnClickListener(v -> {
            setResult(88);
            finish();
        });
    }
}