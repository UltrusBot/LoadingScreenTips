package me.ultrusmods.loadingscreentips;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

import static java.lang.System.Logger.Level.ERROR;

public class LoadingTipsLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
    public static final System.Logger LOGGER = System.getLogger(LoadingScreenTips.MOD_ID);
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
                    LOGGER.log(ERROR, "Couldn't parse tips: {}", identifier, e);
                }
            }
        }));
    }

}
