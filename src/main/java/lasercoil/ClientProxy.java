package lasercoil;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy{
	
		@Override
		public void preInit()
		{}
		
		@Override
		public EntityPlayer getPlayer(MessageContext context)
		{
			if(FMLCommonHandler.instance().getEffectiveSide().isServer())
			{
				return context.getServerHandler().playerEntity;
			}
			else
			{
				return Minecraft.getMinecraft().thePlayer;
			}
		}
}
