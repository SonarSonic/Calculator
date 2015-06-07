package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSickle extends ModelBase
{
  //fields
    ModelRenderer Base;
    ModelRenderer Handle1;
    ModelRenderer Sickle1;
    ModelRenderer Handle2;
    ModelRenderer Sickle2;
    ModelRenderer Sickle3;
    ModelRenderer Sickle4;
    ModelRenderer Sickle5;
  
  public ModelSickle()
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
      Handle1.addBox(0F, 0F, 0F, 1, 6, 1);
      Handle1.setRotationPoint(-0.5F, 16F, -0.5F);
      Handle1.setTextureSize(64, 32);
      Handle1.mirror = true;
      setRotation(Handle1, 0F, 0F, 0F);
      Sickle1 = new ModelRenderer(this, 0, 3);
      Sickle1.addBox(0F, 0F, 0F, 5, 1, 1);
      Sickle1.setRotationPoint(3F, 11F, 0.5F);
      Sickle1.setTextureSize(64, 32);
      Sickle1.mirror = true;
      setRotation(Sickle1, 0F, 3.141593F, -0.7853982F);
      Handle2 = new ModelRenderer(this, 0, 24);
      Handle2.addBox(0F, 0F, 0F, 2, 2, 2);
      Handle2.setRotationPoint(-1F, 14F, -1F);
      Handle2.setTextureSize(64, 32);
      Handle2.mirror = true;
      setRotation(Handle2, 0F, 0F, 0F);
      Sickle2 = new ModelRenderer(this, 0, 3);
      Sickle2.addBox(0F, 0F, 0F, 3, 1, 1);
      Sickle2.setRotationPoint(3.7F, 8.7F, -0.5F);
      Sickle2.setTextureSize(64, 32);
      Sickle2.mirror = true;
      setRotation(Sickle2, 0F, 0F, 1.570796F);
      Sickle3 = new ModelRenderer(this, 0, 0);
      Sickle3.addBox(0F, 0F, 0F, 3, 1, 1);
      Sickle3.setRotationPoint(1.6F, 6.6F, -0.5F);
      Sickle3.setTextureSize(64, 32);
      Sickle3.mirror = true;
      setRotation(Sickle3, 0F, 0F, 0.7853982F);
      Sickle4 = new ModelRenderer(this, 0, 0);
      Sickle4.addBox(0F, 0F, 0F, 3, 1, 1);
      Sickle4.setRotationPoint(-1.4F, 6.6F, -0.5F);
      Sickle4.setTextureSize(64, 32);
      Sickle4.mirror = true;
      setRotation(Sickle4, 0F, 0F, 0F);
      Sickle5 = new ModelRenderer(this, 0, 0);
      Sickle5.addBox(0F, 0F, 0F, 3, 1, 1);
      Sickle5.setRotationPoint(-4F, 8.1F, -0.5F);
      Sickle5.setTextureSize(64, 32);
      Sickle5.mirror = true;
      setRotation(Sickle5, 0F, 0F, -0.5235988F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Base.render(f5);
    Handle1.render(f5);
    Sickle1.render(f5);
    Handle2.render(f5);
    Sickle2.render(f5);
    Sickle3.render(f5);
    Sickle4.render(f5);
    Sickle5.render(f5);
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
