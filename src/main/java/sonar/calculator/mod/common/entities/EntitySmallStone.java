package sonar.calculator.mod.common.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySmallStone extends EntityThrowable {
	public EntitySmallStone(World world) {
		super(world);
	}

	public EntitySmallStone(World world, EntityLivingBase player) {
		super(world, player);
	}

	public EntitySmallStone(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!this.getEntityWorld().isRemote) {
			if (result.entityHit != null) {
				result.entityHit.attackEntityFrom(CalculatorDamages.smallstone, 4.0F);
			}
			setDead();
		}
	}
}
