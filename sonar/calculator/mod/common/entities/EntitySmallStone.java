package sonar.calculator.mod.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySmallStone extends EntityThrowable
{
	   public EntitySmallStone(World world)
	    {
	        super(world);
	    }

	    public EntitySmallStone(World world, EntityLivingBase player)
	    {
	        super(world, player);
	    }

	    public EntitySmallStone(World world, double x, double y, double z)
	    {
	        super(world, x, y, z);
	    }
  
  @Override
protected void onImpact(MovingObjectPosition var1) {
      if (!this.worldObj.isRemote) {
    	 if(var1.entityHit!=null){
    		 var1.entityHit.attackEntityFrom(CalculatorDamages.smallstone, 4.0F);
    	 }
        setDead();      
    }
  }
}
