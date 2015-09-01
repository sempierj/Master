package ltenergy;

import ltitems.LaserMain;
import net.minecraft.nbt.NBTTagCompound;

public class EnergyStack {

	public int amount;
	public LaserMain type;
	public int mode;
	public EnergyStack(LaserMain laser, int quantity)
	{
		type = laser;
		amount = quantity;

	}
	private EnergyStack(){}
	
	public EnergyStack withAmount(int newAmount)
	{
		amount = newAmount;
		return this;
	}
	public LaserMain getType(){
		return type;
	}

	public NBTTagCompound writeAmount(NBTTagCompound tags)
	{
		tags.setInteger("amount", amount);
		return tags;
	}

	public void read(NBTTagCompound tags){
		amount = tags.getInteger("amount");
	}
	
	public static EnergyStack readFromNBT(NBTTagCompound tags){
		if(tags == null || tags.hasNoTags())
		{
			return null;
		}
		EnergyStack stack = new EnergyStack();
		stack.read(tags);
		
		if(stack.amount< 0)
		{
			return null;
		}
	
		
		return stack;
	}
	
	public EnergyStack copy()
	{
		return new EnergyStack(type,amount);
	}
	

}
