package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import sonar.calculator.mod.client.gui.misc.GuiReinforcedChest;
import sonar.calculator.mod.common.containers.ContainerReinforcedChest;
import sonar.core.common.tileentity.TileEntityLargeInventory;
import sonar.core.inventory.ILargeInventory;
import sonar.core.inventory.SonarLargeInventory;
import sonar.core.utils.IGuiTile;

public class TileEntityReinforcedChest extends TileEntityLargeInventory implements IGuiTile {
	public float lidAngle;
	public float prevLidAngle;
	public int numPlayersUsing;
	private int ticksSinceSync;

	public TileEntityReinforcedChest() {
		super(27, 4);
	}

    @Override
	public SonarLargeInventory getTileInv() {
        return inv;
	}

	public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

    @Override
	public void update() {
		super.update();
		int i = this.pos.getX();
		int j = this.pos.getY();
		int k = this.pos.getZ();
		++this.ticksSinceSync;

		if (!this.world.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0) {
			this.numPlayersUsing = 0;
			float f = 5.0F;

			for (EntityPlayer entityplayer : this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((double) ((float) i - f), (double) ((float) j - f), (double) ((float) k - f), (double) ((float) (i + 1) + f), (double) ((float) (j + 1) + f), (double) ((float) (k + 1) + f)))) {
				if (entityplayer.openContainer instanceof ContainerReinforcedChest) {
					ILargeInventory iinventory = ((ContainerReinforcedChest) entityplayer.openContainer).entity;
					if (iinventory == this) {
						++this.numPlayersUsing;
					}
				}
			}
		}
		this.prevLidAngle = this.lidAngle;
		float f1 = 0.1F;

		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
			double d1 = (double) i + 0.5D;
			double d2 = (double) k + 0.5D;
            this.world.playSound(null, d1, (double) j + 0.5D, d2, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
			float f2 = this.lidAngle;

			if (this.numPlayersUsing > 0) {
				this.lidAngle += f1;
			} else {
				this.lidAngle -= f1;
			}

			if (this.lidAngle > 1.0F) {
				this.lidAngle = 1.0F;
			}

			float f3 = 0.5F;

			if (this.lidAngle < f3 && f2 >= f3) {
				double d3 = (double) i + 0.5D;
				double d0 = (double) k + 0.5D;
                this.world.playSound(null, d3, (double) j + 0.5D, d0, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (this.lidAngle < 0.0F) {
				this.lidAngle = 0.0F;
			}
		}
	}

    @Override
	public boolean receiveClientEvent(int id, int type) {
		if (id == 1) {
			this.numPlayersUsing = type;
			return true;
		} else {
			return super.receiveClientEvent(id, type);
		}
	}

	public void openInventory(EntityPlayer player) {
		// super.openInventory(player);
		if (!player.isSpectator()) {
			if (this.numPlayersUsing < 0) {
				this.numPlayersUsing = 0;
			}
			++this.numPlayersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), true);
			this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType(), true);
		}
	}

	public void closeInventory(EntityPlayer player) {
		// super.closeInventory(player);
		if (!player.isSpectator()) {
			--this.numPlayersUsing;
			if (this.numPlayersUsing < 0) {
				this.numPlayersUsing = 0;
			}
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), true);
			this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType(), true);
		}
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerReinforcedChest(player, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiReinforcedChest(player, this);
	}
}
