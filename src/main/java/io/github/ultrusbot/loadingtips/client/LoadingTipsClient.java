package io.github.ultrusbot.loadingtips.client;

import io.github.ultrusbot.loadingtips.LoadingTipsLoader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;


@Environment(EnvType.CLIENT)
public class LoadingTipsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new LoadingTipsLoader());
    }
}
