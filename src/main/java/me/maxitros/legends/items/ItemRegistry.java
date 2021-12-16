package me.maxitros.legends.items;

import me.maxitros.legends.Legends;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemRegistry {
    public static final Item CUSTOM_BOW = new CustomBow().setRegistryName(Legends.modId,"custombow").setUnlocalizedName("custombow");

    public static void RegisterItems()
    {
        ForgeRegistries.ITEMS.register(CUSTOM_BOW);
    }
}
