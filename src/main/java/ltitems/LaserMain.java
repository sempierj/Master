package ltitems;

import ltenergy.EnergyStack;
import ltentity.EntityBlast;
import ltentity.EntityBlast2;
import ltentity.EntityBlast2BURST;
import ltentity.EntityBlast3;
import ltentity.EntityBlast3BURST;
import ltentity.EntityBlastBURST;
import ltentity.EntityMine;
import ltinit.LTItemInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

	public class LaserMain extends Item
	{
		public static Item type;
		public static EntityPlayer owner;
		public double damage;
		public int energy;
		public boolean first = true;
		
		
	public LaserMain(){
			
		}
	public LaserMode getMode(ItemStack x){	
			if(x.stackTagCompound == null)
		{
			x.setTagCompound(new NBTTagCompound());
		}
		
		LaserModeNBT stored = LaserModeNBT.readFromNBT(x.stackTagCompound.getCompoundTag("mode"));
		
		if(stored == null)
		{
			stored = new LaserModeNBT(this,	LaserMode.STANDARD);
		}
		
		return stored.mode;
	}
	public void setMode(ItemStack x, LaserMode z)
		{
			
			if(x.stackTagCompound == null)
			{
				x.setTagCompound(new NBTTagCompound());
			}
			
			if(z == null)
			{
				x.stackTagCompound.removeTag("mode");

			}
			else{
				LaserModeNBT newmode = new LaserModeNBT(this , z);
				
				x.stackTagCompound.setTag("mode", newmode.writeMode(new NBTTagCompound()));

			}
	}
	public void incrementMode(ItemStack x)
	{
		setMode(x, getMode(x).increment());
	}
	
	public void useEnergy(ItemStack x)
	{
		int mod = 1;

		if(getMode(x) == LaserMode.STANDARD)
		{
			mod = 1;
		}
		else if(getMode(x) == LaserMode.BURST)
		{
			mod = 5;
		}
		else if(getMode(x) == LaserMode.MINING)
		{
			mod = 2;
		}
		int cost = (50*mod) / this.energyMod();
		setEnergy(x, new EnergyStack(getEnergy(x).getType(), getEnergy(x).amount-cost));
	}
	
	public int getMaxEnergy(ItemStack itemstack){
		if(itemstack.getItem().equals(LTItemInit.ironLaser)){
		 return 1500;
		}
		else if(itemstack.getItem().equals(LTItemInit.goldLaser)){
			return 5000;
		}
		else if(itemstack.getItem().equals(LTItemInit.finallaser))
		{
			return 20000;
		}
		return 0;
	}
	
	public int addEnergy(ItemStack x, EnergyStack y)
	{
		if(getEnergy(x) != null && getEnergy(x).getType() != y.getType())
		{
			return 0;
		}
		
		int change = 1;
		setEnergy(x,new EnergyStack(y.getType(),getStored(x)+change));
		return change;
	}
	
	public int getStored(ItemStack x)
	{
		return getEnergy(x) != null ? getEnergy(x).amount : 0;
	}
	public EnergyStack getEnergy(ItemStack x){
		if(x.stackTagCompound == null)
		{
			x.setTagCompound(new NBTTagCompound());
		}
		
		EnergyStack stored = EnergyStack.readFromNBT(x.stackTagCompound.getCompoundTag("stored"));
		
		if(stored == null)
		{
			stored = new EnergyStack(this,0);
		}
		
		return stored;
	}
	
	public void setEnergy(ItemStack x, EnergyStack y)
	{
		if(x.stackTagCompound == null)
		{
			x.setTagCompound(new NBTTagCompound());
		}
		
		if(y == null)
		{
			x.stackTagCompound.removeTag("stored");
		}
		else{
			int amount = y.amount;
			EnergyStack energyStack = new EnergyStack(y.getType(), amount);
			
			x.stackTagCompound.setTag("stored", energyStack.writeAmount(new NBTTagCompound()));
		}
	}

	public int energyMod(){
		energy = 0;
		if(this.equals(LTItemInit.ironLaser)){
			energy+=1;
		}
		else if(this.equals(LTItemInit.goldLaser))
		{
			energy+=2;
		}
		else if(this.equals(LTItemInit.finallaser))
		{
			energy+=4;
		}

		return energy;
	}
	public double getDmg(){
		damage = 0;
		if(this.equals(LTItemInit.ironLaser)){
			damage+=2.0;
		}
		else if(this.equals(LTItemInit.goldLaser)){
			damage+=4.5;
		}
		else if(this.equals(LTItemInit.finallaser)){
			damage+=7.5;
		}
		return damage;
	}
	public ItemStack onItemRightClick(ItemStack x, World world, EntityPlayer player){
		
		
		LaserFire(world, player);
		
		return player.getCurrentEquippedItem();
		
	}

	private void LaserFire(World world, EntityPlayer player)
	{
		if(!player.capabilities.isCreativeMode && canShoot(player.getCurrentEquippedItem())){
			useEnergy(player.getCurrentEquippedItem());
		}
		LaserMode mode =getMode(player.getCurrentEquippedItem());
		if((canShoot(player.getCurrentEquippedItem())||player.capabilities.isCreativeMode)){
			if(this.equals(LTItemInit.ironLaser) && !world.isRemote){
				if(mode == LaserMode.STANDARD)
					world.spawnEntityInWorld(new EntityBlast(world, player, this.getDmg()));
				else if(mode == LaserMode.BURST){
					world.spawnEntityInWorld(new EntityBlastBURST(world, player, this.getDmg()*3));
				}
				else if(mode==LaserMode.MINING){
					world.spawnEntityInWorld(new EntityMine(world,player,this.getDmg()));
				}
			}
			else if(this.equals(LTItemInit.goldLaser) && !world.isRemote){
				if(mode == LaserMode.STANDARD)
				{
					world.spawnEntityInWorld(new EntityBlast2(world,player,this.getDmg()));
				}
				else if(mode == LaserMode.BURST){
					world.spawnEntityInWorld(new EntityBlast2BURST(world, player, this.getDmg()*3));
				}
				else if(mode== LaserMode.MINING){
					world.spawnEntityInWorld(new EntityMine(world,player,this.getDmg()));
				}
			}
			else if(this.equals(LTItemInit.finallaser)&& !world.isRemote){
				if(mode == LaserMode.STANDARD)
				{
					world.spawnEntityInWorld(new EntityBlast3(world,player,this.getDmg()));
				}
				else if(mode == LaserMode.BURST){
					world.spawnEntityInWorld(new EntityBlast3BURST(world,player,this.getDmg()));	
				}
				else if(mode == LaserMode.MINING){
					world.spawnEntityInWorld(new EntityMine(world,player, this.getDmg()));
				}
			}

		}
		
	}
	
	
	public boolean canShoot(ItemStack x){
		if(x.stackTagCompound == null)
		{
			return false;
		}
		int stored = getStored(x);
		LaserMode mode = getMode(x);
		try{
		if(mode == LaserMode.STANDARD){
		return ((stored - ((50/energyMod()))) >=0);
		}
		else if(mode == LaserMode.BURST)
		{
			return ((stored - ((50/energyMod())*5)) >=0);
		}
		else if(mode == LaserMode.MINING){
			return ((stored - ((50/energyMod())*2)) >=0);
		}
		}
		catch(NullPointerException e)
		{
			return false;
		}
		return false;
	}
	@Override
	public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

	@Override
	   public double getDurabilityForDisplay(ItemStack x)
	    {
		 if(x.stackTagCompound == null)
		 {
			 x.setTagCompound(new NBTTagCompound());
			 return 1;
		 }
		 else if(x.stackTagCompound.getCompoundTag("stored") == null)
		 {
			 return 1;
		 }
		 else
		 {
			 EnergyStack stored = EnergyStack.readFromNBT(x.stackTagCompound.getCompoundTag("stored"));
			 try{
				// System.out.println(stored.amount);
				// System.out.println(getMaxEnergy(x));
				 double y = stored.amount+0.0;
				 double z = getMaxEnergy(x)+0.0;
				// System.out.println(1-(stored.amount/getMaxEnergy(x)));
				 return 1-(y/z);
			 }
			 catch (NullPointerException e){
				 return 1;
			 }
			 
		 }
	       
	    }
	public static enum LaserMode
	{
		STANDARD("STANDARD"),
		BURST("BURST"),
		MINING("MINING");
	
		private String unlocal;
		private LaserMode(String s)
		{
			unlocal = s;
		}
		
		public LaserMode increment()
		{
			return ordinal() < values().length-1 ? values()[ordinal()+1] : values()[0];
		}
		
	}
}
