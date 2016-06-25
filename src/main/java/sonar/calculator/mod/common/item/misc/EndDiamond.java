package sonar.calculator.mod.common.item.misc;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import sonar.core.common.item.SonarItem;

public class EndDiamond extends SonarItem {

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		world.playSound(player, player.getPosition(), SoundEvent.REGISTRY.getObject(new ResourceLocation("random.bow")), SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!world.isRemote) {
			world.spawnEntityInWorld(new EntityEnderPearl(world, player));
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
}
