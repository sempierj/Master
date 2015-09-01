package ltgui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ltcontainer.ContainerInfuser;
import ltentity.TileEntityInfuser;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class infuserGUI  extends GuiContainer{

	private static final ResourceLocation infuserGuiTextures = new ResourceLocation("lasercoil:textures/GUI/infuser.png");
	private TileEntityInfuser tileInfuser;
	
	public infuserGUI(InventoryPlayer invplayer, TileEntityInfuser tile) {
		super(new ContainerInfuser(invplayer, tile));
		this.tileInfuser = tile;
	}

	protected void drawGuiContainerForegroundLayer(int var1, int var2){
		String string = this.tileInfuser.hasCustomInventoryName() ? this.tileInfuser.getInventoryName() : I18n.format(this.tileInfuser.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string) /2,6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory",new Object[0]), 8, this.ySize - 96+5, 4210752);
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F,  1.0F,  1.0F,  1.0F);
		this.mc.getTextureManager().bindTexture(infuserGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;
		
		if(this.tileInfuser.isBurning()){
			i1 = this.tileInfuser.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(k+34, l+37 + 12 - i1,176	, 12-i1	, 14, i1+2);
		}
		
		i1 = this.tileInfuser.getCookProgressScaled(24);
		this.drawTexturedModalRect(k + 79, l+34, 176, 14, i1+1, 16);
		
	}

}