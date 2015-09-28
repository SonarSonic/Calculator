package sonar.calculator.mod.api;

import net.minecraft.world.World;

/** implementing on blocks, used by the Calculator Wrench */
public interface IWrenchable {

	/** called when a block is clicked by a wrench */
	public void onWrench(World world, int x, int y, int z, int side);

}