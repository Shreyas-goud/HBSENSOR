package com.example.mnrhbsensor;

import static java.lang.System.currentTimeMillis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Date;

public final class MemoryData {
    private static String color = "";
    private static Bitmap bitmap;
    private static String name = "";
    private static String userId = "";

    private static String imageUrl = "";
    private static Uri image;
    public static void saveData(String data, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("mdata.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveEmail(String data, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("email.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveUsername(String data, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("username.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveHexColor(String data, Context context) {
        color = data;
    }

    public static void saveBitmap(Bitmap bit, Context context){bitmap = bit;}

    public static Bitmap getBitmap(Context context){
        return bitmap;
    }

    public static Bitmap cropToCenter(Context context, Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int size = Math.min(width,height)/2;
        int x = (width - size)/2;
        int y = (height - size)/2;

        return Bitmap.createBitmap(bitmap, x,y,size,size);
    }

     /*Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, size, size);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output; */

    public static void saveImageToGallery(Bitmap bitmap, Context context) {
        String fileName = currentTimeMillis() + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File imageFile = new File(storageDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imageFile.getAbsolutePath(), imageFile.getName(), imageFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  void saveName(Context context, String pname){
        name = pname;
    }

    public static void saveURI(Context context, Uri uri){
        image = uri;
    }

    public static Uri getURI(Context context){
        return image;
    }
    public static String getPname(Context context){
        String pname = name;
        name = "";
        return pname;
    }

    public static void saveUserId(Context context,String id){
        userId = id;
    }

    public static void saveImageUrl(Context context, String url){ imageUrl = url;}

    public static String getImageUrl(Context context){
        String temp = imageUrl;
        imageUrl = "";
        return temp;
    }
    public static String getUserId(Context context){
        String pId = userId;
        userId = "";
        return pId;
    }

    public static String getHexColor(){
        String hexColor = color;
        color = "";
        return hexColor;
    }

    public static String getData(Context context) {
        String data = "";
        try {
            FileInputStream fis = context.openFileInput("mdata.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static String getEmail(Context context) {
        String data = "";
        try {
            FileInputStream fis = context.openFileInput("email.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static String getUsername(Context context) {
        String data = "";
        try {
            FileInputStream fis = context.openFileInput("username.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static void clearUserData(Context context) throws FileNotFoundException {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("mdata.txt", Context.MODE_PRIVATE);
            fileOutputStream.write("".getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("email.txt", Context.MODE_PRIVATE);
            fileOutputStream.write("".getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("username.txt", Context.MODE_PRIVATE);
            fileOutputStream.write("".getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
