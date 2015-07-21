
package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAnalysingChamber extends ModelBase
{
  //fields
    ModelRenderer Base;
    ModelRenderer Support1;
    ModelRenderer Support2;
    ModelRenderer Support3;
    ModelRenderer Support4;
    ModelRenderer Top;
    ModelRenderer Glass1;
    ModelRenderer Glass2;
    ModelRenderer Glass3;
    ModelRenderer Glass4;
  
  public ModelAnalysingChamber()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Base = new ModelRenderer(this, 0, 8);
      Base.addBox(0F, 0F, 0F, 16, 8, 16);
      Base.setRotationPoint(-8F, 16F, -8F);
      Base.setTextureSize(64, 32);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Support1 = new ModelRenderer(this, 0, 0);
      Support1.addBox(0F, 0F, 0F, 2, 6, 2);
      Support1.setRotationPoint(6F, 10F, -8F);
      Support1.setTextureSize(64, 32);
      Support1.mirror = true;
      setRotation(Support1, 0F, 0F, 0F);
      Support2 = new ModelRenderer(this, 0, 0);
      Support2.addBox(0F, 0F, 0F, 2, 6, 2);
      Support2.setRotationPoint(6F, 10F, 6F);
      Support2.setTextureSize(64, 32);
      Support2.mirror = true;
      setRotation(Support2, 0F, 0F, 0F);
      Support3 = new ModelRenderer(this, 0, 0);
      Support3.addBox(0F, 0F, 0F, 2, 6, 2);
      Support3.setRotationPoint(-8F, 10F, 6F);
      Support3.setTextureSize(64, 32);
      Support3.mirror = true;
      setRotation(Support3, 0F, 0F, 0F);
      Support4 = new ModelRenderer(this, 0, 0);
      Support4.addBox(0F, 0F, 0F, 2, 6, 2);
      Support4.setRotationPoint(-8F, 10F, -8F);
      Support4.setTextureSize(64, 32);
      Support4.mirror = true;
      setRotation(Support4, 0F, 0F, 0F);
      Top = new ModelRenderer(this, 0, 8);
      Top.addBox(0F, 0F, 0F, 16, 2, 16);
      Top.setRotationPoint(-8F, 8F, -8F);
      Top.setTextureSize(64, 32);
      Top.mirror = true;
      setRotation(Top, 0F, 0F, 0F);
      Glass1 = new ModelRenderer(this, 16, 0);
      Glass1.addBox(0F, 0F, 0F, 12, 6, 0);
      Glass1.setRotationPoint(-6F, 10F, -7F);
      Glass1.setTextureSize(64, 32);
      Glass1.mirror = true;
      setRotation(Glass1, 0F, 0F, 0F);
      Glass2 = new ModelRenderer(this, 16, 0);
      Glass2.addBox(0F, 0F, 0F, 12, 6, 0);
      Glass2.setRotationPoint(-6F, 10F, 7F);
      Glass2.setTextureSize(64, 32);
      Glass2.mirror = true;
      setRotation(Glass2, 0F, 0F, 0F);
      Glass3 = new ModelRenderer(this, 16, 0);
      Glass3.addBox(0F, 0F, 0F, 12, 6, 0);
      Glass3.setRotationPoint(7F, 10F, 6F);
      Glass3.setTextureSize(64, 32);
      Glass3.mirror = true;
      setRotation(Glass3, 0F, 1.570796F, 0F);
      Glass4 = new ModelRenderer(this, 16, 0);
      Glass4.addBox(0F, 0F, 0F, 12, 6, 0);
      Glass4.setRotationPoint(-7F, 10F, 6F);
      Glass4.setTextureSize(64, 32);
      Glass4.mirror = true;
      setRotation(Glass4, 0F, 1.570796F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Base.render(f5);
    Support1.render(f5);
    Support2.render(f5);
    Support3.render(f5);
    Support4.render(f5);
    Top.render(f5);
    Glass1.render(f5);
    Glass2.render(f5);
    Glass3.render(f5);
    Glass4.render(f5);
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
