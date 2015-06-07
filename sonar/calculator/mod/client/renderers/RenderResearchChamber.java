package sonar.calculator.mod.client.renderers;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.registry.GameRegistry;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.models.ModelAtomicMultiplier;
import sonar.calculator.mod.client.models.ModelResearchChamber;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.core.utils.helpers.RenderHelper;

public class RenderResearchChamber extends TileEntitySpecialRenderer {
	private static final ResourceLocation texture = new ResourceLocation(
			"Calculator:textures/model/researchchamber.png");

	  private static final ResourceLocation scan = new ResourceLocation("Calculator:textures/blocks/scanner.png");
	private ModelResearchChamber model;

	public final RenderBlocks renderBlocks;
	private RenderItem itemRenderer;

	public RenderResearchChamber() {
		this.model = new ModelResearchChamber();
		this.renderBlocks = new RenderBlocks();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double xCoord, double yCoord,
			double zCoord, float f) {
		RenderHelper.beginRender(xCoord + 0.5F, yCoord + 1.5F, zCoord + 0.5F, RenderHelper.setMetaData(tileentity), texture.toString());
		
		this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		if (tileentity.getWorldObj() != null) {
			TileEntityResearchChamber chamber = (TileEntityResearchChamber) tileentity;

			if (chamber.slots[0] != null) {
			if (GameRegistry.findUniqueIdentifierFor(chamber.slots[0].getItem()).modId.equals(Calculator.modid) && !RenderBlocks.renderItemIn3d(Block.getBlockFromItem(chamber.slots[0].getItem()).getRenderType()) && chamber.slots[0].getItem() instanceof ItemBlock  && Block.getBlockFromItem(chamber.slots[0].getItem()).getRenderType() == -1 && MinecraftForgeClient.getItemRenderer(chamber.slots[0], ItemRenderType.FIRST_PERSON_MAP)!=null) {

				GL11.glRotated(180, 1, 0, 0);
				GL11.glScaled(0.31, 0.31, 0.31);
				GL11.glTranslated(-0.5, -3.25, -0.3);
				if(Block.getBlockFromItem(chamber.slots[0].getItem())==Calculator.scarecrow){
					GL11.glRotated(180, 0, 1, 0);	
					GL11.glScaled(0.5, 0.5, 0.5);
					GL11.glTranslated(-1.45, 0, -1.45);
				}

				if(Block.getBlockFromItem(chamber.slots[0].getItem())== Calculator.starchextractor ||
						Block.getBlockFromItem(chamber.slots[0].getItem())== Calculator.glowstoneextractor ||
						Block.getBlockFromItem(chamber.slots[0].getItem())== Calculator.redstoneextractor ){
					GL11.glTranslated(0, 0.2, 0);
				}
				if(Block.getBlockFromItem(chamber.slots[0].getItem())==Calculator.conductorMast){
					GL11.glScaled(0.3, 0.3, 0.3);
					GL11.glTranslated(1.2, 0, 1.2);
				}
				MinecraftForgeClient.getItemRenderer(chamber.slots[0], ItemRenderType.FIRST_PERSON_MAP).renderItem(ItemRenderType.FIRST_PERSON_MAP, chamber.slots[0], renderBlocks, new EntityItem(tileentity.getWorldObj(), (int)xCoord, (int)yCoord, (int)zCoord, chamber.slots[0]));;
				
			} else {
				GL11.glTranslated(0.0, 1.01, -0.06);
				GL11.glRotated(180, 1, 0, 0);
					if (!(chamber.slots[0].getItemSpriteNumber() == 0)
							|| chamber.slots[0].getItemSpriteNumber() == 0
							&& chamber.slots[0].getItem() instanceof ItemBlock
							&& !RenderBlocks.renderItemIn3d(Block.getBlockFromItem(chamber.slots[0].getItem()).getRenderType())) {
						GL11.glRotated(-90, 1, 0, 0);
						GL11.glTranslated(0, -0.22, 0.03);
					}
					RenderHelper.renderItem(tileentity.getWorldObj(),chamber.slots[0]);

				}
			}
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		 if(tileentity.getWorldObj()!=null && tileentity instanceof TileEntityResearchChamber){
			 TileEntityResearchChamber tile = (TileEntityResearchChamber) tileentity;    	
		    	if(tile.slots[0]!=null && tile.ticks!=0 && tile.ticks!=tile.researchSpeed){

		    double height = 0.3;
		    Tessellator tessellator = Tessellator.instance;
		    this.bindTexture(scan);
		    GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
		    GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
		    GL11.glDisable(GL11.GL_LIGHTING);
		    GL11.glDisable(GL11.GL_CULL_FACE);
		    GL11.glDisable(GL11.GL_BLEND);
		    GL11.glDepthMask(true);
		    OpenGlHelper.glBlendFunc(770, 1, 1, 0);
		    float f2 = (float)tileentity.getWorldObj().getTotalWorldTime() + 20;
		    float f3 = -f2 * 0.01F - (float)MathHelper.floor_float(-f2 * 0.1F);
		    byte b0 = 1;
		    double d3 = (double)f2 * 0.025D * (1.0D - (double)(b0 & 1) * 2.5D);
		    tessellator.startDrawingQuads();
		    tessellator.setColorRGBA(255, 255, 255, 32);
		    double d5 = (double)b0 * 0.33D;
		    double d7 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d5;
		    double d9 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d5;
		    double d11 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * d5;
		    double d13 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * d5;
		    double d15 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d5;
		    double d17 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d5;
		    double d19 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d5;
		    double d21 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d5;
		    double d23 = (double)(height);
		    double d25 = 0.0D;
		    double d27 = 1.0D;
		    double d28 = (double)(-1.0F + f3);
		    double d29 = (double)(height) * (0.5D / d5) + d28;
		    
		    ForgeDirection dir = ForgeDirection.getOrientation(tileentity.blockMetadata);
		    double x = xCoord + dir.offsetX*+0.06;
		    double y = yCoord +0.5;
		    double z = zCoord + dir.offsetZ*+0.06;
		    
		    tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d27, d29);
		    tessellator.addVertexWithUV(x + d7, y, z + d9, d27, d28);
		    tessellator.addVertexWithUV(x + d11, y, z + d13, d25, d28);
		    tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d25, d29);
		    tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d27, d29);
		    tessellator.addVertexWithUV(x + d19, y, z + d21, d27, d28);
		    tessellator.addVertexWithUV(x + d15, y, z + d17, d25, d28);
		    tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d25, d29);
		    tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d27, d29);
		    tessellator.addVertexWithUV(x + d11, y, z + d13, d27, d28);
		    tessellator.addVertexWithUV(x + d19, y, z + d21, d25, d28);
		    tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d25, d29);
		    tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d27, d29);
		    tessellator.addVertexWithUV(x + d15, y, z + d17, d27, d28);
		    tessellator.addVertexWithUV(x + d7, y, z + d9, d25, d28);
		    tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d25, d29);
		    
		    
		    tessellator.draw();
		    GL11.glEnable(GL11.GL_BLEND);
		    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		    GL11.glDepthMask(false);
		    tessellator.startDrawingQuads();
		    tessellator.setColorRGBA(255, 255, 255, 32);
		    double offset = 0.2D;
		    double d6 = 0.8D;
		    double d18 = (double)(height);
		    double d20 = 0.0D;
		    double d22 = 1.0D;
		    double d24 = (double)(-1.0F + f3);
		    double d26 = (double)(height) + d24;
		    
		    tessellator.addVertexWithUV(x + offset, y + d18, z + offset, d22, d26);
		    tessellator.addVertexWithUV(x + offset, y, z + offset, d22, d24);
		    tessellator.addVertexWithUV(x + d6, y, z + offset, d20, d24);
		    tessellator.addVertexWithUV(x + d6, y + d18, z + offset, d20, d26);
		    tessellator.addVertexWithUV(x + d6, y + d18, z + offset, d22, d26);
		    tessellator.addVertexWithUV(x + d6, y, z + d6, d22, d24);
		    tessellator.addVertexWithUV(x + offset, y, z + d6, d20, d24);
		    tessellator.addVertexWithUV(x + offset, y + d18, z + d6, d20, d26);
		    tessellator.addVertexWithUV(x + d6, y + d18, z + offset, d22, d26);
		    tessellator.addVertexWithUV(x + d6, y, z + offset, d22, d24);
		    tessellator.addVertexWithUV(x + d6, y, z + d6, d20, d24);
		    tessellator.addVertexWithUV(x + d6, y + d18, z + d6, d20, d26);
		    tessellator.addVertexWithUV(x + offset, y + d18, z + d6, d22, d26);
		    tessellator.addVertexWithUV(x + offset, y, z + d6, d22, d24);
		    tessellator.addVertexWithUV(x + offset, y, z + offset, d20, d24);
		    tessellator.addVertexWithUV(x + offset, y + d18, z + offset, d20, d26);
		    
		    
		    tessellator.draw();
		    GL11.glEnable(GL11.GL_LIGHTING);
		    GL11.glEnable(GL11.GL_TEXTURE_2D);
		    GL11.glDepthMask(true);
		    }
		    	
		    }
		   
	}
}
