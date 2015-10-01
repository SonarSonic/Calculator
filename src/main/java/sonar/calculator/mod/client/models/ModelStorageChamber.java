package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelStorageChamber extends ModelBase
{
  //fields
    ModelRenderer Side1;
    ModelRenderer Side2;
    ModelRenderer Side3;
    ModelRenderer Top;
    ModelRenderer Bottom;
    ModelRenderer Middle;
    ModelRenderer Drive1;
    ModelRenderer Drive2;
    ModelRenderer Drive3;
    ModelRenderer Drive4;
    ModelRenderer Drive5;
    ModelRenderer Drive6;
    ModelRenderer Drive7;
    ModelRenderer Drive8;
    ModelRenderer Drive9;
    ModelRenderer Drive10;
    ModelRenderer Drive11;
    ModelRenderer Drive12;
    ModelRenderer Drive13;
    ModelRenderer Drive14;
  
  public ModelStorageChamber()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Side1 = new ModelRenderer(this, 0, 0);
      Side1.addBox(0F, 0F, 0F, 16, 16, 1);
      Side1.setRotationPoint(-8F, 8F, 7F);
      Side1.setTextureSize(64, 32);
      Side1.mirror = true;
      setRotation(Side1, 0F, 0F, 0F);
      Side2 = new ModelRenderer(this, 0, 0);
      Side2.addBox(0F, 0F, 0F, 16, 16, 1);
      Side2.setRotationPoint(7F, 8F, 8F);
      Side2.setTextureSize(64, 32);
      Side2.mirror = true;
      setRotation(Side2, 0F, 1.570796F, 0F);
      Side3 = new ModelRenderer(this, 0, 0);
      Side3.addBox(0F, 0F, 0F, 16, 16, 1);
      Side3.setRotationPoint(-8F, 8F, 8F);
      Side3.setTextureSize(64, 32);
      Side3.mirror = true;
      setRotation(Side3, 0F, 1.570796F, 0F);
      Top = new ModelRenderer(this, 0, 0);
      Top.addBox(0F, 0F, 0F, 16, 16, 1);
      Top.setRotationPoint(-8F, 9F, -8F);
      Top.setTextureSize(64, 32);
      Top.mirror = true;
      setRotation(Top, 1.570796F, 0F, 0F);
      Bottom = new ModelRenderer(this, 0, 0);
      Bottom.addBox(0F, 0F, 0F, 16, 16, 1);
      Bottom.setRotationPoint(-8F, 24F, -8F);
      Bottom.setTextureSize(64, 32);
      Bottom.mirror = true;
      setRotation(Bottom, 1.570796F, 0F, 0F);
      Middle = new ModelRenderer(this, 0, 17);
      Middle.addBox(0F, 0F, 0F, 2, 14, 1);
      Middle.setRotationPoint(-1F, 9F, -6.5F);
      Middle.setTextureSize(64, 32);
      Middle.mirror = true;
      setRotation(Middle, 0F, 0F, 0F);
      Drive1 = new ModelRenderer(this, 6, 17);
      Drive1.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive1.setRotationPoint(1F, 9F, -6F);
      Drive1.setTextureSize(64, 32);
      Drive1.mirror = true;
      setRotation(Drive1, 0F, 0F, 0F);
      Drive2 = new ModelRenderer(this, 6, 17);
      Drive2.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive2.setRotationPoint(1F, 11F, -6F);
      Drive2.setTextureSize(64, 32);
      Drive2.mirror = true;
      setRotation(Drive2, 0F, 0F, 0F);
      Drive3 = new ModelRenderer(this, 6, 17);
      Drive3.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive3.setRotationPoint(1F, 13F, -6F);
      Drive3.setTextureSize(64, 32);
      Drive3.mirror = true;
      setRotation(Drive3, 0F, 0F, 0F);
      Drive4 = new ModelRenderer(this, 6, 17);
      Drive4.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive4.setRotationPoint(1F, 15F, -6F);
      Drive4.setTextureSize(64, 32);
      Drive4.mirror = true;
      setRotation(Drive4, 0F, 0F, 0F);
      Drive5 = new ModelRenderer(this, 6, 17);
      Drive5.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive5.setRotationPoint(1F, 17F, -6F);
      Drive5.setTextureSize(64, 32);
      Drive5.mirror = true;
      setRotation(Drive5, 0F, 0F, 0F);
      Drive6 = new ModelRenderer(this, 6, 17);
      Drive6.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive6.setRotationPoint(1F, 19F, -6F);
      Drive6.setTextureSize(64, 32);
      Drive6.mirror = true;
      setRotation(Drive6, 0F, 0F, 0F);
      Drive7 = new ModelRenderer(this, 6, 17);
      Drive7.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive7.setRotationPoint(1F, 21F, -6F);
      Drive7.setTextureSize(64, 32);
      Drive7.mirror = true;
      setRotation(Drive7, 0F, 0F, 0F);
      Drive8 = new ModelRenderer(this, 6, 17);
      Drive8.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive8.setRotationPoint(-7F, 9F, -6F);
      Drive8.setTextureSize(64, 32);
      Drive8.mirror = true;
      setRotation(Drive8, 0F, 0F, 0F);
      Drive9 = new ModelRenderer(this, 6, 17);
      Drive9.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive9.setRotationPoint(-7F, 11F, -6F);
      Drive9.setTextureSize(64, 32);
      Drive9.mirror = true;
      setRotation(Drive9, 0F, 0F, 0F);
      Drive10 = new ModelRenderer(this, 6, 17);
      Drive10.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive10.setRotationPoint(-7F, 13F, -6F);
      Drive10.setTextureSize(64, 32);
      Drive10.mirror = true;
      setRotation(Drive10, 0F, 0F, 0F);
      Drive11 = new ModelRenderer(this, 6, 17);
      Drive11.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive11.setRotationPoint(-7F, 15F, -6F);
      Drive11.setTextureSize(64, 32);
      Drive11.mirror = true;
      setRotation(Drive11, 0F, 0F, 0F);
      Drive12 = new ModelRenderer(this, 6, 17);
      Drive12.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive12.setRotationPoint(-7F, 17F, -6F);
      Drive12.setTextureSize(64, 32);
      Drive12.mirror = true;
      setRotation(Drive12, 0F, 0F, 0F);
      Drive13 = new ModelRenderer(this, 6, 17);
      Drive13.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive13.setRotationPoint(-7F, 19F, -6F);
      Drive13.setTextureSize(64, 32);
      Drive13.mirror = true;
      setRotation(Drive13, 0F, 0F, 0F);
      Drive14 = new ModelRenderer(this, 6, 17);
      Drive14.addBox(0F, 0F, 0F, 6, 2, 1);
      Drive14.setRotationPoint(-7F, 21F, -6F);
      Drive14.setTextureSize(64, 32);
      Drive14.mirror = true;
      setRotation(Drive14, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Side1.render(f5);
    Side2.render(f5);
    Side3.render(f5);
    Top.render(f5);
    Bottom.render(f5);
    Middle.render(f5);
    Drive1.render(f5);
    Drive2.render(f5);
    Drive3.render(f5);
    Drive4.render(f5);
    Drive5.render(f5);
    Drive6.render(f5);
    Drive7.render(f5);
    Drive8.render(f5);
    Drive9.render(f5);
    Drive10.render(f5);
    Drive11.render(f5);
    Drive12.render(f5);
    Drive13.render(f5);
    Drive14.render(f5);
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
