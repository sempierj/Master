package lasercoil;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy {

	
	public Object getClientGui(int ID, EntityPlayer player, World world, int x , int y, int z)
	{
		return null;
	}
	
	public void preInit(){}
	
	public EntityPlayer getPlayer(MessageContext context)
	{
		return context.getServerHandler().playerEntity;
	}
}
