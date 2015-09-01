package ltinit;

import cpw.mods.fml.common.registry.GameRegistry;
import lasercoil.Main;
import ltitems.LTItems;
import ltitems.LaserMain;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class LTItemInit {
	
	public static void mainRegistry(){
		initializeItem();
		registerItem();
	}
	public static Item tess;
	public static Item alumIngotLT;
	public static Item alumShieldLT;
	public static Item infGoldLT;
	public static Item ironTrigger;
	public static Item lossyBattery;
	public static Item ironBarrel;
	public static Item glassLense;
	public static Item copperIngotLT;
	public static Item ironLaser;
	public static Item blast;
	public static Item goldTrigger;
	public static Item goldBarrel;
	public static Item diamondLense;
	public static Item goldBattery;
	public static Item goldLaser;
	public static Item blast2;
	public static Item blast3;
	public static Item finalbarrel;
	public static Item finallense;
	public static Item finalbat;
	public static Item finaltrigger;
	public static Item finallaser;
	
	public static ToolMaterial ironL = EnumHelper.addToolMaterial("ironL", 1, 10000, 4.0F, 0, 5);
	
	public static void initializeItem(){
	    copperIngotLT = new LTItems().setUnlocalizedName("copperIngotLT").setTextureName("lasercoil:copperingot");
	    tess = new LTItems().setTextureName("lasercoil:tesserite").setUnlocalizedName("tess"); 
	    alumIngotLT = new LTItems().setUnlocalizedName("alumIngotLT").setTextureName("lasercoil:alumingot");    
	    alumShieldLT = new LTItems().setUnlocalizedName("alumShieldLT").setTextureName("lasercoil:alumShieldLT");
	    infGoldLT = new LTItems().setUnlocalizedName("infGoldLT").setTextureName("lasercoil:infGoldLT");    
	    ironTrigger = new LTItems().setUnlocalizedName("ironTrigger").setTextureName("lasercoil:irontrigger");
	    lossyBattery = new LTItems().setUnlocalizedName("lossyBattery").setTextureName("lasercoil:lossbat"); 
	    ironBarrel = new LTItems().setUnlocalizedName("ironBarrel").setTextureName("lasercoil:ironBarrel");  
	    glassLense = new LTItems().setUnlocalizedName("glassLense").setTextureName("lasercoil:glasslense");
	    ironLaser = new LaserMain().setUnlocalizedName("ironLaser").setTextureName("lasercoil:baselaser").setCreativeTab(Main.tabName);
	    blast = new LTItems().setUnlocalizedName("blast").setTextureName("lasercoil:blast");
	    goldTrigger = new LTItems().setUnlocalizedName("goldTrigger").setTextureName("lasercoil:goldtrigger");
	    goldBarrel = new LTItems().setUnlocalizedName("goldBarrel").setTextureName("lasercoil:goldbarrel");
	    diamondLense = new LTItems().setUnlocalizedName("diamondLense").setTextureName("lasercoil:diamondlense");
	    goldBattery = new LTItems().setUnlocalizedName("goldBattery").setTextureName("lasercoil:goldbat");
	    goldLaser = new LaserMain().setUnlocalizedName("goldLaser").setTextureName("lasercoil:goldlaser");
	    blast2 = new LTItems().setUnlocalizedName("blast2").setTextureName("lasercoil:blast2");
	    blast3 = new LTItems().setUnlocalizedName("blast3").setTextureName("lasercoil:blast3");
	    finalbarrel = new LTItems().setUnlocalizedName("finalbarrel").setTextureName("lasercoil:finalbarrel");
	    finallense = new LTItems().setUnlocalizedName("finallense").setTextureName("lasercoil:finallense");
	    finalbat = new LTItems().setUnlocalizedName("finalbat").setTextureName("lasercoil:finalbattery3");
	    finaltrigger = new LTItems().setUnlocalizedName("finaltrigger").setTextureName("lasercoil:diamondtrigger");
	    finallaser = new LaserMain().setUnlocalizedName("finalLaser").setTextureName("lasercoil:finallaser");
	}
	
	public static void registerItem(){
		   GameRegistry.registerItem(copperIngotLT, "copperIngotLT");
		   GameRegistry.registerItem(tess, "tesserite");
		   GameRegistry.registerItem(alumIngotLT, "alumIngotLT");
		    GameRegistry.registerItem(alumShieldLT,  "alumShieldLT");
		    GameRegistry.registerItem(infGoldLT,  "infGoldLT");
		    GameRegistry.registerItem(ironTrigger, "ironTrigger");
		    GameRegistry.registerItem(lossyBattery, "lossyBattery");
		    GameRegistry.registerItem(ironBarrel, "ironBarrel");
		    GameRegistry.registerItem(glassLense, "glassLense");
		    GameRegistry.registerItem(ironLaser, "ironLaser");
		    GameRegistry.registerItem(blast, "blast");
		    GameRegistry.registerItem(goldTrigger, "goldTrigger");
		    GameRegistry.registerItem(goldBarrel, "goldBarrel");
		    GameRegistry.registerItem(diamondLense, "diamondLense");
		    GameRegistry.registerItem(goldBattery, "goldBattery");
		    GameRegistry.registerItem(goldLaser, "goldLaser");
		    GameRegistry.registerItem(blast2,"blast2");
		    GameRegistry.registerItem(blast3, "blast3");
		    GameRegistry.registerItem(finalbarrel, "finalbarrel");
		    GameRegistry.registerItem(finallense, "finallense");
		    GameRegistry.registerItem(finalbat, "finalbat");
		    GameRegistry.registerItem(finaltrigger, "finaltrigger");
		    GameRegistry.registerItem(finallaser, "finallaser");
		    RecipeInit.mainRegistry();
			   
	}
}
