package sonar.calculator.mod.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
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
	protected void onImpact(MovingObjectPosition var1) {

		if (!this.worldObj.isRemote) {
			setDead();
			this.worldObj.createExplosion((Entity) null, this.posX, this.posY, this.posZ, 5.0F, true);

		} else {
			for (int i = 0; i < 10; i++) {
				this.worldObj.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, 0.8999999761581421D, 0.8999999761581421D, 0.8999999761581421D);
			}
		}
	}
}
