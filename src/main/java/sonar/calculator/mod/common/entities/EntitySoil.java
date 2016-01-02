package sonar.calculator.mod.common.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySoil extends EntityThrowable {
	public EntitySoil(World world) {
		super(world);
	}

	public EntitySoil(World world, EntityLivingBase player) {
		super(world, player);
	}

	public EntitySoil(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {
		if (!this.worldObj.isRemote) {
			if (var1.entityHit != null && var1.entityHit instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) var1.entityHit;
				player.addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 1));

			}

			setDead();
		}
	}
}
