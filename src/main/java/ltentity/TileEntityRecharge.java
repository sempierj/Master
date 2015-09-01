package ltentity;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import ltblocks.LTBlocks;
import ltenergy.EnergyStack;
import ltinit.LTItemInit;
import ltitems.LTItems;
import ltitems.LaserMain;
import ltitems.Recharge;

public class TileEntityRecharge extends TileEntity implements ISidedInventory{

	private static final int[] slotsTop = new int[]{0};
	private static final int[] slotsBottom = new int[]{2,1};
	private static final int[] slotsSides = new int[]{1};
	
	private ItemStack[] rechargeItemStacks = new ItemStack[2];
	public int rechargeBurnTime;
	public int currentBurnTime;
	public int rechargeCookTime;
	private String rechargeName;
	public int maxCharge = 25000;
	public int curCharge;
	public EnergyStack energy;

	public void furnaceName(String string){
		this.rechargeName = string;
	}
	
	
	@Override
	public int getSizeInventory() {
		
		return this.rechargeItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return this.rechargeItemStacks[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		if(this.rechargeItemStacks[var1] != null){
			ItemStack itemstack;
			if(this.rechargeItemStacks[var1].stackSize <= var2){
				itemstack = this.rechargeItemStacks[var1];
				this.rechargeItemStacks[var1] = null;
				return itemstack;
			}else{
				itemstack = this.rechargeItemStacks[var1].splitStack(var2);
				
				if(this.rechargeItemStacks[var1].stackSize == 0){
					this.rechargeItemStacks[var1] = null;
				}
				return itemstack;
			}
		}else{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		if(this.rechargeItemStacks[var1] !=null){
			ItemStack itemstack = this.rechargeItemStacks[var1];
			this.rechargeItemStacks[var1] = null;
			return itemstack;
		}
		else{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		this.rechargeItemStacks[var1] = var2;
		
		if(var2 != null && var2.stackSize > this.getInventoryStackLimit()){
			var2.stackSize = this.getInventoryStackLimit();			
		}
		
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.rechargeName : "Recharge";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.rechargeName != null && this.rechargeName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		
		return 64;
	}

	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.rechargeItemStacks = new ItemStack[this.getSizeInventory()];
		
		for(int i = 0; i < tagList.tagCount(); ++i){
			NBTTagCompound tagCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tagCompound1.getByte("Slot");
			
			if(byte0 >= 0 && byte0 < this.rechargeItemStacks.length){
				this.rechargeItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tagCompound1);
			}
		}
		
		this.rechargeBurnTime = tagCompound.getShort("BurnTime");
		this.rechargeCookTime = tagCompound.getShort("CookTime");
		
		this.curCharge = this.rechargeBurnTime;
		
		if(tagCompound.hasKey("CustomName" , 8)){
			this.rechargeName = tagCompound.getString("CustomName");
		}
	}
	
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		tagCompound.setShort("BurnTime", (short)this.curCharge);
		tagCompound.setShort("CookTime", (short)this.rechargeBurnTime);
		NBTTagList tagList = new NBTTagList();
		
		for(int i = 0; i < this.rechargeItemStacks.length; ++i){
			if(this.rechargeItemStacks[i] != null){
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte)i);
				this.rechargeItemStacks[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}
		
		tagCompound.setTag("Items", tagList);
		if(this.hasCustomInventoryName()){
			tagCompound.setString("CustomName", this.rechargeName);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getChargeScaled(int par1){
		return this.curCharge * par1 / 25000;
	}
	
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1){
		if(this.currentBurnTime ==0){
			this.currentBurnTime = 200;
		}
		return this.rechargeBurnTime * par1 / this.currentBurnTime;
	}
	
	public boolean isBurning(){
		return this.canCharge();
	}
	
	public void updateEntity(){
		boolean flag = this.curCharge > 0;
		boolean flag1 = false;
		boolean consume = false;
		
		if(!this.worldObj.isRemote){
			if(this.curCharge == 0 && this.canCharge()){
				this.curCharge = getItemBurnTime(this.rechargeItemStacks[1]);
				consume = true;
			}else if(this.curCharge <= (this.maxCharge - getItemBurnTime(this.rechargeItemStacks[1]))){
					consume = true;

				this.curCharge += getItemBurnTime(this.rechargeItemStacks[1]);
			}
			if(this.curCharge > this.maxCharge){
				this.curCharge = this.maxCharge;
			}
		
				if(consume){
					flag1 = true;
					if(this.rechargeItemStacks[1] != null){
						this.rechargeItemStacks[1].stackSize--;
						if(this.rechargeItemStacks[1].stackSize == 0){
							this.rechargeItemStacks[1] = rechargeItemStacks[1].getItem().getContainerItem(this.rechargeItemStacks[1]);
						}
						/**if(consume){
							try{
							--this.rechargeItemStacks[1].stackSize;
							}
							catch(NullPointerException e){}
						}**/
					}
					
				}
			}
			
			if(this.isBurning() && this.canCharge()){
				this.curCharge--;
					this.chargeItem(rechargeItemStacks[0]);
					flag1=true;
				}
			
		
		
		if(flag !=this.curCharge > 0){
			flag1 = true;
			Recharge.updateBlockState(this.curCharge>0,this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}
		if(flag1){
			this.markDirty();
		}
	}
	
	private boolean canCharge(){
		if(this.rechargeItemStacks[0] == null)
		{
			return false;
		}else if(this.rechargeItemStacks[0].getItem() == LTItemInit.ironLaser || this.rechargeItemStacks[0].getItem() == LTItemInit.goldLaser || this.rechargeItemStacks[0].getItem() == LTItemInit.finallaser){
			LaserMain laser = (LaserMain)rechargeItemStacks[0].getItem();
			if(laser.getEnergy(rechargeItemStacks[0]).amount < laser.getMaxEnergy(rechargeItemStacks[0])){
			return true;			
			}
		}
			return false;
		
		
		
	}
	/**FIX AMOUNT BEING ADDED**/
	public void chargeItem(ItemStack x){
		if(this.canCharge()){
			if(x.stackTagCompound == null)
			{
				x.setTagCompound(new NBTTagCompound());
				LaserMain laser = (LaserMain)x.getItem();
				EnergyStack energy = new EnergyStack(laser, 0);
				laser.addEnergy(x, new EnergyStack(laser.getEnergy(x).getType(), laser.getEnergy(x).amount));
			}else{		
				LaserMain laser = (LaserMain)x.getItem();
				laser.addEnergy(x, new EnergyStack(laser.getEnergy(x).getType(), laser.getEnergy(x).amount));
			}
		}
	}
	//FIX METHOD TO ADD BURNTIMES
	public static int getItemBurnTime(ItemStack itemstack){
		if(itemstack == null){
			return 0;
		}else{
			Item item = itemstack.getItem();
			
			if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air){
				Block block = Block.getBlockFromItem(item);
			}
				if(item == Items.redstone) return 500;
			
		}
		return GameRegistry.getFuelValue(itemstack);
	}
	
	public static boolean isItemFuel(ItemStack itemstack){
		return getItemBurnTime(itemstack) > 0;		
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return var1 == 2 ? false : (var1 == 1 ? isItemFuel(var2): true);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return var1 == 0 ? slotsBottom: (var1 ==1 ? slotsTop: slotsSides);
	}

	@Override
	public boolean canInsertItem(int var1, ItemStack var2, int var3) {
		return this.isItemValidForSlot(var1, var2);
	}

	@Override
	public boolean canExtractItem(int var1, ItemStack var2, int var3) {
		return var3 !=0 || var1 !=1 || var2.getItem() == Items.bucket;
	}
	

}
