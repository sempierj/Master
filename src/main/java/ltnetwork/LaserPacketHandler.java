package ltnetwork;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import ltitems.LaserMain;
import ltnetwork.LaserPacketHandler.LaserMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class LaserPacketHandler  implements IMessageHandler<LaserMessage, IMessage>
{
	@Override
	public	IMessage onMessage(LaserMessage message, MessageContext context)
	{
		EntityPlayer player = PacketHandler.getPlayer(context);
		
		if(message.type == LaserPacket.MODE)
		{
			ItemStack item = player.getCurrentEquippedItem();
			
			if(item !=null && item.getItem() instanceof LaserMain)
			{
				if(!message.value)
				{
					((LaserMain)item.getItem()).incrementMode(item);
				}
			}
		}
		return null;
	}
	
	public static class LaserMessage implements IMessage
	{
		public LaserPacket type;
		public String user;
		public boolean value;
		
		public LaserMessage(){}
		
		public LaserMessage(LaserPacket x, String name, boolean z)
		{
			type = x;
			user = name;
			value = z;
			
		}
		
		@Override
		public void toBytes(ByteBuf data)
		{
			data.writeInt(type.ordinal());
			
		if(type == LaserPacket.MODE)
		{
			data.writeBoolean(value);
		}
		
		}
		
		@Override
		public void fromBytes(ByteBuf data)
		{
			type = LaserPacket.values()[data.readInt()];
			
			if(type == LaserPacket.MODE)
			{
				value = data.readBoolean();
			}
		}
	}
	
	public static enum LaserPacket
	{
		MODE;
	}
}
