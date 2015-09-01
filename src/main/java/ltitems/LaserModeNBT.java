package ltitems;

import ltitems.LaserMain.LaserMode;
import net.minecraft.nbt.NBTTagCompound;

public class LaserModeNBT {

	
	public LaserMain type;
	public LaserMode mode;
	
	public LaserModeNBT(LaserMain laser, LaserMode m)
	{
		type = laser;
		mode = m;

	}
	private LaserModeNBT(){}
	

	public LaserMain getType(){
		return type;
	}

	public NBTTagCompound writeMode(NBTTagCompound tags)
	{
		tags.setInteger("mode", mode.ordinal());
		return tags;
	}

	public void read(NBTTagCompound tags){
		mode = LaserMode.values()[tags.getInteger("mode")];
	}
	
	public static LaserModeNBT readFromNBT(NBTTagCompound tags){
		if(tags == null || tags.hasNoTags())
		{
			return null;
		}
		LaserModeNBT stack = new LaserModeNBT();
		stack.read(tags);
		
		if(stack.mode != LaserMode.STANDARD)
		{
			if(stack.mode != LaserMode.BURST)
			{
				if(stack.mode != LaserMode.MINING)
				{
					System.out.println("returnNULL");
					return null;
				}
			}
		}
		
		return stack;
	}
	
	public LaserModeNBT copy()
	{
		return new LaserModeNBT(type,mode);
	}
	

}
