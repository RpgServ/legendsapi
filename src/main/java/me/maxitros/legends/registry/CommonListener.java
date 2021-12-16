package me.maxitros.legends.registry;


import me.maxitros.legends.ClientData;
import me.maxitros.legends.Legends;
import me.maxitros.legends.capabilities.IMagicStats;
import me.maxitros.legends.capabilities.TrackingStats;
import me.maxitros.legends.capabilities.providers.MagicStatsProvider;
import me.maxitros.legends.networking.PacketHandler;
import me.maxitros.legends.networking.SyncMagicStatsPacket;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommonListener
{

    public static void RegisterBlockAndItems(){

    }

    @SubscribeEvent
    public void onRegisterEntities(RegistryEvent.Register<EntityEntry> event)    {

    }

    @SideOnly(Side.CLIENT)
    public static void registerRender() {

    }
    @SideOnly(Side.CLIENT)
    private static void setItemBlockRender(Block block) {

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }
}
