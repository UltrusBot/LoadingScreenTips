package me.ultrusmods.loadingscreentips.mixin;

import me.ultrusmods.loadingscreentips.LoadingScreenTips;
import me.ultrusmods.loadingscreentips.TipShowingScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
import net.minecraft.client.world.ClientChunkLoadProgress;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//TODO: Rewrite this

@Environment(EnvType.CLIENT)
@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin extends Screen implements TipShowingScreen {

    protected LevelLoadingScreenMixin(Text text) {
        super(text);
    }

    private String randomTip;
    float tipTimer = 0f;

    @Inject(method = "<init>", at = @At("TAIL"))
    void pickRandomTip(ClientChunkLoadProgress clientChunkLoadProgress, LevelLoadingScreen.WorldEntryReason worldEntryReason, CallbackInfo ci) {
        randomTip = LoadingScreenTips.getRandomTip();
    }

    @Inject(method = "render", at = @At("TAIL"))
    void drawLoadingTip(DrawContext graphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        drawLoadingTips(textRenderer, graphics, width, height, delta);
    }

    @Override
    public float getTipTimer() {
        return tipTimer;
    }

    @Override
    public void setRandomTip(String randomTip) {
        this.randomTip = randomTip;
    }

    @Override
    public void setTipTimer(float tipTimer) {
        this.tipTimer = tipTimer;
    }

    @Override
    public String getRandomTip() {
        return randomTip;
    }
}
