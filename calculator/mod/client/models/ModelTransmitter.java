
package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTransmitter extends ModelBase
{
  //fields
    ModelRenderer Centre;
    ModelRenderer Bar1;
    ModelRenderer Bar2;
    ModelRenderer Bar3;
    ModelRenderer Bar4;
    ModelRenderer Middle;
    ModelRenderer Bottom;
  
  public ModelTransmitter()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      Centre = new ModelRenderer(this, 0, 36);
      Centre.addBox(0F, 0F, 0F, 2, 26, 2);
      Centre.setRotationPoint(-1F, -8F, -1F);
      Centre.setTextureSize(128, 64);
      Centre.mirror = true;
      setRotation(Centre, 0F, 0F, 0F);
      Bar1 = new ModelRenderer(this, 0, 0);
      Bar1.addBox(0F, 0F, 0F, 20, 1, 1);
      Bar1.setRotationPoint(-10F, -4F, 1F);
      Bar1.setTextureSize(128, 64);
      Bar1.mirror = true;
      setRotation(Bar1, 0F, 0F, 0F);
      Bar2 = new ModelRenderer(this, 0, 0);
      Bar2.addBox(0F, 0F, 0F, 20, 1, 1);
      Bar2.setRotationPoint(-10F, -4F, -2F);
      Bar2.setTextureSize(128, 64);
      Bar2.mirror = true;
      setRotation(Bar2, 0F, 0F, 0F);
      Bar3 = new ModelRenderer(this, 0, 0);
      Bar3.addBox(0F, 0F, 0F, 20, 1, 1);
      Bar3.setRotationPoint(1F, -4F, 10F);
      Bar3.setTextureSize(128, 64);
      Bar3.mirror = true;
      setRotation(Bar3, 0F, 1.570796F, 0F);
      Bar4 = new ModelRenderer(this, 0, 0);
      Bar4.addBox(0F, 0F, 0F, 20, 1, 1);
      Bar4.setRotationPoint(-2F, -4F, 10F);
      Bar4.setTextureSize(128, 64);
      Bar4.mirror = true;
      setRotation(Bar4, 0F, 1.570796F, 0F);
      Middle = new ModelRenderer(this, 0, 11);
      Middle.addBox(0F, 0F, 0F, 4, 9, 4);
      Middle.setRotationPoint(-2F, 1F, -2F);
      Middle.setTextureSize(128, 64);
      Middle.mirror = true;
      setRotation(Middle, 0F, 0F, 0F);
      Bottom = new ModelRenderer(this, 0, 24);
      Bottom.addBox(0F, 0F, 0F, 6, 6, 6);
      Bottom.setRotationPoint(-3F, 18F, -3F);
      Bottom.setTextureSize(128, 64);
      Bottom.mirror = true;
      setRotation(Bottom, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Centre.render(f5);
    Bar1.render(f5);
    Bar2.render(f5);
    Bar3.render(f5);
    Bar4.render(f5);
    Middle.render(f5);
    Bottom.render(f5);
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
