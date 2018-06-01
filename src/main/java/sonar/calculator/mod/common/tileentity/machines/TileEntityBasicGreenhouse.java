package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.machines.GuiBasicGreenhouse;
import sonar.calculator.mod.common.containers.ContainerBasicGreenhouse;
import sonar.calculator.mod.common.tileentity.TileEntityBuildingGreenhouse;
import sonar.calculator.mod.utils.helpers.GreenhouseHelper;
import sonar.core.api.IFlexibleGui;
import sonar.core.api.energy.EnergyMode;
import sonar.core.handlers.inventories.handling.EnumFilterType;
import sonar.core.handlers.inventories.handling.filters.IExtractFilter;
import sonar.core.handlers.inventories.handling.filters.SlotFilter;
import sonar.core.handlers.inventories.handling.filters.SlotHelper;
import sonar.core.helpers.SonarHelper;

import java.util.ArrayList;

public class TileEntityBasicGreenhouse extends TileEntityBuildingGreenhouse implements IFlexibleGui {

	public static final SlotFilter resource_slots = new SlotFilter(null, new int[] { 0, 1, 2, 3 });
	public static final SlotFilter plant_slots = new SlotFilter(null, new int[] { 5, 6, 7, 8, 9, 10, 11, 12, 13 });

	public int plants, lanterns, growTicks, growTick;

	public TileEntityBasicGreenhouse() {
		super(56, 18, 30, 14);
		super.storage.setCapacity(CalculatorConfig.BASIC_GREENHOUSE_STORAGE);
		super.storage.setMaxTransfer(CalculatorConfig.BASIC_GREENHOUSE_TRANSFER_RATE);
		super.inv.setSize(14);
		super.inv.getInsertFilters().put((SLOT,STACK,FACE) -> resource_slots.checkFilter(SLOT, FACE) ? checkInsert(SLOT,STACK,FACE) : null, EnumFilterType.EXTERNAL);
		super.inv.getInsertFilters().put((SLOT,STACK,FACE) -> plant_slots.checkFilter(SLOT, FACE) ? isSeed(STACK) : null, EnumFilterType.EXTERNAL);
		super.inv.getInsertFilters().put(SlotHelper.dischargeSlot(4), EnumFilterType.INTERNAL);
		super.inv.getExtractFilters().put(IExtractFilter.BLOCK_EXTRACT, EnumFilterType.EXTERNAL);
		super.energyMode = EnergyMode.RECIEVE;
		super.type = 1;
		super.maxLevel = 100000;
		super.plantTick = 60;
	}


	@Override
	public void update() {
		super.update();
		discharge(4);
	}

