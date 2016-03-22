package sonar.calculator.mod.common.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.common.block.ConnectedBlock.StableStoneColours;
import sonar.core.common.block.properties.IMetaRenderer;
import sonar.core.common.block.properties.IMetaVariant;

public class MaterialBlock extends Block implements IMetaRenderer<MaterialBlock.Variants> {

	public static final PropertyEnum<Variants> VARIANTS = PropertyEnum.<Variants> create("variant", Variants.class);

	public enum Variants implements IStringSerializable, IMetaVariant {
		AMETHYST(0), TANZANITE(1), ENRICHED_GOLD(2), REINFORCED_IRON(3), WEAKENED_DIAMOND(4), FLAWLESS_DIAMOND(5), FIRE_DIAMOND(6), ELECTRIC_DIAMOND(7), END_DIAMOND(8);
		private int meta;

		Variants(int meta) {
			this.meta = meta;
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

		@Override
		public int getMeta() {
			return meta;
		}
	}

	@Override
	public Variants[] getVariants() {
		return Variants.values();
	}

	@Override
	public Variants getVariant(int metadata) {
		for (Variants colour : Variants.values()) {
			if (colour.meta == metadata) {
				return colour;
			}
		}
		return Variants.AMETHYST;
	}

	public MaterialBlock() {
		super(Material.rock);
	}

	public int damageDropped(IBlockState state) {
		return ((Variants) state.getValue(VARIANTS)).getMeta();
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
		for (int i = 0; i < getVariants().length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANTS, getVariant(meta));
	}

	public int getMetaFromState(IBlockState state) {
		return ((Variants) state.getValue(VARIANTS)).getMeta();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANTS });
	}

}
