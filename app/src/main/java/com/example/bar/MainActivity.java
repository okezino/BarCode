package com.example.bar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;


import com.google.zxing.BarcodeFormat;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name","Joseph Okeh Simon");
            jsonObject.put("phone number","08022320123");
            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.fcmb_logo_toolbar);



            DoneDecoder barcodeEncoder = new DoneDecoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(jsonObject.toString(), BarcodeFormat.QR_CODE, 400, 400);
            ImageView imageViewQrCode = (ImageView) findViewById(R.id.code);
            imageViewQrCode.setImageBitmap(DoneDecoder.mergeToPin(bitmap, DoneDecoder.getResizedBitmap(largeIcon, 60, 60)));
        } catch(Exception e) {
            e.printStackTrace();

        }
    }
}