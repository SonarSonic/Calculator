package sonar.calculator.mod.api.blocks.properties;

import java.util.Collection;

import net.minecraft.block.properties.PropertyEnum;
import sonar.calculator.mod.common.block.ConnectedBlock.StableStoneColours;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class PropertyStableStoneColours extends PropertyEnum<StableStoneColours> {
	protected PropertyStableStoneColours(String name, Collection<StableStoneColours> values) {
		super(name, StableStoneColours.class, values);
	}

	public static PropertyStableStoneColours create(String name) {
		return create(name, StableStoneColours.NONE);
	}

	public static PropertyStableStoneColours create(String name, Predicate<StableStoneColours> filter) {
		return create(name, Collections2.<StableStoneColours> filter(Lists.newArrayList(StableStoneColours.values()), filter));
	}

	public static PropertyStableStoneColours create(String name, Collection<StableStoneColours> values) {
		return new PropertyStableStoneColours(name, values);
	}
}