package sonar.calculator.mod.common.item.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import sonar.calculator.mod.common.entities.EntityBabyGrenade;
import sonar.calculator.mod.common.entities.EntityGrenade;
import sonar.core.common.item.SonarItem;
import sonar.core.utils.SonarCompat;

public class Grenade extends SonarItem {
	int type;

	public Grenade(int par) {
		type = par;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if (!player.capabilities.isCreativeMode) {
			stack = SonarCompat.shrink(stack, 1);
		}
        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote) {

			EntityThrowable entity = null;
			switch (type) {
			case 0:
				entity = new EntityBabyGrenade(world, player);
				break;
			case 1:
				entity = new EntityGrenade(world, player);
				break;
			}
			entity.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntityInWorld(entity);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
}
