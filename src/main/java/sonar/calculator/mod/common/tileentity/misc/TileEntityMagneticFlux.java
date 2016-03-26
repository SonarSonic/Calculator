package sonar.calculator.mod.common.tileentity.misc;

import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.client.gui.misc.GuiMagneticFlux;
import sonar.calculator.mod.common.containers.ContainerMagneticFlux;
import sonar.core.api.ActionType;
import sonar.core.api.SonarAPI;
import sonar.core.api.StoredItemStack;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.helpers.FontHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.SonarHelper;
import sonar.core.inventory.SonarTileInventory;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.utils.IGuiTile;

public class TileEntityMagneticFlux extends TileEntityInventory implements IEntitySelector, ISidedInventory, IByteBufTile, IGuiTile {

	public boolean whitelisted, exact;
	public Random rand = new Random();
	public float rotate = 0;
	public boolean disabled;

	public TileEntityMagneticFlux() {
		super.inv = new SonarTileInventory(this, 8);
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	public void update() {
		super.update();
		if (this.worldObj.isBlockIndirectlyGettingPowered(pos)>0) {
			disabled = true;
			return;
		}
		disabled = false;
		if (this.worldObj.isRemote) {
			if (!(rotate >= 1)) {
				rotate += (float) 1 / 100;
			} else {
				rotate = 0;
			}
		}
		this.magnetizeItems();

	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			this.whitelisted = nbt.getBoolean("blacklisted");
			this.exact = nbt.getBoolean("exact");
		}

	}

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type == SyncType.SAVE || type == SyncType.SYNC) {
			nbt.setBoolean("blacklisted", whitelisted);
			nbt.setBoolean("exact", exact);
		}
	}

	public void magnetizeItems() {
		int range = 10;
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range);
		List<Entity> items = this.worldObj.selectEntitiesWithinAABB(EntityItem.class, aabb, null);
		for (Entity entity : items) {
			if (entity instanceof EntityItem && validItemStack(((EntityItem) entity).getEntityItem())) {
				double x = xCoord + 0.5D - entity.posX;
				double y = yCoord + 0.2D - entity.posY;
				double z = zCoord + 0.5D - entity.posZ;

				double distance = Math.sqrt(x * x + y * y + z * z);

				if (distance < 1.5) {
					ItemStack itemstack = addToInventory((EntityItem) entity);
					if (itemstack == null || itemstack.stackSize <= 0) {
						entity.setDead();
					} else {
						((EntityItem) entity).setEntityItemStack(itemstack);
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
		if (slots == null) {
			return true;
		}
		for (int i = 0; i < slots.length; i++) {
			if (slots()[i] != null) {
				boolean matches = matchingStack(slots()[i], stack);
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
			for (int i = 0; i < stackDict.length; i++) {
				for (int s = 0; s < storedDict.length; s++) {
					if (stackDict[i] == storedDict[s]) {
						return true;
					}
				}
			}

		}

		return stack.getItem() == stack2.getItem() && (exact || stack.getItemDamage() == stack2.getItemDamage()) && (exact || ItemStack.areItemStackTagsEqual(stack, stack2));

	}

	public ItemStack addToInventory(EntityItem item) {
		if (!this.worldObj.isRemote) {
			EntityItem entity = (EntityItem) this.worldObj.getEntityByID(item.getEntityId());
			if (entity == null) {
				return null;
			}
			ItemStack itemstack = entity.getEntityItem();
			int i = itemstack.stackSize;
			TileEntity target = SonarHelper.getAdjacentTileEntity(this, EnumFacing.DOWN);
			if (target instanceof IItemDuct) {
				itemstack = ((IItemDuct) target).insertItem(EnumFacing.UP, itemstack);
			} else {
				itemstack = SonarAPI.getItemHelper().addItems(target, new StoredItemStack(itemstack), ForgeDirection.getOrientation(1), ActionType.PERFORM, null).getFullStack();
			}
			return itemstack;
		}
		return item.getEntityItem();
	}

	@Override
	public boolean isEntityApplicable(Entity entity) {
		if (entity instanceof EntityItem) {

			return true;
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public List<String> getWailaInfo(List<String> currenttip) {
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
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerMagneticFlux(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiMagneticFlux(player.inventory, this);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		// TODO Auto-generated method stub
		return false;
	}
}
