package ltentity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBlast extends EntityThrowable{
	
	private double explosionRadius = 0.3F;
	private float damage;
	public EntityBlast(World par1World)
    {
        super(par1World);
    }
    public EntityBlast(World par1World, EntityLivingBase par2EntityLivingBase)
    {
        super(par1World, par2EntityLivingBase);
    }
    public EntityBlast(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }
    public EntityBlast(World world, EntityLivingBase player, double x){
    	super(world,player);
    	damage = (float)x;
    }
    @Override
    protected float getGravityVelocity()
    {
        return (float) 0.001;
    }
    @Override
    protected void onImpact(MovingObjectPosition mop){
    	if(mop.entityHit != null){
    		mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage);
    	}
    	this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, true);
		this.setDead();
    }
}

