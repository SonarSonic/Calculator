package sonar.calculator.mod.common.item.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import sonar.calculator.mod.common.entities.EntityBabyGrenade;
import sonar.calculator.mod.common.entities.EntityGrenade;
import sonar.core.common.item.SonarItem;

public class Grenade extends SonarItem {
	int type;

	public Grenade(int par) {
		type = par;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if (!player.capabilities.isCreativeMode) {
			stack.stackSize -= 1;
		}
		world.playSound(player, player.getPosition(), SoundEvent.REGISTRY.getObject(new ResourceLocation("random.fizz")), SoundCategory.PLAYERS, 0.7F, 0.8F);

		if (!world.isRemote) {
			switch (type) {
			case 0:
				world.spawnEntityInWorld(new EntityBabyGrenade(world, player));
				break;
			case 1:
				world.spawnEntityInWorld(new EntityGrenade(world, player));
				break;
			}
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
}
