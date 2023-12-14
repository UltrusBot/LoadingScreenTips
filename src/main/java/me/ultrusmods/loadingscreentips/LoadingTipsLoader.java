package me.ultrusmods.loadingscreentips;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoadingTipsLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
    public LoadingTipsLoader() {
        super(new Gson(), "loading_tips");
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(LoadingScreenTips.MOD_ID, "loading_tips");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        for (Map.Entry<Identifier, JsonElement> entry : prepared.entrySet()) {
            Identifier identifier = entry.getKey();
            JsonElement jsonElement = entry.getValue();
            AtomicBoolean isReplace = new AtomicBoolean(false);
            LoadingTip.CODEC.decode(JsonOps.INSTANCE, jsonElement).result().ifPresent((tipPair -> {
                LoadingTip tip = tipPair.getFirst();
                if (identifier.getPath().equals("tips")) {
                    if (tip.replace()) {
                        LoadingScreenTips.TIPS.clear();
                        LoadingScreenTips.TIPS.addAll(tip.tips());
                        isReplace.set(true);
                    }
                    LoadingScreenTips.TIPS.addAll(tip.tips());
                }
            }));
            if (isReplace.get()) {
                // We are going from the top resource pack, so at the first replace, ones below it no longer matter
                break;
            }
        }
    }

}
