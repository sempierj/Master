package ltblocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.Random;

import ltinit.LTItemInit;
import net.minecraft.block.Block;

public class LTOres2 extends Block{


	public LTOres2(Material material) 
    {
            super(material);
            setCreativeTab(CreativeTabs.tabBlock);
            setBlockTextureName("lasercoil:copper");
    
    
    }
	
    public Item getItemDropped(int metadata, Random random, int fortune) 
    {
    
        return LTItemInit.tess;
    	
    }
}
    
 