    @Override
	public ArrayList<BlockPos> getPlantArea() {
        ArrayList<BlockPos> coords = new ArrayList<>();
		int fX = forward.getFrontOffsetX();
		int fZ = forward.getFrontOffsetZ();
		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
                coords.add(pos.add(2 * fX + X, 0, 2 * fZ + Z));
			}
		}
		return coords;
	}

    /**
     * adds gas, depends on day and night
     **/
    @Override
	public void gasLevels() {
		boolean day = this.world.isDaytime();
		if (day) {
            int add = this.plants * 8 - this.lanterns * 50;
			this.addGas(-add);
		}
		if (!day) {
            int add = this.plants * 2 + this.lanterns * 50;
			this.addGas(add);
		}
	}

    /**
     * gets plants inside greenhouse and sets it to this.plants
     **/
    @Override
	public int getPlants() {
		this.plants = 0;
		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				if (this.world.getBlockState(pos.add(X, 0, Z)).getBlock() instanceof IGrowable) {
					this.plants++;
				}
			}
		}
		return plants;
	}

    /**
     * gets lanterns inside greenhouse and sets it to this.lanterns
     **/
    @Override
	public int getLanterns() {
		this.lanterns = 0;
		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
				for (int Y = 0; Y <= 3; Y++) {
                    BlockPos pos = this.pos.add(forward.getFrontOffsetX() * 2 + X, Y, forward.getFrontOffsetZ() * 2 + Z);
					if (this.world.getBlockState(pos).getBlock() == Calculator.gas_lantern_on) {
						this.lanterns++;
					}
				}
			}
		}
		return lanterns;
	}

    /**
     * Checks inventories has resources
     **/
    @Override
	public boolean hasRequiredStacks() {
		if (slots().get(0) != null && slots().get(1) != null && slots().get(2) != null && slots().get(3) != null) {
			if (slots().get(0).getCount() >= requiredLogs && GreenhouseHelper.checkLog(Block.getBlockFromItem(slots().get(0).getItem()))) {
				if (slots().get(1).getCount() >= requiredStairs && GreenhouseHelper.checkStairs(Block.getBlockFromItem(slots().get(1).getItem()))) {
					if (slots().get(2).getCount() >= requiredGlass && GreenhouseHelper.checkGlass(Block.getBlockFromItem(slots().get(2).getItem()))) {
                        return slots().get(3).getCount() >= requiredPlanks && GreenhouseHelper.checkPlanks(Block.getBlockFromItem(slots().get(3).getItem()));
					}
				}
			}
		}
		return false;
	}

    /**
     * Hoes the ground
     **/
    @Override
	public void addFarmland() {
		for (int Z = -1; Z <= 1; Z++) {
			for (int X = -1; X <= 1; X++) {
                BlockPos pos = this.pos.add(2 * forward.getFrontOffsetX() + X, 0, 2 * forward.getFrontOffsetZ() + Z);
				if (X == 0 && Z == 0) {
					if (this.storage.getEnergyStored() >= waterRF) {
						if (GreenhouseHelper.applyWater(world, pos)) {
							this.storage.modifyEnergyStored(-waterRF);
						}
					}
				} else {
					if (this.storage.getEnergyStored() >= farmlandRF) {
						if (GreenhouseHelper.applyFarmland(world, pos)) {
							this.storage.modifyEnergyStored(-farmlandRF);
						}
					}
					IBlockState state = world.getBlockState(pos);
					Block block = state.getBlock();
					if (!block.isAir(state, world, pos) && GreenhouseHelper.r(world, pos)) {
						world.setBlockToAir(pos);
					}
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.planting = nbt.getInteger("planting");
		this.plants = nbt.getInteger("Plants");
		this.lanterns = nbt.getInteger("lanterns");
		this.levelTicks = nbt.getInteger("Level");
		this.plantTicks = nbt.getInteger("Plant");
		this.checkTicks = nbt.getInteger("Check");
		this.growTicks = nbt.getInteger("Grow");
		this.growTick = nbt.getInteger("GrowTick");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		nbt.setInteger("planting", this.planting);
		nbt.setInteger("Plants", this.plants);
		nbt.setInteger("lanterns", this.lanterns);
		nbt.setInteger("Level", this.levelTicks);
		nbt.setInteger("Check", this.checkTicks);
		nbt.setInteger("Plant", this.plantTicks);
		nbt.setInteger("Grow", this.growTicks);
		nbt.setInteger("GrowTick", this.growTick);
		return nbt;
	}

    @Override
	public ArrayList<BlockPlace> getStructure() {
        ArrayList<BlockPlace> blocks = new ArrayList<>();

		int hX = SonarHelper.getHorizontal(forward).getFrontOffsetX();
		int hZ = SonarHelper.getHorizontal(forward).getFrontOffsetZ();

		int hoX = SonarHelper.getHorizontal(forward).getOpposite().getFrontOffsetX();
		int hoZ = SonarHelper.getHorizontal(forward).getOpposite().getFrontOffsetZ();

		int fX = forward.getFrontOffsetX();
		int fZ = forward.getFrontOffsetZ();

		// end
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		for (int i = 1; i <= 2; i++) {
			blocks.add(new BlockPlace(BlockType.LOG, x, y + i, z, -1));
		}

		for (int i = 0; i <= 2; i++) {
            blocks.add(new BlockPlace(BlockType.LOG, x + hX * 2, y + i, z + hZ * 2, -1));
            blocks.add(new BlockPlace(BlockType.LOG, x + hoX * 2, y + i, z + hoZ * 2, -1));
		}
		for (int i = 0; i <= 2; i++) {
            blocks.add(new BlockPlace(BlockType.GLASS, x + hX, y + i, z + hZ, -1));
            blocks.add(new BlockPlace(BlockType.GLASS, x + hoX, y + i, z + hoZ, -1));
		}

		// front
        x = pos.getX() + forward.getFrontOffsetX() * 4;
		y = pos.getY();
        z = pos.getZ() + forward.getFrontOffsetZ() * 4;
		for (int i = 0; i <= 2; i++) {
            blocks.add(new BlockPlace(BlockType.LOG, x + hX * 2, y + i, z + hZ * 2, -1));
            blocks.add(new BlockPlace(BlockType.LOG, x + hoX * 2, y + i, z + hoZ * 2, -1));
		}
		for (int i = 0; i <= 2; i++) {
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hX, y + i, z + hZ, -1));
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hoX, y + i, z + hoZ, -1));
			if (i == 2)
				blocks.add(new BlockPlace(BlockType.PLANKS, x, y + i, z, -1));
		}

		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		// sides
		for (int i = 1; i <= 3; i++) {
			if (i != 2) {
                blocks.add(new BlockPlace(BlockType.PLANKS, x + hX * 2 + fX * i, y - 1, z + hZ * 2 + fZ * i, -1));
                blocks.add(new BlockPlace(BlockType.PLANKS, x + hoX * 2 + fX * i, y - 1, z + hoZ * 2 + fZ * i, -1));
				for (int s = 0; s <= 1; s++) {

                    blocks.add(new BlockPlace(BlockType.GLASS, x + hX * 2 + fX * i, y + s, z + hZ * 2 + fZ * i, -1));
                    blocks.add(new BlockPlace(BlockType.GLASS, x + hoX * 2 + fX * i, y + s, z + hoZ * 2 + fZ * i, -1));
				}
			}
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hX * 2 + fX * i, y + 2, z + hZ * 2 + fZ * i, -1));
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hoX * 2 + fX * i, y + 2, z + hoZ * 2 + fZ * i, -1));
		}

		for (int Y = 0; Y <= 1; Y++) {
            blocks.add(new BlockPlace(BlockType.LOG, x + hX * 2 + fX * 2, y + Y, z + hZ * 2 + fZ * 2, -1));
            blocks.add(new BlockPlace(BlockType.LOG, x + hoX * 2 + fX * 2, y + Y, z + hoZ * 2 + fZ * 2, -1));
		}

		// roof
		for (int i = -1; i <= 1; i++) {
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hX * i, y + 3, z + hZ * i, -1));
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hX * i + fX * 4, y + 3, z + hZ * i + fZ * 4, -1));
		}
		for (int i = -1; i <= 5; i++) {
			for (int s = 2; s <= 4; s++) {
                blocks.add(new BlockPlace(BlockType.STAIRS, x + hX * intValues(s, BlockType.STAIRS) + fX * i, y + s, z + hZ * intValues(s, BlockType.STAIRS) + fZ * i, type("r")));
                blocks.add(new BlockPlace(BlockType.STAIRS, x + hoX * intValues(s, BlockType.STAIRS) + fX * i, y + s, z + hoZ * intValues(s, BlockType.STAIRS) + fZ * i, type("l")));
                blocks.add(new BlockPlace(BlockType.PLANKS, x + fX * i, y + 4, z + fZ * i, -1));
			}
		}

		// underroof
		for (int i = -1; i <= 5; i++) {
			if (i != -1 && i != 0 && i != 4 && i != 5) {
                blocks.add(new BlockPlace(BlockType.STAIRS, x + hX + fX * i, y + 3, z + hZ + fZ * i, type("d")));
                blocks.add(new BlockPlace(BlockType.STAIRS, x + hoX + fX * i, y + 3, z + hoZ + fZ * i, type("d2")));
			} else {
				if (i != 0 && i != 4) {
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hX + fX * i, y + 3, z + hZ + fZ * i, type("d")));
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hoX + fX * i, y + 3, z + hoZ + fZ * i, type("d2")));
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hX * 2 + fX * i, y + 2, z + hZ * 2 + fZ * i, type("d")));
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hoX * 2 + fX * i, y + 2, z + hoZ * 2 + fZ * i, type("d2")));
				}
			}
		}
		return blocks;
	}

	@Override
	public int[] getSlotsForType(BlockType type) {
		switch (type) {
		case LOG:
			return new int[] { 0 };
		case GLASS:
			return new int[] { 2 };
		case PLANKS:
			return new int[] { 3 };
		case STAIRS:
			return new int[] { 1 };
		default:
			return new int[0];
		}
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerBasicGreenhouse(player.inventory, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiBasicGreenhouse(player.inventory, this);
	}
}