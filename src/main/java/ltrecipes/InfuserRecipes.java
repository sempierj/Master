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

public class InfuserRecipes
{
    private static final InfuserRecipes infusingBase = new InfuserRecipes();
    /** The list of smelting results. */
    private static Map infusingList = new HashMap();
    private static ItemStack returnstack;
    private static final String __OBFID = "CL_00000085";

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static InfuserRecipes infusing()
    {
        return infusingBase;
    }

    private InfuserRecipes()
    {
    
    }

    public void addInfusing(int x, Item i, Item l, Item k, ItemStack itemstack)
    {
        infusingList.put(tripleItem(i,l,k), itemstack);
    }
    

    public static ItemStack getInfusingResult(Item i, Item j, Item k)
    {
    	if(i == LTItemInit.alumIngotLT && j == LTItemInit.alumIngotLT && k == Items.diamond){
    		returnstack = new ItemStack(LTItemInit.alumShieldLT);
        return returnstack;
    	}
    	else if(i == Items.gold_ingot && j == Items.redstone && k == Items.redstone){
    		returnstack = new ItemStack(LTItemInit.infGoldLT);
    		return returnstack;
    	}
    	
    	return null;
    }

    public Map getInfusingList()
    {
        return infusingList;
    }
    
    private Item [] tripleItem(Item i , Item l, Item k)
    {
    	return new Item[] {i, l, k};
    }
    

}