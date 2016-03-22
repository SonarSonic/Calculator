package sonar.calculator.mod.network;

import sonar.calculator.mod.Calculator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public enum RenderThrowables implements IRenderFactory {
	
	GRENADE,BABY_GRENADE,SMALL_STONE,SOIL;

	@Override
	public Render createRenderFor(RenderManager manager) {
		switch(this){
		case GRENADE:
			return new RenderSnowball(manager, Calculator.grenade, Minecraft.getMinecraft().getRenderItem());
		case BABY_GRENADE:
			return new RenderSnowball(manager, Calculator.baby_grenade, Minecraft.getMinecraft().getRenderItem());
		case SMALL_STONE:
			return new RenderSnowball(manager, Calculator.small_stone, Minecraft.getMinecraft().getRenderItem());
		case SOIL:
			return new RenderSnowball(manager, Calculator.soil, Minecraft.getMinecraft().getRenderItem());
		default:
			break;
		}
		return null;
	}

}
