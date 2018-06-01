package sonar.calculator.mod.common.block.generators;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.network.FlexibleGuiHandler;
import sonar.core.utils.ISpecialTooltip;

import javax.annotation.Nonnull;
import java.util.List;

public class ExtractorBlock extends SonarBlock implements ITileEntityProvider, ISpecialTooltip {
	public int type;

	public ExtractorBlock(int type) {
		super(SonarMaterials.machine, false);
		this.type = type;
		this.hasSpecialRenderer = true;
		this.setBlockBounds(0.0625F, 0.0625F, 0.0625F, 1 - 0.0625F, 1 - 0.0625F, 1 - 0.0625F);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player != null && !world.isRemote) {
			FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
		}
		return true;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityGenerator) {
			TileEntityGenerator generator = (TileEntityGenerator) world.getTileEntity(pos);
			//generator.updateAdjacentHandlers();
		}
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbour) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityGenerator) {
			TileEntityGenerator generator = (TileEntityGenerator) world.getTileEntity(pos);
			//generator.updateAdjacentHandlers();
		}
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World world, int var) {
		switch (type) {
		case 0:
			return new TileEntityGenerator.StarchExtractor();
		case 1:
			return new TileEntityGenerator.RedstoneExtractor();
		case 2:
			return new TileEntityGenerator.GlowstoneExtractor();
		}
		return new TileEntityGenerator.StarchExtractor();
	}

    @Override
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list, NBTTagCompound tag) {
        CalculatorHelper.addEnergytoToolTip(stack, world, list);
        CalculatorHelper.addItemLevelToolTip(stack, world, list);
        switch (type) {
		case 0:
			list.add(FontHelper.translate("energy.generate") + ": " + CalculatorConfig.STARCH_EXTRACTOR_PER_TICK + " RF/t");
			break;
		case 1:
			list.add(FontHelper.translate("energy.generate") + ": " + CalculatorConfig.REDSTONE_EXTRACTOR_PER_TICK + " RF/t");
			break;
		case 2:
			list.add(FontHelper.translate("energy.generate") + ": " + CalculatorConfig.GLOWSTONE_EXTRACTOR_PER_TICK + " RF/t");
			break;
		}
	}
}
