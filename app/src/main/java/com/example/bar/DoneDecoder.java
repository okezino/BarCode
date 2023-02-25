package com.example.bar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Map;

public class DoneDecoder {

    private static final int WHITE = 0xFFFFFFFF;
   // private static final int BLACK = 0xFF000000;
    private static final int BLACK = 0xFF4b0082;


    public DoneDecoder(){

    }
    public Bitmap createBitmap(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height) throws WriterException {
        try {
            return new MultiFormatWriter().encode(contents, format, width, height);
        } catch (WriterException e) {
            throw e;
        } catch (Exception e) {
            // ZXing sometimes throws an IllegalArgumentException
            throw new WriterException(e);
        }
    }

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        try {
            return new MultiFormatWriter().encode(contents, format, width, height, hints);
        } catch (WriterException e) {
            throw e;
        } catch (Exception e) {
            throw new WriterException(e);
        }
    }

    public Bitmap encodeBitmap(String contents, BarcodeFormat format, int width, int height) throws WriterException {
        return createBitmap(encode(contents, format, width, height));
    }

    public Bitmap encodeBitmap(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        return createBitmap(encode(contents, format, width, height, hints));
    }

    public static Bitmap mergeToPin(Bitmap back, Bitmap front) {
        Bitmap result = Bitmap.createBitmap(back.getWidth(), back.getHeight(), back.getConfig());
        Canvas canvas = new Canvas(result);
        int widthBack = back.getWidth();
        int widthFront = front.getWidth();
        float move = (widthBack - widthFront) / 2;
        canvas.drawBitmap(back, 0f, 0f, null);
        canvas.drawBitmap(front, move, move, null);
        return result;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }




}
