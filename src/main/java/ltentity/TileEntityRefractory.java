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
import ltitems.LTItems;
import ltitems.LaserMain;
import ltitems.Refractory;
import ltrecipes.RefractoryRecipes;

public class TileEntityRefractory extends TileEntity implements ISidedInventory{

	private static final int[] slotsTop = new int[]{0};
	private static final int[] slotsBottom = new int[]{2,1};
	private static final int[] slotsSides = new int[]{1};
	
	private ItemStack[] refractoryItemStacks = new ItemStack[6];
	public int refractoryBurnTime;
	public int currentBurnTime;
	public int refractoryCookTime;
	private String refractoryName;

	public void furnaceName(String string){
		this.refractoryName = string;
	}
	
	
	@Override
	public int getSizeInventory() {
		
		return this.refractoryItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return this.refractoryItemStacks[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		if(this.refractoryItemStacks[var1] != null){
			ItemStack itemstack;
			if(this.refractoryItemStacks[var1].stackSize <= var2){
				itemstack = this.refractoryItemStacks[var1];
				this.refractoryItemStacks[var1] = null;
				return itemstack;
			}else{
				itemstack = this.refractoryItemStacks[var1].splitStack(var2);
				
				if(this.refractoryItemStacks[var1].stackSize == 0){
					this.refractoryItemStacks[var1] = null;
				}
				return itemstack;
			}
		}else{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		if(this.refractoryItemStacks[var1] !=null){
			ItemStack itemstack = this.refractoryItemStacks[var1];
			this.refractoryItemStacks[var1] = null;
			return itemstack;
		}
		else{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		this.refractoryItemStacks[var1] = var2;
		
		if(var2 != null && var2.stackSize > this.getInventoryStackLimit()){
			var2.stackSize = this.getInventoryStackLimit();			
		}
		
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.refractoryName : "Refractory";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.refractoryName != null && this.refractoryName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		
		return 64;
	}

	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.refractoryItemStacks = new ItemStack[this.getSizeInventory()];
		
		for(int i = 0; i < tagList.tagCount(); ++i){
			NBTTagCompound tagCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tagCompound1.getByte("Slot");
			
			if(byte0 >= 0 && byte0 < this.refractoryItemStacks.length){
				this.refractoryItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tagCompound1);
			}
		}
		
		this.refractoryBurnTime = tagCompound.getShort("BurnTime");
		this.refractoryCookTime = tagCompound.getShort("CookTime");
		this.currentBurnTime = getItemBurnTime(this.refractoryItemStacks[1]);
		
		if(tagCompound.hasKey("CustomName" , 8)){
			this.refractoryName = tagCompound.getString("CustomName");
		}
	}
	
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		tagCompound.setShort("BurnTime", (short)this.refractoryBurnTime);
		tagCompound.setShort("CookTime", (short)this.refractoryBurnTime);
		NBTTagList tagList = new NBTTagList();
		
		for(int i = 0; i < this.refractoryItemStacks.length; ++i){
			if(this.refractoryItemStacks[i] != null){
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte)i);
				this.refractoryItemStacks[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}
		
		tagCompound.setTag("Items", tagList);
		if(this.hasCustomInventoryName()){
			tagCompound.setString("CustomName", this.refractoryName);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1){
		return this.refractoryCookTime * par1 / 500;
	}
	
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1){
		if(this.currentBurnTime ==0){
			this.currentBurnTime = 200;
		}
		return this.refractoryBurnTime * par1 / this.currentBurnTime;
	}
	
	public boolean isBurning(){
		return this.refractoryBurnTime > 0;
	}
	
	public void updateEntity(){
		boolean flag = this.refractoryBurnTime > 0;
		boolean flag1 = false;
		
		if(this.refractoryBurnTime > 0){
			--this.refractoryBurnTime;
		}
		
		if(!this.worldObj.isRemote){
			if(this.refractoryBurnTime == 0 && this.canSmelt()){
				this.currentBurnTime = this.refractoryBurnTime = getItemBurnTime(this.refractoryItemStacks[1]);
				
				if(this.refractoryBurnTime > 0){
					flag1 = true;
					if(this.refractoryItemStacks[1] != null){
						--this.refractoryItemStacks[1].stackSize;
						
						if(this.refractoryItemStacks[1].stackSize == 0){
							this.refractoryItemStacks[1] = refractoryItemStacks[1].getItem().getContainerItem(this.refractoryItemStacks[1]);
						}
					}
					
				}
			}
			
			if(this.isBurning() && this.canSmelt()){
				++this.refractoryCookTime;
				if(this.refractoryCookTime == 500){
					this.refractoryCookTime = 0;
					this.smeltItem();
					flag1=true;
				}
			}else{
				this.refractoryCookTime = 0;
			}
		}
		
		if(flag !=this.refractoryBurnTime > 0){
			flag1 = true;
			Refractory.updateBlockState(this.refractoryBurnTime>0,this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}
		if(flag1){
			this.markDirty();
		}
	}
	
	private boolean canSmelt(){
		if(this.refractoryItemStacks[0] == null || this.refractoryItemStacks[3] == null || this.refractoryItemStacks[4] == null || this.refractoryItemStacks[5] == null)
		{
			return false;
		}else{
			ItemStack itemstack = RefractoryRecipes.getResult(refractoryItemStacks[0].getItem(), refractoryItemStacks[3].getItem(), refractoryItemStacks[4].getItem(), refractoryItemStacks[5].getItem());
			if(itemstack == null){
				return false;
			}
			if(this.refractoryItemStacks[2] == null) return true;
			if(!this.refractoryItemStacks[2].isItemEqual(itemstack)) return false;
			int result = refractoryItemStacks[2].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.refractoryItemStacks[2].getMaxStackSize();
		}
	}
	
	public void smeltItem(){
		if(this.canSmelt()){
			ItemStack itemstack = RefractoryRecipes.crafting().getResult(refractoryItemStacks[0].getItem(), refractoryItemStacks[3].getItem(), refractoryItemStacks[4].getItem(), refractoryItemStacks[5].getItem());
			if(this.refractoryItemStacks[2] == null){
				this.refractoryItemStacks[2] = itemstack.copy();
				LaserMain laser = (LaserMain) itemstack.getItem();
				EnergyStack energy = new EnergyStack(laser,0);
			}
	
			
			
			for(int i = 0; i<6; i++){
				if(i != 1 && i!=2){
					if(refractoryItemStacks[i].stackSize<=0){
						refractoryItemStacks[i] = new ItemStack(refractoryItemStacks[i].getItem().setFull3D());
					}
					else{
						refractoryItemStacks[i].stackSize--;
					}
					if(refractoryItemStacks[i].stackSize <=0){
						refractoryItemStacks[i] = null;
					}
				}
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
