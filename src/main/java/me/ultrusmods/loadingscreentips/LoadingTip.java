package me.ultrusmods.loadingscreentips;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record LoadingTip(boolean replace, List<String> tips) {
    public static final Codec<LoadingTip> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.BOOL.fieldOf("replace").forGetter(LoadingTip::replace),
            Codec.STRING.listOf().fieldOf("tips").forGetter(LoadingTip::tips)).apply(builder, LoadingTip::new));
}
