package sonar.calculator.mod.common.block;

import java.util.List;
import java.util.Random;

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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.core.common.block.properties.IMetaRenderer;
import sonar.core.common.block.properties.IMetaVariant;

public class MaterialBlock extends Block implements IMetaRenderer {

	public static final PropertyEnum<Variants> VARIANTS = PropertyEnum.<Variants> create("variant", Variants.class);

	public static enum Variants implements IStringSerializable, IMetaVariant {
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

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.randomDisplayTick(world, pos, state, rand);
		if (state.getValue(VARIANTS) == Variants.END_DIAMOND) {
			for (int l = 0; l < 3; ++l) {
				double d6 = pos.getX() + rand.nextFloat();
				double d1 = pos.getY() + rand.nextFloat();
				d6 = pos.getZ() + rand.nextFloat();
				double d3 = 0.0D;
				double d4 = 0.0D;
				double d5 = 0.0D;
				int i1 = rand.nextInt(2) * 2 - 1;
				int j1 = rand.nextInt(2) * 2 - 1;
				d3 = (rand.nextFloat() - 0.5D) * 0.125D;
				d4 = (rand.nextFloat() - 0.5D) * 0.125D;
				d5 = (rand.nextFloat() - 0.5D) * 0.125D;
				double d2 = pos.getZ() + 0.5D + 0.25D * j1;
				d5 = rand.nextFloat() * 1.0F * j1;
				double d0 = pos.getX() + 0.5D + 0.25D * i1;
				d3 = rand.nextFloat() * 1.0F * i1;
				world.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
			}
		}
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
