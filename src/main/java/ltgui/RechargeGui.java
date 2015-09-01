package ltgui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ltcontainer.ContainerRecharge;
import ltentity.TileEntityRecharge;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RechargeGui  extends GuiContainer{

	private static final ResourceLocation rechargeGuiTextures = new ResourceLocation("lasercoil:textures/GUI/recharge.png");
	private TileEntityRecharge tileRecharge;
	
	public RechargeGui(InventoryPlayer invplayer, TileEntityRecharge tile) {
		super(new ContainerRecharge(invplayer, tile));
		this.tileRecharge = tile;
	}

	protected void drawGuiContainerForegroundLayer(int var1, int var2){
		String string = this.tileRecharge.hasCustomInventoryName() ? this.tileRecharge.getInventoryName() : I18n.format(this.tileRecharge.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string) /2,6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory",new Object[0]), 8, this.ySize - 96+5, 4210752);
		String charge = this.tileRecharge.curCharge + "";
		this.fontRendererObj.drawString(charge, 89,29,4210752);
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F,  1.0F,  1.0F,  1.0F);
		this.mc.getTextureManager().bindTexture(rechargeGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;
		
		i1 = this.tileRecharge.getChargeScaled(64);
		if(i1 != 0){
		this.drawTexturedModalRect(k + 72, l+39, 176, 0, i1+1, 16);
		}
		
	}

}