package ltitems;

import lasercoil.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class LTItems extends Item{
	
	public LTItems()
	{
	// Constructor Configuration
	setMaxStackSize(64);
	setCreativeTab(Main.tabName);
	}
}
