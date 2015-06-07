package sonar.calculator.mod.client.renderers;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.models.ModelAtomicMultiplier;
import sonar.calculator.mod.client.models.ModelConductorMast;
import sonar.calculator.mod.client.models.ModelFlawlessCapacitor;
import sonar.calculator.mod.client.models.ModelGenerator;
import sonar.calculator.mod.client.models.ModelScarecrow;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.core.client.renderers.SonarTERender;
import sonar.core.utils.helpers.RenderHelper;

public class RenderHandlers {

	public static class AtomicMultiplier extends SonarTERender {
		public AtomicMultiplier() {
			super(new ModelAtomicMultiplier(),
					"Calculator:textures/model/atomicmultiplier.png");
		}

	}
	public static class ConductorMast extends SonarTERender {
		public ConductorMast() {
			super(new ModelConductorMast(),
					"Calculator:textures/model/conductormask.png");
		}

	}
	public static class Scarecrow extends SonarTERender {
		public Scarecrow(){
			super(new ModelScarecrow(),"Calculator:textures/model/scarecrow.png");
		}	
	}
	public static class StarchExtractor extends SonarTERender {
		public StarchExtractor(){
			super(new ModelGenerator(),"Calculator:textures/model/starchextractor.png");
		}			
	}
	public static class RedstoneExtractor extends SonarTERender {
		public RedstoneExtractor(){
			super(new ModelGenerator(),"Calculator:textures/model/redstoneextractor.png");
		}			
	}
	public static class GlowstoneExtractor extends SonarTERender {
		public GlowstoneExtractor(){
			super(new ModelGenerator(),"Calculator:textures/model/glowstoneextractor.png");
		}			
	}
}
