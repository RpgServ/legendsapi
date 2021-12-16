package me.maxitros.legends.proxy;


import me.maxitros.legends.entities.magic.*;
import me.maxitros.legends.items.ItemRegistry;
import me.maxitros.legends.registry.CommonListener;
import me.maxitros.legends.render.*;
import me.maxitros.legends.skills.keys.KeybindsRegister;
import me.maxitros.legends.skills.magic.SkillClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        CommonListener.registerRender();
        KeybindsRegister.register();
        MinecraftForge.EVENT_BUS.register(new SkillClientHandler());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerModel(ItemRegistry.CUSTOM_BOW, 0);
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicArrow.class, manager -> new RenderMagicArrow(manager));
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicGrenade.class, manager -> new RenderMagicGrenade<EntityMagicGrenade>(manager, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityFallingCubes.class, manager -> new RenderNullEntity<EntityFallingCubes>(manager));
        RenderingRegistry.registerEntityRenderingHandler(EntityFallingCube.class, manager -> new RenderFallingCubes(manager));
        RenderingRegistry.registerEntityRenderingHandler(EntityIceCube.class, manager -> new RenderIceCube(manager));
        RenderingRegistry.registerEntityRenderingHandler(EntityMine.class, manager -> new RenderMine<EntityMine>(manager, Minecraft.getMinecraft().getRenderItem()));
    }
    private static void registerModel(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

}
