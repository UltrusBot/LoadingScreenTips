package me.ultrusmods.loadingscreentips.mixin;

import me.ultrusmods.loadingscreentips.LoadingScreenTips;
import me.ultrusmods.loadingscreentips.config.LoadingScreenTipsConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

//TODO: Rewrite this

@Environment(EnvType.CLIENT)
@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin extends Screen {

    protected LevelLoadingScreenMixin(Text text) {
        super(text);
    }

    private String randomTip;
    float tipTimer = 0f;

    @Inject(method = "<init>", at = @At("TAIL"))
    void pickRandomTip(WorldGenerationProgressTracker worldGenerationProgressTracker, CallbackInfo ci) {
        randomTip = LoadingScreenTips.getRandomTip();
    }

    @Inject(method = "render", at = @At("TAIL"))
    void drawLoadingTip(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        tipTimer += delta;
        //TODO: Add config to change this value.
        if (tipTimer >= LoadingScreenTipsConfig.changeTime) {
            randomTip = LoadingScreenTips.getRandomTip();
            tipTimer = 0;
        }
        List<OrderedText> wrappedText = textRenderer.wrapLines(Text.translatable(randomTip), width/3);
        int textY = (LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_LEFT || LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_RIGHT) ? this.height - this.textRenderer.fontHeight : this.textRenderer.fontHeight;
        int textX = (LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.TOP_LEFT || LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_LEFT) ? 0 : (int)(width/1.5f);
        if (LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_LEFT || LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_RIGHT) {
            for (int i = wrappedText.size() - 1; i >= 0; i--) {
                textY = renderTipTextLine$LoadingScreenTips(matrices, wrappedText, textY, textX, i);
            }
            drawTextWithShadow(matrices, this.textRenderer, Text.translatable("text.loadingscreentips.tip"), textX, textY, 3847130);
        } else {
            drawTextWithShadow(matrices, this.textRenderer, Text.translatable("text.loadingscreentips.tip"), textX, textY, 3847130);
            textY += textRenderer.fontHeight * 1.25f;
            for (int i = 0; i < wrappedText.size(); i++) {
                textY = renderTipTextLine$LoadingScreenTips(matrices, wrappedText, textY, textX, i);
            }
        }
    }

    @Unique
    private int renderTipTextLine$LoadingScreenTips(MatrixStack matrices, List<OrderedText> wrappedText, int textY, int textX, int i) {
        OrderedText orderedText = wrappedText.get(i);
        textRenderer.draw(matrices, orderedText, textX, textY, 16777215);
        textY -= ((LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_LEFT || LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_RIGHT) ? 1 : -1 ) * textRenderer.fontHeight * 1.25f;
        return textY;
    }
}
