package me.ultrusmods.loadingscreentips;

import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoadingScreenTips implements ModInitializer {
    public static final String MOD_ID = "loadingscreentips" ;
    public static List<String> TIPS = new ArrayList<>();
    public static final Random RANDOM_TIP = new Random();

    public static String getRandomTip() {
        if (TIPS.size() > 0) {
            return TIPS.get(RANDOM_TIP.nextInt(TIPS.size()));
        } else {
            return "";
        }
    }


    @Override
    public void onInitialize() {

    }
}
