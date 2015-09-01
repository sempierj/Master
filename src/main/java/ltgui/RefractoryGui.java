package ltgui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ltcontainer.ContainerRefractory;
import ltentity.TileEntityRefractory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RefractoryGui  extends GuiContainer{

	private static final ResourceLocation RefractoryGuiTextures = new ResourceLocation("lasercoil:textures/GUI/Refractory.png");
	private TileEntityRefractory tileRefractory;
	
	public RefractoryGui(InventoryPlayer invplayer, TileEntityRefractory tile) {
		super(new ContainerRefractory(invplayer, tile));
		this.tileRefractory = tile;
	}

	protected void drawGuiContainerForegroundLayer(int var1, int var2){
		String string = this.tileRefractory.hasCustomInventoryName() ? this.tileRefractory.getInventoryName() : I18n.format(this.tileRefractory.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string) /2,6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory",new Object[0]), 8, this.ySize - 96+5, 4210752);
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F,  1.0F,  1.0F,  1.0F);
		this.mc.getTextureManager().bindTexture(RefractoryGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;
		
		i1 = this.tileRefractory.getCookProgressScaled(15);
		this.drawTexturedModalRect(k + 85, l+33, 176, 14, i1, 16);
		
	}

}