package sonar.calculator.mod.common.block;

import sonar.calculator.mod.common.block.ConnectedBlock.StableStoneColours;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;

public class CompressedBlock extends Block {

	public static final PropertyEnum<CompressedTypes> COLOUR = PropertyEnum.<CompressedTypes> create("colour", CompressedTypes.class);

	public CompressedBlock() {
		super(Material.rock);		
	}

	public enum CompressedTypes implements IStringSerializable, Predicate<CompressedTypes> {
		AMETHYST(0), TANZANITE(1), ENRICHED_GOLD(2), REINFORCED_IRON(3), WEAKENED_DIAMOND(4), FLAWLESS_DIAMOND(5), FIRE_DIAMOND(6), ELECTRIC_DIAMOND(7), END_DIAMOND(8);

		private int meta;

		CompressedTypes(int meta) {
			this.meta = meta;
		}

		public int getMetaData() {
			return meta;
		}

		@Override
		public String getName() {
			return this.name();
		}

		@Override
		public boolean apply(CompressedTypes input) {
			return input.meta == this.meta;
		}

		public static CompressedTypes getColour(int meta) {
			for (CompressedTypes colour : CompressedTypes.values()) {
				if (colour.meta == meta) {
					return colour;
				}
			}
			return CompressedTypes.AMETHYST;
		}

	}
}
