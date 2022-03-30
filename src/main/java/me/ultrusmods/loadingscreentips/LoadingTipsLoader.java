package me.ultrusmods.loadingscreentips;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.slf4j.Logger;

import java.util.Map;

public class LoadingTipsLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new Gson();
    public LoadingTipsLoader() {
        super(new Gson(), "loading_tips");
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(LoadingScreenTips.MOD_ID, "loading_tips");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        prepared.forEach(((identifier, jsonElement) -> {
            if (identifier.getPath().equals("tips"))  {
                try {
                   LoadingTip tip = GSON.fromJson(jsonElement, LoadingTip.class);
                   if (tip.isReplace()) {
                       LoadingScreenTips.TIPS = tip.getTips();
                   } else {
                       LoadingScreenTips.TIPS.addAll(tip.getTips());
                   }
                } catch (Exception e) {
                    LOGGER.error("Couldn't parse tips: {}", identifier, e);
                }
            }
        }));
    System.out.println(LoadingScreenTips.TIPS);
    }

}
