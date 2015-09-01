package ltnetwork;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import lasercoil.Main;
import ltitems.LaserMain;
import ltnetwork.LaserPacketHandler.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LTKeyHandler {
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event){
		if(LaserKeyHandler.mode.isPressed()){
			EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
			ItemStack wep = player.getCurrentEquippedItem();
			Item item = wep.getItem();
			int set;
			
			if(item instanceof LaserMain){
				LaserMain laser = (LaserMain)item;
				laser.incrementMode(wep);
				Main.packetHandler.sendToServer(new LaserMessage(LaserPacket.MODE, player.getDisplayName(), false));
			}

		}
	}
}