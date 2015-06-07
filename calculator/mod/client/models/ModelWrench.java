
package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWrench extends ModelBase
{
  //fields
    ModelRenderer Base;
    ModelRenderer Handle1;
    ModelRenderer Wrench1;
    ModelRenderer Wrench2;
    ModelRenderer Wrench3;
    ModelRenderer Wrench4;
    ModelRenderer Handle2;
    ModelRenderer Wrench5;
    ModelRenderer Wrench6;
  
  public ModelWrench()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Base = new ModelRenderer(this, 0, 28);
      Base.addBox(0F, 0F, 0F, 2, 2, 2);
      Base.setRotationPoint(-1F, 22F, -1F);
      Base.setTextureSize(64, 32);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Handle1 = new ModelRenderer(this, 8, 20);
      Handle1.addBox(0F, 0F, 0F, 1, 11, 1);
      Handle1.setRotationPoint(-0.5F, 11F, -0.5F);
      Handle1.setTextureSize(64, 32);
      Handle1.mirror = true;
      setRotation(Handle1, 0F, 0F, 0F);
      Wrench1 = new ModelRenderer(this, 0, 3);
      Wrench1.addBox(0F, 0F, 0F, 3, 1, 1);
      Wrench1.setRotationPoint(-2F, 7F, -0.5F);
      Wrench1.setTextureSize(64, 32);
      Wrench1.mirror = true;
      setRotation(Wrench1, 0F, 0F, 0.7853982F);
      Wrench2 = new ModelRenderer(this, 0, 3);
      Wrench2.addBox(0F, 0F, 0F, 3, 1, 1);
      Wrench2.setRotationPoint(2F, 7F, 0.5F);
      Wrench2.setTextureSize(64, 32);
      Wrench2.mirror = true;
      setRotation(Wrench2, 0F, 3.141593F, -0.7853982F);
      Wrench3 = new ModelRenderer(this, 0, 0);
      Wrench3.addBox(0F, 0F, 0F, 1, 2, 1);
      Wrench3.setRotationPoint(-2.7F, 5.7F, -0.5F);
      Wrench3.setTextureSize(64, 32);
      Wrench3.mirror = true;
      setRotation(Wrench3, 0F, 0F, 0F);
      Wrench4 = new ModelRenderer(this, 0, 0);
      Wrench4.addBox(0F, 0F, 0F, 1, 2, 1);
      Wrench4.setRotationPoint(1.7F, 5.7F, -0.5F);
      Wrench4.setTextureSize(64, 32);
      Wrench4.mirror = true;
      setRotation(Wrench4, 0F, 0F, 0F);
      Handle2 = new ModelRenderer(this, 0, 24);
      Handle2.addBox(0F, 0F, 0F, 2, 2, 2);
      Handle2.setRotationPoint(-1F, 9F, -1F);
      Handle2.setTextureSize(64, 32);
      Handle2.mirror = true;
      setRotation(Handle2, 0F, 0F, 0F);
      Wrench5 = new ModelRenderer(this, 0, 0);
      Wrench5.addBox(0F, 0F, 0F, 1, 1, 1);
      Wrench5.setRotationPoint(2F, 5F, -0.5F);
      Wrench5.setTextureSize(64, 32);
      Wrench5.mirror = true;
      setRotation(Wrench5, 0F, 0F, 0.7853982F);
      Wrench6 = new ModelRenderer(this, 0, 0);
      Wrench6.addBox(0F, 0F, 0F, 1, 1, 1);
      Wrench6.setRotationPoint(-2F, 5F, 0.5F);
      Wrench6.setTextureSize(64, 32);
      Wrench6.mirror = true;
      setRotation(Wrench6, 0F, 3.141593F, -0.7853982F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Base.render(f5);
    Handle1.render(f5);
    Wrench1.render(f5);
    Wrench2.render(f5);
    Wrench3.render(f5);
    Wrench4.render(f5);
    Handle2.render(f5);
    Wrench5.render(f5);
    Wrench6.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(Entity entity,float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
