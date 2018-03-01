package sonar.calculator.mod.common.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityGrenade extends EntityThrowable {
	public EntityGrenade(World world) {
		super(world);
	}

	public EntityGrenade(World world, EntityLivingBase player) {
		super(world, player);
	}

	public EntityGrenade(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected void onImpact(RayTraceResult result) {

		if (!this.getEntityWorld().isRemote) {
			setDead();
            this.getEntityWorld().createExplosion(null, this.posX, this.posY, this.posZ, 5.0F, true);
		} else {
			for (int i = 0; i < 10; i++) {
				this.getEntityWorld().spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, 0.8999999761581421D, 0.8999999761581421D, 0.8999999761581421D);
			}
		}
	}
}
