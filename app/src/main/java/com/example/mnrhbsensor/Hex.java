package com.example.mnrhbsensor;

import android.content.Context;

public class Hex {
    static int red = 0,green = 0,blue = 0;
    static String hex = "";
    public static void calculateRGB(Context context){
        hex = MemoryData.getHexColor();
        red = convertToColorVal(hex,1,3);
        green = convertToColorVal(hex,3,5);
        blue = convertToColorVal(hex,5,7);
    }
    public static int convertToColorVal(String hex, int a,int b){
        int color = 0;
        int mul = 16;
        for(int i=a;i<b;i++){
            mul = i % 2 == 0 ? 1 : 16;
            switch (hex.charAt(i)){
                case 'A' : color += 10 * mul; break;
                case 'B' : color += 11 * mul; break;
                case 'C' : color += 12 * mul; break;
                case 'D' : color += 13 * mul; break;
                case 'E' : color += 14 * mul; break;
                case 'F' : color += 15 * mul; break;
                default: color += (hex.charAt(i) - 48)  * mul;
            }
        }
        return color;
    }

    //contains dummy values needs to be updated later
    public static double getHemoLevel(Context context){
        calculateRGB(context);
        double hemoLevel = 0;
        if((red >= 200) && (green > 160 && green < 190)&&(blue > 150 && blue < 180)){
            hemoLevel = 2.5;
        }else if((red > 100 && red < 140) && (green > 110 && green < 160)&&(blue > 90 && blue < 130))
            hemoLevel = 3.0;
        else if((red > 60 && red < 100) && (green > 90 && green < 130)&&(blue > 40 && blue < 80))
            hemoLevel = 4.0;
        else if((red >= 200) && (green > 140 && green < 190)&&(blue > 110 && blue < 150))
            hemoLevel = 5.0;
        else if((red >= 200) && (green > 120 && green < 160)&&(blue > 70 && blue < 100))
            hemoLevel = 6.0;
        else if((red > 140 && red < 160) && (green > 20 && green < 40)&&(blue > 20 && blue < 40))
            hemoLevel = 7.0;
        else if((red > 150 && red < 180) && (green > 0)&&(blue > 0))
            hemoLevel = 9.0;
        else if((red > 120 && red < 150) && (green > 0)&&(blue > 0))
            hemoLevel = 10.0;
        else if((red > 80 && red < 110) && (green > 0)&&(blue > 0))
            hemoLevel = 12.0;
        else
            hemoLevel = 0;
        return hemoLevel;
    }
}
