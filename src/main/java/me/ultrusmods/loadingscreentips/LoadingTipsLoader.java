package me.ultrusmods.loadingscreentips;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

import static java.lang.System.Logger.Level.ERROR;

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
        prepared.forEach((identifier, jsonElement) -> {
            LoadingTip.CODEC.decode(JsonOps.INSTANCE, jsonElement).result().ifPresent((tipPair -> {
                LoadingTip tip = tipPair.getFirst();
                if (identifier.getPath().equals("tips")) {
                    if (tip.replace()) {
                        LoadingScreenTips.TIPS = tip.tips();
                    } else {
                        LoadingScreenTips.TIPS.addAll(tip.tips());
                    }
                }
            }));
        });
    }

}
