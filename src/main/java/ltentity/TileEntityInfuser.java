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
import ltitems.Infuser;
import ltitems.LTItems;
import ltrecipes.InfuserRecipes;

public class TileEntityInfuser extends TileEntity implements ISidedInventory{

	private static final int[] slotsTop = new int[]{0};
	private static final int[] slotsBottom = new int[]{2,1};
	private static final int[] slotsSides = new int[]{1};
	
	private ItemStack[] infuserItemStacks = new ItemStack[5];
	public int infuserBurnTime;
	public int currentBurnTime;
	public int infuserCookTime;
	private String infuserName;

	public void furnaceName(String string){
		this.infuserName = string;
	}
	
	
	@Override
	public int getSizeInventory() {
		
		return this.infuserItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return this.infuserItemStacks[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		if(this.infuserItemStacks[var1] != null){
			ItemStack itemstack;
			if(this.infuserItemStacks[var1].stackSize <= var2){
				itemstack = this.infuserItemStacks[var1];
				this.infuserItemStacks[var1] = null;
				return itemstack;
			}else{
				itemstack = this.infuserItemStacks[var1].splitStack(var2);
				
				if(this.infuserItemStacks[var1].stackSize == 0){
					this.infuserItemStacks[var1] = null;
				}
				return itemstack;
			}
		}else{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		if(this.infuserItemStacks[var1] !=null){
			ItemStack itemstack = this.infuserItemStacks[var1];
			this.infuserItemStacks[var1] = null;
			return itemstack;
		}
		else{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		this.infuserItemStacks[var1] = var2;
		
		if(var2 != null && var2.stackSize > this.getInventoryStackLimit()){
			var2.stackSize = this.getInventoryStackLimit();			
		}
		
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.infuserName : "Infuser";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.infuserName != null && this.infuserName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		
		return 64;
	}

	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.infuserItemStacks = new ItemStack[this.getSizeInventory()];
		
		for(int i = 0; i < tagList.tagCount(); ++i){
			NBTTagCompound tagCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tagCompound1.getByte("Slot");
			
			if(byte0 >= 0 && byte0 < this.infuserItemStacks.length){
				this.infuserItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tagCompound1);
			}
		}
		
		this.infuserBurnTime = tagCompound.getShort("BurnTime");
		this.infuserCookTime = tagCompound.getShort("CookTime");
		this.currentBurnTime = getItemBurnTime(this.infuserItemStacks[1]);
		
		if(tagCompound.hasKey("CustomName" , 8)){
			this.infuserName = tagCompound.getString("CustomName");
		}
	}
	
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		tagCompound.setShort("BurnTime", (short)this.infuserBurnTime);
		tagCompound.setShort("CookTime", (short)this.infuserBurnTime);
		NBTTagList tagList = new NBTTagList();
		
		for(int i = 0; i < this.infuserItemStacks.length; ++i){
			if(this.infuserItemStacks[i] != null){
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte)i);
				this.infuserItemStacks[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}
		
		tagCompound.setTag("Items", tagList);
		if(this.hasCustomInventoryName()){
			tagCompound.setString("CustomName", this.infuserName);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1){
		return this.infuserCookTime * par1 / 500;
	}
	
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1){
		if(this.currentBurnTime ==0){
			this.currentBurnTime = 200;
		}
		return this.infuserBurnTime * par1 / this.currentBurnTime;
	}
	
	public boolean isBurning(){
		return this.infuserBurnTime > 0;
	}
	
	public void updateEntity(){
		boolean flag = this.infuserBurnTime > 0;
		boolean flag1 = false;
		
		if(this.infuserBurnTime > 0){
			--this.infuserBurnTime;
		}
		
		if(!this.worldObj.isRemote){
			if(this.infuserBurnTime == 0 && this.canSmelt()){
				this.currentBurnTime = this.infuserBurnTime = getItemBurnTime(this.infuserItemStacks[1]);
				
				if(this.infuserBurnTime > 0){
					flag1 = true;
					if(this.infuserItemStacks[1] != null){
						--this.infuserItemStacks[1].stackSize;
						
						if(this.infuserItemStacks[1].stackSize == 0){
							this.infuserItemStacks[1] = infuserItemStacks[1].getItem().getContainerItem(this.infuserItemStacks[1]);
						}
					}
					
				}
			}
			
			if(this.isBurning() && this.canSmelt()){
				++this.infuserCookTime;
				if(this.infuserCookTime == 500){
					this.infuserCookTime = 0;
					this.smeltItem();
					flag1=true;
				}
			}else{
				this.infuserCookTime = 0;
			}
		}
		
		if(flag !=this.infuserBurnTime > 0){
			flag1 = true;
			Infuser.updateBlockState(this.infuserBurnTime>0,this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}
		if(flag1){
			this.markDirty();
		}
	}
	
	private boolean canSmelt(){
		if(this.infuserItemStacks[0] == null || this.infuserItemStacks[3] == null || this.infuserItemStacks[4] == null)
		{
			return false;
		}else{
			ItemStack itemstack = InfuserRecipes.getInfusingResult(infuserItemStacks[0].getItem(), infuserItemStacks[3].getItem(), infuserItemStacks[4].getItem());
			if(itemstack == null){
				return false;
			}
			if(this.infuserItemStacks[2] == null) return true;
			if(!this.infuserItemStacks[2].isItemEqual(itemstack)) return false;
			int result = infuserItemStacks[2].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.infuserItemStacks[2].getMaxStackSize();
		}
	}
	
	public void smeltItem(){
		if(this.canSmelt()){
			ItemStack itemstack = InfuserRecipes.infusing().getInfusingResult(infuserItemStacks[0].getItem(), infuserItemStacks[3].getItem(), infuserItemStacks[4].getItem());
			if(this.infuserItemStacks[2] == null){
				this.infuserItemStacks[2] = itemstack.copy();
			}
			else if(this.infuserItemStacks[2].getItem() == itemstack.getItem()){
				this.infuserItemStacks[2].stackSize += itemstack.stackSize;
			}
			
			for(int i = 0; i<5; i++){
				if(i != 1 && i!=2){
					if(infuserItemStacks[i].stackSize<=0){
						infuserItemStacks[i] = new ItemStack(infuserItemStacks[i].getItem().setFull3D());
					}
					else{
						infuserItemStacks[i].stackSize--;
					}
					if(infuserItemStacks[i].stackSize <=0){
						infuserItemStacks[i] = null;
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
