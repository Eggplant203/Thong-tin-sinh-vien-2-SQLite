package com.example.thongtinsinhvien2;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.BitmapFactory;
import java.io.File;

import java.util.List;

public class StudentListAdapter extends BaseAdapter {
    private Context context;
    private List<Student> studentList;
    private LayoutInflater inflater;

    public StudentListAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return studentList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.student_list_item, parent, false);
        }

        Student student = studentList.get(position);

        TextView tvName = convertView.findViewById(R.id.tvStudentName);
        TextView tvMSSV = convertView.findViewById(R.id.tvStudentMSSV);
        ImageView imgStudent = convertView.findViewById(R.id.imgStudent);

        tvName.setText(student.getName());
        tvMSSV.setText("MSSV: " + student.getMssv());

        // Tải ảnh từ thư mục files
        File imageFile = new File(context.getFilesDir(), student.getMssv() + ".jpg");
        if (imageFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            imgStudent.setImageBitmap(bitmap);
        } else {
            imgStudent.setImageResource(R.drawable.face); // Ảnh mặc định
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imgStudent;
        TextView tvStudentName;
        TextView tvStudentMSSV;
    }
}