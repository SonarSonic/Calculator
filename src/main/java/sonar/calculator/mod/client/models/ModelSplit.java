package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSplit extends ModelBase
{
  //fields
    ModelRenderer Split1;
    ModelRenderer Split2;
    ModelRenderer Frame1;
    ModelRenderer Frame2;
    ModelRenderer Frame3;
  
  public ModelSplit()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Split1 = new ModelRenderer(this, 0, 0);
      Split1.addBox(0F, 0F, 0F, 1, 4, 1);
      Split1.setRotationPoint(-0.5F, 10F, -0.5F);
      Split1.setTextureSize(64, 32);
      Split1.mirror = true;
      setRotation(Split1, 0F, 0F, 0F);
      Split2 = new ModelRenderer(this, 0, 0);
      Split2.addBox(0F, 0F, 0F, 3, 1, 3);
      Split2.setRotationPoint(-1.5F, 13F, -1.5F);
      Split2.setTextureSize(64, 32);
      Split2.mirror = true;
      setRotation(Split2, 0F, 0F, 0F);
      Frame1 = new ModelRenderer(this, 0, 0);
      Frame1.addBox(0F, 0F, 0F, 12, 1, 2);
      Frame1.setRotationPoint(-6F, 9F, -1F);
      Frame1.setTextureSize(64, 32);
      Frame1.mirror = true;
      setRotation(Frame1, 0F, 0F, 0F);
      Frame2 = new ModelRenderer(this, 0, 0);
      Frame2.addBox(0F, 0F, 0F, 1, 10, 2);
      Frame2.setRotationPoint(-7F, 10F, -1F);
      Frame2.setTextureSize(64, 32);
      Frame2.mirror = true;
      setRotation(Frame2, 0F, 0F, 0F);
      Frame3 = new ModelRenderer(this, 0, 0);
      Frame3.addBox(0F, 0F, 0F, 1, 10, 2);
      Frame3.setRotationPoint(6F, 10F, -1F);
      Frame3.setTextureSize(64, 32);
      Frame3.mirror = true;
      setRotation(Frame3, 0F, 0F, 0F);
  }
  public void renderSplitter(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Split1.render(f5);
    Split2.render(f5);
    Frame1.render(f5);
  }
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Frame2.render(f5);
    Frame3.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
