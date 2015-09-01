package ltnetwork;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

@SideOnly(Side.CLIENT)
public class LaserKeyHandler{
		public static KeyBinding mode;

		public static void init(){
			mode = new KeyBinding("key.mode", Keyboard.KEY_R, "key.categories.mymod");
			
			ClientRegistry.registerKeyBinding(mode);
		}
}
