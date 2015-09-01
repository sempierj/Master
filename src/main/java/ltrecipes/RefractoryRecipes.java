package ltrecipes;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ltinit.LTItemInit;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;

public class RefractoryRecipes
{
    private static final RefractoryRecipes refractorBase = new RefractoryRecipes();
    /** The list of smelting results. */
    private static ItemStack returnstack;
    private static final String __OBFID = "CL_00000085";

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static RefractoryRecipes crafting()
    {
        return refractorBase;
    }

    private RefractoryRecipes()
    {
    
    }

    public static ItemStack getResult(Item i, Item j, Item k, Item m)
    {
    	if(j.equals(LTItemInit.glassLense) && k.equals(LTItemInit.lossyBattery) && i.equals(LTItemInit.ironBarrel) && m.equals(LTItemInit.ironTrigger))
    	{
    		returnstack = new ItemStack(LTItemInit.ironLaser);
        return returnstack;
    	}
    	else if(j.equals(LTItemInit.diamondLense) && k.equals(LTItemInit.goldBattery) && i.equals(LTItemInit.goldBarrel) && m.equals(LTItemInit.goldTrigger))
    	{
    		returnstack = new ItemStack(LTItemInit.goldLaser);
        return returnstack;
    	}
    	else if(j.equals(LTItemInit.finallense) && k.equals(LTItemInit.finalbat) && i.equals(LTItemInit.finalbarrel) && m.equals(LTItemInit.finaltrigger))
    	{
    		returnstack = new ItemStack(LTItemInit.finallaser);
    	return returnstack;
    	}
    	return null;
    }
}