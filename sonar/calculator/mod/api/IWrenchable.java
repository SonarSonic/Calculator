package sonar.calculator.mod.api;

import net.minecraft.world.World;

public interface IWrenchable {

	public void onWrench(World world, int x, int y, int z, int side);

}