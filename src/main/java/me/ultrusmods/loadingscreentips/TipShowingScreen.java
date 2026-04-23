package me.ultrusmods.loadingscreentips;

import me.ultrusmods.loadingscreentips.config.LoadingScreenTipsConfig;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import java.util.List;

public interface TipShowingScreen {

    default void drawLoadingTips(TextRenderer textRenderer, DrawContext graphics, int width, int height, float delta) {
        setTipTimer(getTipTimer() + delta);
        if (getTipTimer() >= LoadingScreenTipsConfig.changeTime) {
            selectRandomTip();
            setTipTimer(0f);
        }
        List<OrderedText> wrappedText = textRenderer.wrapLines(Text.translatable(getRandomTip()), width/3);
        int textY = (LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_LEFT || LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_RIGHT) ? height - textRenderer.fontHeight : textRenderer.fontHeight;
        int textX = (LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.TOP_LEFT || LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_LEFT) ? 0 : (int)(width/1.5f);
        if (LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_LEFT || LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_RIGHT) {
            for (int i = wrappedText.size() - 1; i >= 0; i--) {
                textY = renderTipTextLine(graphics, wrappedText, textY, textX, i, textRenderer);
            }
            graphics.drawTextWithShadow(textRenderer, Text.translatable("text.loadingscreentips.tip"), textX, textY, -12930086);
        } else {
            graphics.drawTextWithShadow(textRenderer, Text.translatable("text.loadingscreentips.tip"), textX, textY, -12930086);
            textY += textRenderer.fontHeight * 1.25f;
            for (int i = 0; i < wrappedText.size(); i++) {
                textY = renderTipTextLine(graphics, wrappedText, textY, textX, i, textRenderer);
            }
        }
    }

    private int renderTipTextLine(DrawContext graphics, List<OrderedText> wrappedText, int textY, int textX, int i, TextRenderer textRenderer) {
        OrderedText orderedText = wrappedText.get(i);
        graphics.drawTextWithShadow(textRenderer, orderedText, textX, textY, -1);
        textY -= ((LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_LEFT || LoadingScreenTipsConfig.corner == LoadingScreenTipsConfig.CORNER.BOTTOM_RIGHT) ? 1 : -1 ) * textRenderer.fontHeight * 1.25f;
        return textY;
    }


    void setTipTimer(float timer);
    float getTipTimer();

    default void selectRandomTip() {
        setRandomTip(LoadingScreenTips.getRandomTip());
    }

    void setRandomTip(String tip);

    String getRandomTip();
}
