package lasercoil;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import ltentity.EntityBlast;
import ltentity.EntityBlast2;
import ltentity.EntityBlast2BURST;
import ltentity.EntityBlast3;
import ltentity.EntityBlast3BURST;
import ltentity.EntityBlastBURST;
import ltentity.EntityMine;
import ltentity.TileEntityInfuser;
import ltentity.TileEntityRecharge;
import ltentity.TileEntityRefractory;
import ltgui.LTGuiOverlay;
import ltgui.guiHandler;
import ltinit.LTItemInit;
import ltinit.RecipeInit;
import ltitems.LTBlocksInit;
import ltnetwork.LTKeyHandler;
import ltnetwork.LaserKeyHandler;
import ltnetwork.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;


@Mod(modid="laserCoil", name = "Laser Coils", version="0.1")
public class Main
{
public static String MODID = "laserCoil";
public static String VERSION = "0.1";

public static PacketHandler packetHandler = new PacketHandler();

@SidedProxy(clientSide = "lasercoil.ClientProxy", serverSide = "lasercoil.CommonProxy")
public static CommonProxy proxy;

@Instance("laserCoil")
public static Main instance;

public static CreativeTabs tabName = new CreativeTabs("ltcoil")
{
public Item getTabIconItem()
{
return Items.arrow;
}
};

@EventHandler
public void preInit(FMLPreInitializationEvent e)
{
	LTItemInit.mainRegistry();
	LTBlocksInit.mainRegistry();
	FMLCommonHandler.instance().bus().register(new LTKeyHandler());

	LaserKeyHandler.init();
}

@EventHandler
public void init(FMLInitializationEvent e)
{    
		RecipeInit.mainRegistry();
	    NetworkRegistry.INSTANCE.registerGuiHandler(this, new guiHandler());
        instance = this;
        GameRegistry.registerTileEntity(TileEntityInfuser.class, "1");
        GameRegistry.registerTileEntity(TileEntityRefractory.class, "2");
        GameRegistry.registerTileEntity(TileEntityRecharge.class, "3");
        packetHandler.initialize();
        EntityRegistry.registerModEntity(EntityBlast.class, "Laser Blast", 1, this, 64, 10, true);
        RenderingRegistry.registerEntityRenderingHandler(EntityBlast.class, new RenderSnowball(LTItemInit.blast));
        EntityRegistry.registerModEntity(EntityBlast2.class, "Laser Blast2", 2, this, 64, 10, true);
        RenderingRegistry.registerEntityRenderingHandler(EntityBlast2.class, new RenderSnowball(LTItemInit.blast2));
        EntityRegistry.registerModEntity(EntityBlastBURST.class, "Laser Blast Burst", 3, this, 64, 10, true);
        RenderingRegistry.registerEntityRenderingHandler(EntityBlastBURST.class, new RenderSnowball(LTItemInit.blast));
        EntityRegistry.registerModEntity(EntityBlast2BURST.class, "Laser Blast2 Burst", 4, this, 64, 10, true);
        RenderingRegistry.registerEntityRenderingHandler(EntityBlast2BURST.class, new RenderSnowball(LTItemInit.blast2));
        EntityRegistry.registerModEntity(EntityMine.class, "MiningShots", 5, this, 64, 10, true);
        RenderingRegistry.registerEntityRenderingHandler(EntityMine.class, new RenderSnowball(LTItemInit.blast2));
        EntityRegistry.registerModEntity(EntityBlast3.class, "Laser Blast3", 6, this, 64, 10, true);
        RenderingRegistry.registerEntityRenderingHandler(EntityBlast3.class, new RenderSnowball(LTItemInit.blast3));
        EntityRegistry.registerModEntity(EntityBlast3BURST.class, "Laser Blast3 Burst", 7, this, 64, 10, true);
        RenderingRegistry.registerEntityRenderingHandler(EntityBlast3BURST.class, new RenderSnowball(LTItemInit.blast3));
        
}

@EventHandler
public void postInit(FMLPostInitializationEvent e)
{
	MinecraftForge.EVENT_BUS.register(new LTGuiOverlay(Minecraft.getMinecraft()));
}
}
