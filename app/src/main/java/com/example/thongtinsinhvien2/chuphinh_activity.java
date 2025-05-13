package com.example.thongtinsinhvien2;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class chuphinh_activity extends AppCompatActivity {
    ImageButton imgpic, btnAssignPhoto;
    ImageView imghinh;
    Button btnback3;
    Bitmap pictureBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_layout);
        imghinh=findViewById(R.id.imghinh);
        imgpic=findViewById(R.id.imgpic);
        btnAssignPhoto = findViewById(R.id.btnAssignPhoto);
        btnback3=findViewById(R.id.btnback3);
        imgpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic_intent=new Intent(ACTION_IMAGE_CAPTURE);
                if (ActivityCompat.checkSelfPermission(chuphinh_activity.this,
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(chuphinh_activity.this,new
                            String[]{android.Manifest.permission.CAMERA}, 1);
                    return;
                }
                startActivityForResult(pic_intent,99);
            }
        });
        btnAssignPhoto.setOnClickListener(v -> {
            if (pictureBitmap != null) {
                // Giảm kích thước ảnh
                int newWidth = pictureBitmap.getWidth() / 4;
                int newHeight = pictureBitmap.getHeight() / 4;
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(pictureBitmap, newWidth, newHeight, true);

                Intent intent = new Intent(this, SelectStudentActivity.class);
                intent.putExtra("photo", resizedBitmap);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Chưa có ảnh!", Toast.LENGTH_SHORT).show();
            }
        });

        btnback3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==99 && resultCode== Activity.RESULT_OK)
        {
            Bitmap picture =(Bitmap) data.getExtras().get("data");
            if (picture != null) {
                pictureBitmap = picture;
                imghinh.setImageBitmap(picture);
            }
        }
    }
}
