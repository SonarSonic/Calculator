package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSword extends ModelBase
{
  //fields
    ModelRenderer Base1;
    ModelRenderer Base2;
    ModelRenderer Handle;
    ModelRenderer Support1;
    ModelRenderer Support2;
    ModelRenderer Support3;
    ModelRenderer Sword1;
    ModelRenderer Sword2;
    ModelRenderer Sword3;
    ModelRenderer Sword4;
    ModelRenderer Sword5;
    ModelRenderer Support4;
    ModelRenderer Support5;
    ModelRenderer Support6;
    ModelRenderer Support7;
  
  public ModelSword()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Base1 = new ModelRenderer(this, 4, 28);
      Base1.addBox(0F, 0F, 0F, 3, 1, 3);
      Base1.setRotationPoint(-1.5F, 22F, -1.5F);
      Base1.setTextureSize(64, 32);
      Base1.mirror = true;
      setRotation(Base1, 0F, 0F, 0F);
      Base2 = new ModelRenderer(this, 4, 25);
      Base2.addBox(0F, 0F, 0F, 2, 1, 2);
      Base2.setRotationPoint(-1F, 23F, -1F);
      Base2.setTextureSize(64, 32);
      Base2.mirror = true;
      setRotation(Base2, 0F, 0F, 0F);
      Handle = new ModelRenderer(this, 0, 26);
      Handle.addBox(0F, 0F, 0F, 1, 5, 1);
      Handle.setRotationPoint(-0.5F, 17F, -0.5F);
      Handle.setTextureSize(64, 32);
      Handle.mirror = true;
      setRotation(Handle, 0F, 0F, 0F);
      Support1 = new ModelRenderer(this, 48, 0);
      Support1.addBox(0F, 0F, 0F, 6, 1, 2);
      Support1.setRotationPoint(-3F, 16F, -1F);
      Support1.setTextureSize(64, 32);
      Support1.mirror = true;
      setRotation(Support1, 0F, 0F, 0F);
      Support2 = new ModelRenderer(this, 40, 0);
      Support2.addBox(0F, 0F, 0F, 1, 3, 3);
      Support2.setRotationPoint(-4F, 15F, -1.5F);
      Support2.setTextureSize(64, 32);
      Support2.mirror = true;
      setRotation(Support2, 0F, 0F, -0.3490659F);
      Support3 = new ModelRenderer(this, 40, 0);
      Support3.addBox(0F, 0F, 0F, 1, 3, 3);
      Support3.setRotationPoint(4F, 15F, 1.5F);
      Support3.setTextureSize(64, 32);
      Support3.mirror = true;
      setRotation(Support3, 0F, 3.141593F, 0.3490659F);
      Sword1 = new ModelRenderer(this, 0, 0);
      Sword1.addBox(0F, 0F, 0F, 2, 12, 1);
      Sword1.setRotationPoint(-1F, 4F, -0.5F);
      Sword1.setTextureSize(64, 32);
      Sword1.mirror = true;
      setRotation(Sword1, 0F, 0F, 0F);
      Sword2 = new ModelRenderer(this, 6, 0);
      Sword2.addBox(0F, 0F, 0F, 2, 6, 2);
      Sword2.setRotationPoint(-1F, 10F, -1F);
      Sword2.setTextureSize(64, 32);
      Sword2.mirror = true;
      setRotation(Sword2, 0F, 0F, 0F);
      Sword3 = new ModelRenderer(this, 14, 0);
      Sword3.addBox(0F, 0F, 0F, 1, 7, 1);
      Sword3.setRotationPoint(-2F, 9F, -0.5F);
      Sword3.setTextureSize(64, 32);
      Sword3.mirror = true;
      setRotation(Sword3, 0F, 0F, 0F);
      Sword4 = new ModelRenderer(this, 18, 0);
      Sword4.addBox(0F, 0F, 0F, 1, 9, 1);
      Sword4.setRotationPoint(1F, 7F, -0.5F);
      Sword4.setTextureSize(64, 32);
      Sword4.mirror = true;
      setRotation(Sword4, 0F, 0F, 0F);
      Sword5 = new ModelRenderer(this, 22, 0);
      Sword5.addBox(0F, 0F, 0F, 1, 1, 1);
      Sword5.setRotationPoint(0F, 3F, -0.5F);
      Sword5.setTextureSize(64, 32);
      Sword5.mirror = true;
      setRotation(Sword5, 0F, 0F, 0F);
      Support4 = new ModelRenderer(this, 34, 0);
      Support4.addBox(0F, 0F, 0F, 1, 1, 2);
      Support4.setRotationPoint(3F, 14F, -1F);
      Support4.setTextureSize(64, 32);
      Support4.mirror = true;
      setRotation(Support4, 0F, 0F, 0F);
      Support5 = new ModelRenderer(this, 34, 0);
      Support5.addBox(0F, 0F, 0F, 1, 1, 2);
      Support5.setRotationPoint(-4F, 14F, -1F);
      Support5.setTextureSize(64, 32);
      Support5.mirror = true;
      setRotation(Support5, 0F, 0F, 0F);
      Support6 = new ModelRenderer(this, 30, 0);
      Support6.addBox(0F, 0F, 0F, 1, 2, 1);
      Support6.setRotationPoint(-4F, 12F, -0.5F);
      Support6.setTextureSize(64, 32);
      Support6.mirror = true;
      setRotation(Support6, 0F, 0F, 0F);
      Support7 = new ModelRenderer(this, 30, 0);
      Support7.addBox(0F, 0F, 0F, 1, 5, 1);
      Support7.setRotationPoint(3F, 9F, -0.5F);
      Support7.setTextureSize(64, 32);
      Support7.mirror = true;
      setRotation(Support7, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Base1.render(f5);
    Base2.render(f5);
    Handle.render(f5);
    Support1.render(f5);
    Support2.render(f5);
    Support3.render(f5);
    Sword1.render(f5);
    Sword2.render(f5);
    Sword3.render(f5);
    Sword4.render(f5);
    Sword5.render(f5);
    Support4.render(f5);
    Support5.render(f5);
    Support6.render(f5);
    Support7.render(f5);
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
