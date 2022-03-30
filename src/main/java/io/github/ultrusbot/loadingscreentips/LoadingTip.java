package io.github.ultrusbot.loadingscreentips;

import com.google.gson.*;
import net.minecraft.util.JsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class LoadingTip {
    private final boolean replace;
    private final List<String> tips;

    public LoadingTip(boolean replace, List<String> tips) {
        this.replace = replace;
        this.tips = tips;
    }

    public List<String> getTips() {
        return tips;
    }

    public boolean isReplace() {
        return replace;
    }

    @SuppressWarnings("unused")
    public static class Serializer implements JsonDeserializer<LoadingTip>, JsonSerializer<LoadingTip> {

        @Override
        public LoadingTip deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = JsonHelper.asObject(json, "loadingscreentips");
            List<String> tips = new ArrayList<>();
            boolean replace = JsonHelper.getBoolean(jsonObject, "replace", false);
            JsonHelper.getArray(jsonObject, "tips").iterator().forEachRemaining(jsonElement -> {
                tips.add(jsonElement.getAsString());
            });
            return new LoadingTip(replace, tips);

        }

        @Override
        public JsonElement serialize(LoadingTip src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("replace", src.replace);
            JsonArray tips = new JsonArray();
            src.tips.forEach(tips::add);
            jsonObject.add("tips", tips);
            return jsonObject;
        }
    }
}
