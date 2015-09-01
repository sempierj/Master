package ltitems;

import cpw.mods.fml.common.registry.GameRegistry;
import lasercoil.Main;
import ltblocks.LTOres;
import ltblocks.LTOres2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LTBlocksInit {

		public static void mainRegistry(){
			initializeBlock();
			registerBlock();
		}
		
		public static Block infuserOff;
		public static Block infuserOn;
		public static Block refractoryOff;
		public static Block refractoryOn;
		public static Block copperOreLTBlock;
		public static Block alumOreLTBlock;
		public static Block tessOreLTBlock;
		public static Block rechargeOff;
		public static Block rechargeOn;
		
		public static void initializeBlock(){
			infuserOff = new Infuser(false).setBlockName("InfuserOff").setCreativeTab(Main.tabName);
		    infuserOn = new Infuser(true).setBlockName("InfuserOn");
		    refractoryOff = new Refractory(false).setBlockName("RefractoryOff").setCreativeTab(Main.tabName);
		    refractoryOn = new Refractory(true).setBlockName("RefractoryOn");
		    rechargeOff = new Recharge(false).setBlockName("RechargeOff").setCreativeTab(Main.tabName);
		    rechargeOn = new Recharge(true).setBlockName("RechargeOn");
		    copperOreLTBlock = new LTOres(Material.iron)
		    		.setHardness(3.0F)
		    		.setStepSound(Block.soundTypeStone)
		    		.setBlockName("copperOreLTBlock")
		    		.setCreativeTab(Main.tabName)
		   			.setBlockTextureName("lasercoil:copper");
		    alumOreLTBlock = new LTOres(Material.iron)
		    		.setHardness(3.0F)
		    		.setStepSound(Block.soundTypeStone)
		    		.setBlockName("alumOreLTBlock")
		    		.setCreativeTab(Main.tabName)
		   			.setBlockTextureName("lasercoil:aluminum");	
		    tessOreLTBlock = new LTOres2(Material.iron)
		    		.setHardness(4.0F)
		    		.setStepSound(Block.soundTypeStone)
		    		.setBlockName("tessOreLTBlock")
		    		.setCreativeTab(Main.tabName)
		   			.setBlockTextureName("lasercoil:tesserite");	   
		}
		
		public static void registerBlock(){
			 GameRegistry.registerBlock(infuserOff, "InfuserOff");
			    GameRegistry.registerBlock(infuserOn, "InfuserOn");
			    GameRegistry.registerBlock(copperOreLTBlock, "copperOreLTBlock");
			    GameRegistry.registerBlock(alumOreLTBlock, "alumOreLTBlock");
			    GameRegistry.registerBlock(tessOreLTBlock, "tessOreLTBlock");
			    GameRegistry.registerBlock(refractoryOff, "refractoryOff");
			    GameRegistry.registerBlock(refractoryOn, "refractoryOn");
			    GameRegistry.registerBlock(rechargeOff, "rechargeOff");
			    GameRegistry.registerBlock(rechargeOn,"rechargeOn");
			    
		}
}
