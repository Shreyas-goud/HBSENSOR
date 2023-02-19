package com.example.mnrhbsensor;

import android.content.Context;

public class Hex {
    public void calculateRGB(Context context, String hex){
        int red = convertToColorVal(hex,1,3);
        int green = convertToColorVal(hex,3,5);
        int blue = convertToColorVal(hex,5,7);
    }
    public int convertToColorVal(String hex, int a,int b){
        int color = 0;
        for(int i=a;i<b;i++){
            switch (hex.charAt(i)){
                case 'A' : color += 10 * 16; break;
                case 'B' : color += 11 * 16; break;
                case 'C' : color += 12 * 16; break;
                case 'D' : color += 13 * 16; break;
                case 'E' : color += 14 * 16; break;
                case 'F' : color += 15 * 16; break;
                default: color += (hex.charAt(i) - 48);
            }
        }
        return color;
    }

}
