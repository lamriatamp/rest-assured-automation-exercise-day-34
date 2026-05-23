package utils;

import java.util.Random;

public class utils {
    public static int getRandomTwoDigit(){
        return new Random().nextInt(90) + 10;
    }

    public static String getCategoryName(){
        return "category" + getRandomTwoDigit();
    }
}
