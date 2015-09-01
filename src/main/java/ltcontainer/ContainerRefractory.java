package ltcontainer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ltentity.TileEntityRefractory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ContainerRefractory extends Container{

	private TileEntityRefractory tileRefractory;
	private int lastCookTime;
	private int lastBurntime;
	private int lastItemBurnTime;
	
	public ContainerRefractory(InventoryPlayer player, TileEntityRefractory tileEntityRefractory){
		this.tileRefractory = tileEntityRefractory;
		this.addSlotToContainer(new Slot(tileEntityRefractory, 0, 8, 17));
		this.addSlotToContainer(new Slot(tileEntityRefractory, 1, 32, 62));
		this.addSlotToContainer(new SlotRefractory(player.player, tileEntityRefractory, 2, 116, 35));
		this.addSlotToContainer(new Slot(tileEntityRefractory, 3, 32, 17));
		this.addSlotToContainer(new Slot(tileEntityRefractory, 4, 56, 17));
		this.addSlotToContainer(new Slot(tileEntityRefractory, 5, 44, 38));
		
		int i;
		
		for(i = 0; i < 3; ++i){
			for(int j = 0; j < 9; ++j){
				this.addSlotToContainer(new Slot(player, j+i*9 + 9, 8+j * 18, 84 + i* 18));
			}
		}
		
		for(i = 0; i < 9; ++i){
			this.addSlotToContainer(new Slot(player, i, 8+i*18, 142));
		}
	}
	
	public void addCraftingToCrafters(ICrafting craft){
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.tileRefractory.refractoryCookTime);
		craft.sendProgressBarUpdate(this, 1, this.tileRefractory.refractoryBurnTime);
		craft.sendProgressBarUpdate(this, 2, this.tileRefractory.currentBurnTime);		
	}
	
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); ++i){
			ICrafting craft = (ICrafting) this.crafters.get(i);
			
			if(this.lastCookTime != this.tileRefractory.refractoryCookTime){
				craft.sendProgressBarUpdate(this, 0, this.tileRefractory.refractoryCookTime);
			}
			if(this.lastBurntime != this.tileRefractory.refractoryBurnTime){
				craft.sendProgressBarUpdate(this, 1, this.tileRefractory.refractoryBurnTime);
			}
			if(this.lastItemBurnTime != this.tileRefractory.currentBurnTime){
				craft.sendProgressBarUpdate(this, 2, this.tileRefractory.currentBurnTime);
			}
		}
		this.lastBurntime = this.tileRefractory.refractoryBurnTime;
		this.lastCookTime = this.tileRefractory.refractoryCookTime;
		this.lastItemBurnTime = this.tileRefractory.currentBurnTime;
				
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2){
		if(par1 == 0){
			this.tileRefractory.refractoryCookTime = par2;
		}
		if(par1 == 1){
			this.tileRefractory.refractoryBurnTime = par2;
		}
		if(par1 == 2){
			this.tileRefractory.currentBurnTime = par2;
		}
	}
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileRefractory.isUseableByPlayer(player);
	}
	
	public ItemStack transferStackInSlot(EntityPlayer player, int var2){
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(var2);
		if(slot != null && slot.getHasStack()){
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if(var2 ==2){
				if(!this.mergeItemStack(itemstack1, 3, 39, true)){
					return null;
				}
			slot.onSlotChange(itemstack1,  itemstack);
			}else if(var2 !=1 && var2 !=0){
				if(FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null){
					if(!this.mergeItemStack(itemstack1, 0, 1, false)){
						return null;
					}
				}else if(TileEntityRefractory.isItemFuel(itemstack1)){
					if(!this.mergeItemStack(itemstack1,1,2,false)){
						return null;
					}
				}else if(var2 >= 3 && var2 < 30){
					if(!this.mergeItemStack(itemstack1, 30, 39, false)){
						return null;
					}
				}else if(var2 >= 30 && var2 < 39 &&!this.mergeItemStack(itemstack1, 3, 30, false)){
					return null;
				}
			}else if(!this.mergeItemStack(itemstack1, 3, 39, false)){
				return null;
			}
			if(itemstack1.stackSize == 0){
				slot.putStack((ItemStack)null);
			}else{
				slot.onSlotChanged();
			}
			if(itemstack1.stackSize==itemstack.stackSize){
				return null;
			}
			slot.onPickupFromSlot(player, itemstack1);
		}
		return itemstack;
	}
	

}
