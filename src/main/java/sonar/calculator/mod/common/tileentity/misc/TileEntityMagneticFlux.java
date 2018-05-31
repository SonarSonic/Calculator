package sonar.calculator.mod.common.tileentity.misc;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.client.gui.misc.GuiMagneticFlux;
import sonar.calculator.mod.common.containers.ContainerMagneticFlux;
import sonar.core.api.IFlexibleGui;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.inventory.handling.EnumFilterType;
import sonar.core.inventory.handling.ItemTransferHelper;
import sonar.core.inventory.handling.filters.IExtractFilter;
import sonar.core.inventory.handling.filters.IInsertFilter;
import sonar.core.network.utils.IByteBufTile;

import java.util.List;
import java.util.Random;

public class TileEntityMagneticFlux extends TileEntityInventory implements IByteBufTile, IFlexibleGui {

	public boolean whitelisted, exact;
	public Random rand = new Random();
    public float rotate;
	public boolean disabled;

	public TileEntityMagneticFlux() {
		super.inv.setSize(8);
		super.inv.getInsertFilters().put(IInsertFilter.BLOCK_INSERT, EnumFilterType.EXTERNAL);
		super.inv.getExtractFilters().put(IExtractFilter.BLOCK_EXTRACT, EnumFilterType.EXTERNAL);
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

    @Override
	public void update() {
		super.update();
		if (this.world.isBlockIndirectlyGettingPowered(pos) > 0) {
			disabled = true;
			return;
		}
		disabled = false;
		if (this.world.isRemote) {
			if (!(rotate >= 1)) {
				rotate += (float) 1 / 100;
			} else {
				rotate = 0;
			}
		}
		this.magnetizeItems();
	}

    @Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			this.whitelisted = nbt.getBoolean("blacklisted");
			this.exact = nbt.getBoolean("exact");
		}
	}

    @Override
	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			nbt.setBoolean("blacklisted", whitelisted);
			nbt.setBoolean("exact", exact);
		}
		return nbt;
	}

	public void magnetizeItems() {
		int range = 10;
		AxisAlignedBB aabb = new AxisAlignedBB(pos.getX() - range, pos.getY() - range, pos.getZ() - range, pos.getX() + range, pos.getY() + range, pos.getZ() + range);
		List<EntityItem> items = this.world.getEntitiesWithinAABB(EntityItem.class, aabb, null);
		for (EntityItem entity : items) {
            if (validItemStack(entity.getItem())) {
				double x = pos.getX() + 0.5D - entity.posX;
				double y = pos.getY() + 0.2D - entity.posY;
				double z = pos.getZ() + 0.5D - entity.posZ;

				double distance = Math.sqrt(x * x + y * y + z * z);
				if (distance < 1.5) {
                    ItemStack itemstack = addToInventory(entity);
					if (itemstack.isEmpty() || itemstack.getCount() <= 0) {
						entity.setDead();
					} else {
                        entity.setItem(itemstack);
					}
				} else {
					double speed = entity.isBurning() ? 5.2 : 0.1;
					entity.motionX += x / distance * speed;
					entity.motionY += y * speed;
					if (y > 0) {
						entity.motionY = 0.10;
					}
					entity.motionZ += z / distance * speed;
				}
			}
		}
	}

	public boolean validItemStack(ItemStack stack) {
		for (ItemStack slot : slots()) {
			if (!slot.isEmpty()) {
				boolean matches = matchingStack(slot, stack);
				if (!this.whitelisted && matches) {
					return false;
				} else if (whitelisted && matches) {
					return true;
				}
			}
		}
		return !this.whitelisted;
	}

	public boolean matchingStack(ItemStack stack, ItemStack stack2) {
		if (exact) {
			int[] stackDict = OreDictionary.getOreIDs(stack2);
			int[] storedDict = OreDictionary.getOreIDs(stack);
            for (int aStackDict : stackDict) {
                for (int aStoredDict : storedDict) {
                    if (aStackDict == aStoredDict) {
						return true;
					}
				}
			}
		}
		return stack.getItem() == stack2.getItem() && (exact || stack.getItemDamage() == stack2.getItemDamage()) && (exact || ItemStack.areItemStackTagsEqual(stack, stack2));
	}

	public ItemStack addToInventory(EntityItem item) {
		if (!this.world.isRemote) {
			EntityItem entity = (EntityItem) this.world.getEntityByID(item.getEntityId());
			if (entity == null) {
				return null;
			}
            IItemHandler handler = ItemTransferHelper.getItemHandlerOffset(world, pos, EnumFacing.DOWN);
			return ItemTransferHelper.doInsert(entity.getItem(), Lists.newArrayList(handler));
		}
        return item.getItem();
	}

    @Override
	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip, IBlockState state) {
		if (!disabled) {
			String active = FontHelper.translate("locator.state") + " : " + FontHelper.translate("state.on");
			currenttip.add(active);
		} else {
			String idle = FontHelper.translate("locator.state") + " : " + FontHelper.translate("state.off");
			currenttip.add(idle);
		}

		return currenttip;
	}

	@Override
	public void writePacket(ByteBuf buf, int id) {
		switch (id) {
		case 0:
			this.whitelisted = !whitelisted;
			buf.writeBoolean(whitelisted);
			break;
		case 1:
			this.exact = !exact;
			buf.writeBoolean(exact);
			break;
		}
	}

	@Override
	public void readPacket(ByteBuf buf, int id) {
		switch (id) {
		case 0:
			whitelisted = buf.readBoolean();
			break;
		case 1:
			exact = buf.readBoolean();
			break;
		}
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerMagneticFlux(player.inventory, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiMagneticFlux(player.inventory, this);
	}
}
