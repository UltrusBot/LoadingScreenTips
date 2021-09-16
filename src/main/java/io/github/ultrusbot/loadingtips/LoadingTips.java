package io.github.ultrusbot.loadingtips;

import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoadingTips implements ModInitializer {
    public static final String MOD_ID = "loadingtips" ;
    public static List<String> TIPS = new ArrayList<>();
    public static final Random RANDOM_TIP = new Random();

    public static String getRandomTip() {
        return TIPS.get(RANDOM_TIP.nextInt(TIPS.size()));
    }


    @Override
    public void onInitialize() {

    }
}
