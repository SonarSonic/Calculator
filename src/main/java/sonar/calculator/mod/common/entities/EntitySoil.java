package sonar.calculator.mod.common.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.RayTraceResult;
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
	protected void onImpact(RayTraceResult result) {
		if (!this.world.isRemote) {
			if (result.entityHit != null && result.entityHit instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) result.entityHit;
				player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("blindness"), 100, 1));
			}
			setDead();
		}
	}
}
