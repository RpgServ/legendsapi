package me.maxitros.legends;

import me.maxitros.legends.capabilities.CapabilityHandler;
import me.maxitros.legends.commands.AddXpCommand;
import me.maxitros.legends.commands.SetDamageCmd;
import me.maxitros.legends.commands.SetLvlCmd;
import me.maxitros.legends.commands.SetSkillCmd;
import me.maxitros.legends.proxy.CommonProxy;
import me.maxitros.legends.registry.CapaListener;
import me.maxitros.legends.registry.CommonListener;
import me.maxitros.legends.skills.magic.MagicServerHandler;
import me.maxitros.legends.skills.phisical.PhysicalEventHandler;
import me.maxitros.legends.skills.talants.TalentEventHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Legends.modId, name = Legends.name, version = Legends.version)
public class Legends {
    public static final String modId = "legendsapi";
    public static final String name = "Legends api";
    public static final String version = "1.0.0";
    public static MinecraftServer server;
    private static CreativeTabs ItemGroup;
    public static final EnumFacing capaSide = EnumFacing.UP;

    public static CreativeTabs getItemGroup()
    {
        if (ItemGroup == null)
        {
            ItemGroup = new CreativeTabs(CreativeTabs.getNextID(), "Lagends api")
            {
                @Override
                public ItemStack getTabIconItem()
                {
                    return new ItemStack(Blocks.IRON_ORE);
                }
            };
        }
        return ItemGroup;
    }

    @Mod.Instance(modId)
    public static Legends instance;
    @SidedProxy(clientSide = "me.maxitros.legends.proxy.ClientProxy", serverSide = "me.maxitros.legends.proxy.ServerProxy")
    public static CommonProxy proxy;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CommonListener.RegisterBlockAndItems();
        MinecraftForge.EVENT_BUS.register(new CommonListener());
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(new CapaListener());
        MinecraftForge.EVENT_BUS.register(new PhysicalEventHandler());
        MinecraftForge.EVENT_BUS.register(new TalentEventHandler());
        MinecraftForge.EVENT_BUS.register(new MagicServerHandler());
        proxy.preInit(event);
    }




    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event)
    {
        server=event.getServer();
        event.registerServerCommand(new SetDamageCmd());
        event.registerServerCommand(new SetSkillCmd());
        event.registerServerCommand(new SetLvlCmd());
        event.registerServerCommand(new AddXpCommand());
    }

}
