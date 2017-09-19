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
    public void placeInPortal(Entity entity, float rotationYaw){
    	entity.setPosition(x, y, z);
		entity.motionX = 0F;
		entity.motionY = 0F;
		entity.motionZ = 0F;
    }
}
