package sonar.calculator.mod.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBabyGrenade extends EntityThrowable
{
	   public EntityBabyGrenade(World world)
	    {
	        super(world);
	    }

	    public EntityBabyGrenade(World world, EntityLivingBase player)
	    {
	        super(world, player);
	    }

	    public EntityBabyGrenade(World world, double x, double y, double z)
	    {
	        super(world, x, y, z);
	    }
  
  @Override
protected void onImpact(MovingObjectPosition var1) {
      if (!this.worldObj.isRemote) {
        setDead();
        this.worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, 1.0F, true);
      
    }
  }
}
