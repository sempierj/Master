package ltinit;

import cpw.mods.fml.common.registry.GameRegistry;
import ltitems.LTBlocksInit;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeInit {
	public static void mainRegistry()
	{

		/**Add Smelting Recipes**/
		ItemStack copperLTStack = new ItemStack(LTItemInit.copperIngotLT, 1);
	    ItemStack alumLTStack = new ItemStack(LTItemInit.alumIngotLT,1);
	    GameRegistry.addSmelting(LTBlocksInit.copperOreLTBlock, copperLTStack, 0.1f);
	    GameRegistry.addSmelting(LTBlocksInit.alumOreLTBlock, alumLTStack, 0.1f);

	    /**Add Crafting Recipes**/
	    ItemStack ironTriggerLT = new ItemStack(LTItemInit.ironTrigger,1);
	    ItemStack lossBatteryLT = new ItemStack(LTItemInit.lossyBattery,1);
	    ItemStack ironBarrelLT = new ItemStack(LTItemInit.ironBarrel, 1);
	    ItemStack glassLense = new ItemStack(LTItemInit.glassLense,1);
	    ItemStack goldTriggerLT = new ItemStack(LTItemInit.goldTrigger,1);
	    ItemStack goldBatteryLT = new ItemStack(LTItemInit.goldBattery,1);
	    ItemStack goldBarrelLT = new ItemStack(LTItemInit.goldBarrel,1);
	    ItemStack diamondLense = new ItemStack(LTItemInit.diamondLense,1);
	    ItemStack dTriggerLT = new ItemStack(LTItemInit.finaltrigger,1);
	    ItemStack finalBatteryLT = new ItemStack(LTItemInit.finalbat,1);
	    ItemStack finalbarrelLT = new ItemStack(LTItemInit.finalbarrel,1);
	    ItemStack finallenseLT = new ItemStack(LTItemInit.finallense,1);
	    //Iron Trigger
	    GameRegistry.addRecipe(ironTriggerLT,
	    		"X  ",
	    		"X  ",
	    		" X ", 'X', (new ItemStack(Items.iron_ingot,1)));
	    GameRegistry.addRecipe(ironTriggerLT,
	    		"  X",
	    		"  X",
	    		" X ", 'X', (new ItemStack(Items.iron_ingot,1)));
	    //LossyBattery
	    GameRegistry.addRecipe(lossBatteryLT,
	    		"XYX",
	    		"YZY",
	    		"XYX", 'X', (new ItemStack(Blocks.wool,1)),
	    			   'Y', (new ItemStack(Items.iron_ingot,1)),
	    			   'Z', (new ItemStack(Items.redstone,1)));
	    //Iron Barrel
	    GameRegistry.addRecipe(ironBarrelLT,
	    		"XXX",
	    		"X X",
	    		"XXX", 'X', (new ItemStack(Items.iron_ingot,1)));
	    //Glass Lense
	    GameRegistry.addRecipe(glassLense,
	    		"XXX",
	    		"XXX",
	    		"XXX", 'X', (new ItemStack(Blocks.glass,1)));
	    //Gold Trigger
	    GameRegistry.addRecipe(goldTriggerLT,
	    		"X  ",
	    		"X  ",
	    		" X ", 'X', (new ItemStack(Items.gold_ingot,1)));
	    GameRegistry.addRecipe(goldTriggerLT,
	    		"  X",
	    		"  X",
	    		" X ", 'X', (new ItemStack(Items.gold_ingot,1)));
	    //Standard Battery
	    GameRegistry.addRecipe(goldBatteryLT,
	    		"XYX",
	    		"YZY",
	    		"XYX", 'X', (new ItemStack(Items.gold_ingot,1)),
	    			   'Y', (new ItemStack(LTItemInit.lossyBattery,1)),
	    			   'Z', (new ItemStack(Blocks.redstone_block,1)));
	    //Charged Barrel
	    GameRegistry.addRecipe(goldBarrelLT,
	    		"YXY",
	    		"X X",
	    		"YXY", 'X', (new ItemStack(LTItemInit.alumIngotLT,1)),
	    				'Y', (new ItemStack(Items.gold_ingot,1)));
	    //Diamond Lense
	    GameRegistry.addRecipe(diamondLense,
	    		"XXX",
	    		"XYX",
	    		"XXX", 'X', (new ItemStack(Blocks.glass,1)),
	    			   'Y', (new ItemStack(Items.diamond,1)));
	    //Diamond Trigger
	    GameRegistry.addRecipe(dTriggerLT,
	    		"X  ",
	    		"X  ",
	    		" X ", 'X', (new ItemStack(Items.diamond,1)));
	    GameRegistry.addRecipe(dTriggerLT,
	    		"  X",
	    		"  X",
	    		" X ", 'X', (new ItemStack(Items.diamond,1)));
	    //Tesserite Battery
	    GameRegistry.addRecipe(finalBatteryLT,
	    		"XYX",
	    		"YZY",
	    		"XYX", 'X', (new ItemStack(LTItemInit.infGoldLT,1)),
	    			   'Y', (new ItemStack(LTItemInit.goldBattery,1)),
	    			   'Z', (new ItemStack(LTItemInit.tess,1)));
	    //Diamond Barrel
	    GameRegistry.addRecipe(finalbarrelLT,
	    		"XYX",
	    		"Y Y",
	    		"XYX", 'X', (new ItemStack(LTItemInit.alumShieldLT,1)),
	    			   'Y', (new ItemStack(Items.diamond,1)));
	    //Tesserite Lense
	    GameRegistry.addRecipe(finallenseLT,
	    		"XYX",
	    		"YZY",
	    		"XYX", 'X', (new ItemStack(Items.gold_ingot,1)),
	    			   'Y', (new ItemStack(LTItemInit.infGoldLT,1)),
	    			   'Z', (new ItemStack(LTItemInit.tess,1)));

	}
}