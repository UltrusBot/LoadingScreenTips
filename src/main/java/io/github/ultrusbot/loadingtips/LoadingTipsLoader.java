package io.github.ultrusbot.loadingtips;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Map;

public class LoadingTipsLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new Gson();
    public LoadingTipsLoader() {
        super(new Gson(), "loading_tips");
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(LoadingTips.MOD_ID, "loading_tips");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        prepared.forEach(((identifier, jsonElement) -> {
            if (identifier.getPath().equals("tips"))  {
                try {
                   LoadingTip tip = GSON.fromJson(jsonElement, LoadingTip.class);
                   if (tip.isReplace()) {
                       LoadingTips.TIPS = tip.getTips();
                   } else {
                       LoadingTips.TIPS.addAll(tip.getTips());
                   }
                } catch (Exception e) {
                    LOGGER.error("Couldn't parse tips: {}", identifier, e);
                }
            }
        }));
    System.out.println(LoadingTips.TIPS);
    }

}
