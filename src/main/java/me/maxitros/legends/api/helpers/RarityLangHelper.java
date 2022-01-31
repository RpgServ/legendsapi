package me.maxitros.legends.api.helpers;

import me.maxitros.legends.api.Rarity;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;

import java.awt.*;

public class RarityLangHelper {
    public static String GetRarityString(Rarity rarity)
    {
        switch (rarity)
        {
            case Unusual:
                return new TextComponentTranslation("legendsapi.api.unusual", new Object[0]).getUnformattedText();
            case Rare:
                return new TextComponentTranslation("legendsapi.api.rare", new Object[0]).getUnformattedText();
            case Epic:
                return new TextComponentTranslation("legendsapi.api.epic", new Object[0]).getUnformattedText();
            case Legendary:
                return new TextComponentTranslation("legendsapi.api.legendary", new Object[0]).getUnformattedText();
        }
        return "";
    }
}
