package me.ultrusmods.loadingscreentips.config;

import static eu.midnightdust.lib.config.MidnightConfig.Entry;

//TODO At some point: Instead of corners, add a vertical & horizontal offset (percentage based)
public class LoadingScreenTipsConfig {
    @Entry
    public static CORNER corner = CORNER.BOTTOM_LEFT;

    @Entry(min = 0)
    public static float changeTime = 100f;

    @Entry
    public static boolean serverLoadingTips = true;
    public enum CORNER {
        TOP_LEFT,
        BOTTOM_LEFT,
        TOP_RIGHT,
        BOTTOM_RIGHT
    }
}
