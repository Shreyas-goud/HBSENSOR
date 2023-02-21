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
    public static double getHemoLevel(Context context){
        calculateRGB(context);
        double hemoLevel = 0;
        if((red > 160 && red < 180) && (green > 160 && green < 180)&&(blue > 120 && blue < 140)){
            hemoLevel = 2.5;
        }else if((red > 140 && red < 160) && (green > 180 && green < 200)&&(blue > 100 && blue < 120))
            hemoLevel = 3.0;
        else if(red > 180 && red < 200 && green > 200 && green < 210)
            hemoLevel = 4.0;
        else if(red > 180 && red < 200 && green > 120 && green < 140)
            hemoLevel = 5.0;
        else
            hemoLevel = 12;
        return hemoLevel;
    }
}
