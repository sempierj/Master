package ltgui;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ltinit.LTItemInit;
import ltitems.LaserMain;
import ltitems.LaserMain.LaserMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class LTGuiOverlay extends Gui{
	private Minecraft mc;
	
	public LTGuiOverlay(Minecraft mc){
		super();
		this.mc=mc;
	}
	
	private static final int ICON_SIZE = 18;
	private static final int ICON_SPACIG = ICON_SIZE + 2;
	private static final int U_OFFSET = 0;
	private static final int V_OFFSET = 198;
	private static final int PER_ROW = 8;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent e)
	{
		if(e.isCancelable() || e.type != ElementType.EXPERIENCE)
		{
			return;
		}
		try{
		ItemStack item = this.mc.thePlayer.getCurrentEquippedItem();
		
		if(item.getItem() instanceof LaserMain)
		{
			LaserMain laser = (LaserMain) item.getItem();
		ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		int xPos = 2;
		int yPos = (int) (res.getScaledHeight() * .75);
		String type = "";
		String modeString = "";
		if(item.getItem().equals(LTItemInit.ironLaser))
		{
			type = "Iron Laser";
		}
		else if (item.getItem().equals(LTItemInit.goldLaser))
		{
			type = "Gold Laser";
		}
		else if(item.getItem().equals(LTItemInit.finallaser))
		{
			type = "Diamond Laser";
		}
		int energy = laser.getStored(item);
		int max = laser.getMaxEnergy(item);
		double dmg = laser.getDmg();
		LaserMode mode = laser.getMode(item);
		int mod = 1;
		if(mode == LaserMode.STANDARD)
		{
			modeString = "Standard";
		}
		else if(mode == LaserMode.BURST)
		{
			modeString = "BURST";
			mod = 5;
			dmg *=3;
		}
		else
		{
			modeString = "MINING";
			mod = 2;
			dmg /=2;
		}
		int cost = (50*mod) / laser.energyMod();
		FontRenderer render = mc.fontRenderer;
		render.drawString(type + ": " + energy + "/" + max, xPos, yPos, 0xFFFFFF, true);
		render.drawString(modeString + ": " + dmg + " DPS   " + cost + " EPS" , xPos, yPos+10, 0xFFFFFF, true);
		}
		}catch(NullPointerException event){}
	}
}
