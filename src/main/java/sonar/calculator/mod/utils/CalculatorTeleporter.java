package sonar.calculator.mod.utils;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class CalculatorTeleporter extends Teleporter {

	private final WorldServer world;
	private double x, y, z;

	public CalculatorTeleporter(WorldServer world, double x, double y, double z) {
		super(world);
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void placeInPortal(Entity entity, double p2, double p3, double p4, float p5) {
		this.world.getBlock((int) this.x, (int) this.y, (int) this.z);
		entity.setPosition(this.x, this.y, this.z);
		entity.motionX = 0F;
		entity.motionY = 0F;
		entity.motionZ = 0F;
	}

}
