package me.ultrusmods.loadingscreentips;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class LoadingTipsLoader extends JsonDataLoader<LoadingTip> implements IdentifiableResourceReloadListener {

    public static final IdentifiableResourceReloadListener INSTANCE = new LoadingTipsLoader();

    protected LoadingTipsLoader() {
        super(LoadingTip.CODEC, ResourceFinder.json("loading_tips"));
    }

    @Override
    public Identifier getFabricId() {
        return Identifier.of(LoadingScreenTips.MOD_ID, "loading_tips");
    }

    @Override
    protected void apply(Map<Identifier, LoadingTip> prepared, ResourceManager resourceManager, Profiler profiler) {
        for (Map.Entry<Identifier, LoadingTip> entry : prepared.entrySet()) {
            Identifier identifier = entry.getKey();
            LoadingTip tip = entry.getValue();
            if (identifier.getPath().equals("tips")) {
                if (tip.replace()) {
                    LoadingScreenTips.TIPS.clear();
                }
                LoadingScreenTips.TIPS.addAll(tip.tips());
            }
        }
    }
}
