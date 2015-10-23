package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAssimilator extends ModelBase
{
  //fields
    ModelRenderer Base1;
    ModelRenderer Sap1;
    ModelRenderer Sap2;
    ModelRenderer Centre2;
    ModelRenderer Centre1;
    ModelRenderer Sap3;
    ModelRenderer Notch2;
    ModelRenderer Notch1;
    ModelRenderer Base2;
    ModelRenderer Notch3;
    ModelRenderer Notch4;
  
  public ModelAssimilator()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      Base1 = new ModelRenderer(this, 64, 47);
      Base1.addBox(0F, 0F, 0F, 16, 1, 16);
      Base1.setRotationPoint(-8F, 23F, -8F);
      Base1.setTextureSize(128, 64);
      Base1.mirror = true;
      setRotation(Base1, 0F, 0F, 0F);
      Sap1 = new ModelRenderer(this, 0, 33);
      Sap1.addBox(0F, 0F, 0F, 6, 6, 1);
      Sap1.setRotationPoint(-3F, 12F, 7F);
      Sap1.setTextureSize(128, 64);
      Sap1.mirror = true;
      setRotation(Sap1, 0F, 0F, 0F);
      Sap2 = new ModelRenderer(this, 0, 24);
      Sap2.addBox(0F, 0F, 0F, 4, 4, 5);
      Sap2.setRotationPoint(-2F, 13F, 2F);
      Sap2.setTextureSize(128, 64);
      Sap2.mirror = true;
      setRotation(Sap2, 0F, 0F, 0F);
      Centre2 = new ModelRenderer(this, 3, 42);
      Centre2.addBox(0F, 0F, 0F, 4, 2, 4);
      Centre2.setRotationPoint(-2F, 19F, -2F);
      Centre2.setTextureSize(128, 64);
      Centre2.mirror = true;
      setRotation(Centre2, 0F, 0F, 0F);
      Centre1 = new ModelRenderer(this, 0, 48);
      Centre1.addBox(0F, 0F, 0F, 8, 8, 8);
      Centre1.setRotationPoint(-4F, 11F, -4F);
      Centre1.setTextureSize(128, 64);
      Centre1.mirror = true;
      setRotation(Centre1, 0F, 0F, 0F);
      Sap3 = new ModelRenderer(this, 0, 13);
      Sap3.addBox(0F, 0F, 0F, 10, 10, 1);
      Sap3.setRotationPoint(-5F, 10F, 6F);
      Sap3.setTextureSize(128, 64);
      Sap3.mirror = true;
      setRotation(Sap3, 0F, 0F, 0F);
      Notch2 = new ModelRenderer(this, 0, 0);
      Notch2.addBox(0F, 0F, 0F, 2, 2, 1);
      Notch2.setRotationPoint(-1F, 14F, -5F);
      Notch2.setTextureSize(128, 64);
      Notch2.mirror = true;
      setRotation(Notch2, 0F, 0F, 0F);
      Notch1 = new ModelRenderer(this, 0, 0);
      Notch1.addBox(0F, 0F, 0F, 2, 2, 1);
      Notch1.setRotationPoint(-1F, 11F, -1F);
      Notch1.setTextureSize(128, 64);
      Notch1.mirror = true;
      setRotation(Notch1, 1.570796F, 0F, 0F);
      Base2 = new ModelRenderer(this, 0, 40);
      Base2.addBox(0F, 0F, 0F, 6, 2, 6);
      Base2.setRotationPoint(-3F, 21F, -3F);
      Base2.setTextureSize(128, 64);
      Base2.mirror = true;
      setRotation(Base2, 0F, 0F, 0F);
      Notch3 = new ModelRenderer(this, 0, 0);
      Notch3.addBox(0F, 0F, 0F, 2, 2, 1);
      Notch3.setRotationPoint(4F, 14F, 1F);
      Notch3.setTextureSize(128, 64);
      Notch3.mirror = true;
      setRotation(Notch3, 0F, 1.570796F, 0F);
      Notch4 = new ModelRenderer(this, 0, 0);
      Notch4.addBox(0F, 0F, 0F, 2, 2, 1);
      Notch4.setRotationPoint(-5F, 14F, 1F);
      Notch4.setTextureSize(128, 64);
      Notch4.mirror = true;
      setRotation(Notch4, 0F, 1.570796F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    Base1.render(f5);
    Sap1.render(f5);
    Sap2.render(f5);
    Centre2.render(f5);
    Centre1.render(f5);
    Sap3.render(f5);
    Notch2.render(f5);
    Notch1.render(f5);
    Base2.render(f5);
    Notch3.render(f5);
    Notch4.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}
