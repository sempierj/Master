package ltgui;


import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ltcontainer.ContainerInfuser;
import ltcontainer.ContainerRecharge;
import ltcontainer.ContainerRefractory;
import ltentity.TileEntityInfuser;
import ltentity.TileEntityRecharge;
import ltentity.TileEntityRefractory;

public class guiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		if(!world.isRemote){
		TileEntity tileEntity = world.getTileEntity(x,y,z);
		if(tileEntity instanceof TileEntityInfuser){
			return new ContainerInfuser(player.inventory, (TileEntityInfuser) tileEntity);
		}
		else if(tileEntity instanceof TileEntityRefractory){
			return new ContainerRefractory(player.inventory, (TileEntityRefractory) tileEntity);
		}
		else if(tileEntity instanceof TileEntityRecharge){
			return new ContainerRecharge(player.inventory, (TileEntityRecharge) tileEntity);
		}
		}
		return null;
		
	}
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity tileEntity = world.getTileEntity(x,y,z);
		if(tileEntity instanceof TileEntityInfuser){
			return new infuserGUI(player.inventory, (TileEntityInfuser) tileEntity);
		}
		else if(tileEntity instanceof TileEntityRefractory)
		{
			return new RefractoryGui(player.inventory, (TileEntityRefractory) tileEntity);
		}
		else if(tileEntity instanceof TileEntityRecharge)
		{
			return new RechargeGui(player.inventory, (TileEntityRecharge) tileEntity);
		}
		return null;
		
	}
}
