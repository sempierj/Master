package ltentity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityMine extends EntityThrowable{
	
	private double explosionRadius = 0.1F;
	private final float damage = 0;
	public EntityMine(World par1World)
    {
        super(par1World);
    }
    public EntityMine(World par1World, EntityLivingBase par2EntityLivingBase)
    {
        super(par1World, par2EntityLivingBase);
    }
    public EntityMine(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }
    public EntityMine(World world, EntityLivingBase player, double x){
    	super(world,player);
    }
    @Override
    protected float getGravityVelocity()
    {
        return (float) 0.001;
    }
    @Override
    protected void onImpact(MovingObjectPosition mop){
    	if(mop.entityHit != null){
    	}
    	else
    	{
    		int x = mop.blockX;
        	int y = mop.blockY;
        	int z = mop.blockZ;
        	
        	Random rand = new Random();
        	Item drop;
        	Block block = worldObj.getBlock(x, y, z);
        	if(block != null)
        	{
        		if(!block.equals(Blocks.bedrock))
        		{
        		worldObj.func_147480_a(x, y, z, true);
        		}
        	}
    	}

		this.setDead();
    }

}

