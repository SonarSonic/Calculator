package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.TileEntityBuildingGreenhouse;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse.GreenhouseAction;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse.State;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityBasicGreenhouse;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.api.utils.BlockInteractionType;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.IGuiTile;

public abstract class Greenhouse extends SonarMachineBlock {
	
	public Greenhouse() {
		super(SonarMaterials.machine, true, true);
	}
	
	public static class Advanced extends Greenhouse{

		@Override
		public TileEntity createNewTileEntity(World var1, int var2) {
			return new TileEntityAdvancedGreenhouse();
		}
	}
	
	public static class Basic extends Greenhouse{

		@Override
		public TileEntity createNewTileEntity(World var1, int var2) {
			return new TileEntityBasicGreenhouse();
		}
	}
	
    @Override
	public abstract TileEntity createNewTileEntity(World var1, int var2);
	
	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityBuildingGreenhouse) {
			TileEntityBuildingGreenhouse house = (TileEntityBuildingGreenhouse) tile;
			if (interact.type == BlockInteractionType.SHIFT_RIGHT) {
				
				if (house.houseState.getObject() == State.INCOMPLETE) {
					if (!house.wasBuilt.getObject()) {
						if (!(house.storage.getEnergyStored() >= house.requiredBuildEnergy)) {
							FontHelper.sendMessage(FontHelper.translate("energy.notEnough"), world, player);
							return true;
						} else if (house.hasRequiredStacks()) {
							if (house.houseState.getObject() == State.INCOMPLETE && !house.wasBuilt.getObject()) {
								FailedCoords coords = house.createBlock();
								if (!coords.getBoolean()) {
                                    FontHelper.sendMessage(FontHelper.translate("greenhouse.block") + ' ' + "X: " + coords.getCoords().getX() + " Y: " + coords.getCoords().getY() + " Z: " + coords.getCoords().getZ() + " - " + FontHelper.translate("greenhouse.blocking"), world, player);
								} else {
									FontHelper.sendMessage(FontHelper.translate("greenhouse.construction"), world, player);
								}
								return true;
							}
						} else {
							FontHelper.sendMessage(house.getRequiredStacks().toString(), world, player);
							return true;
						}
					}else {
						FailedCoords coords = house.checkStructure(GreenhouseAction.CHECK);
						if (!coords.getBoolean()) {
                            FontHelper.sendMessage("X: " + coords.getCoords().getX() + " Y: " + coords.getCoords().getY() + " Z: " + coords.getCoords().getZ() + " - " + FontHelper.translate("greenhouse.equal") + ' ' + coords.getBlock(), world, player);
							return true;
						}
					}
				}
				if (house.houseState.getObject() == State.COMPLETED) {
					FontHelper.sendMessage(new TextComponentTranslation("greenhouse.complete"), world, player);
				}
			} else {
				if (player != null) {
					if (!world.isRemote) {
						player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
					}
				}
			}
		}
		return true;
	}

    @Override
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list, NBTTagCompound tag) {
        CalculatorHelper.addEnergytoToolTip(stack, world, list);
        CalculatorHelper.addGasToolTip(stack, world, list);
    }
}
