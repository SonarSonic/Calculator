package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelScarecrow extends ModelBase
{
  //fields
    ModelRenderer Base;
    ModelRenderer Support;
    ModelRenderer Shoulders;
    ModelRenderer Head;
    ModelRenderer Hat1;
    ModelRenderer Hat2;
    ModelRenderer Shape1;
  
  public ModelScarecrow()
  {
    textureWidth = 256;
    textureHeight = 128;
    
      Base = new ModelRenderer(this, 72, 0);
      Base.addBox(0F, 0F, 0F, 14, 1, 14);
      Base.setRotationPoint(-7F, 23F, -7F);
      Base.setTextureSize(256, 128);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Support = new ModelRenderer(this, 60, 0);
      Support.addBox(0F, 0F, 0F, 2, 24, 2);
      Support.setRotationPoint(-1F, -1F, -1F);
      Support.setTextureSize(256, 128);
      Support.mirror = true;
      setRotation(Support, 0F, 0F, 0F);
      Shoulders = new ModelRenderer(this, 68, 15);
      Shoulders.addBox(0F, 0F, 0F, 28, 2, 2);
      Shoulders.setRotationPoint(-14F, 2F, -1F);
      Shoulders.setTextureSize(256, 128);
      Shoulders.mirror = true;
      setRotation(Shoulders, 0F, 0F, 0F);
      Head = new ModelRenderer(this, 0, 0);
      Head.addBox(0F, 0F, 0F, 12, 12, 12);
      Head.setRotationPoint(-6F, -13F, -6F);
      Head.setTextureSize(256, 128);
      Head.mirror = true;
      setRotation(Head, 0F, 0F, 0F);
      Hat1 = new ModelRenderer(this, 72, 33);
      Hat1.addBox(0F, 0F, 0F, 14, 2, 14);
      Hat1.setRotationPoint(-7F, -15F, -7F);
      Hat1.setTextureSize(256, 128);
      Hat1.mirror = true;
      setRotation(Hat1, 0F, 0F, 0F);
      Hat2 = new ModelRenderer(this, 84, 19);
      Hat2.addBox(0F, 0F, 0F, 11, 3, 11);
      Hat2.setRotationPoint(-5.5F, -18F, -5.5F);
      Hat2.setTextureSize(256, 128);
      Hat2.mirror = true;
      setRotation(Hat2, 0F, 0F, 0F);
      Shape1 = new ModelRenderer(this, 128, 0);
      Shape1.addBox(0F, 0F, 0F, 14, 16, 10);
      Shape1.setRotationPoint(-7F, 1F, -5F);
      Shape1.setTextureSize(256, 128);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Base.render(f5);
    Support.render(f5);
    Shoulders.render(f5);
    Head.render(f5);
    Hat1.render(f5);
    Hat2.render(f5);
    Shape1.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  @Override
public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
