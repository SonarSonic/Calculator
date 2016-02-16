package sonar.calculator.mod.common.item.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			itemstack.stackSize -= 1;
		}
		world.playSoundAtEntity(player, "random.fizz", 0.7F, 0.8F);

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

		return itemstack;
	}
}
