
package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelShovel extends ModelBase
{
  //fields
    ModelRenderer Base1;
    ModelRenderer Base2;
    ModelRenderer Handle;
    ModelRenderer Support1;
    ModelRenderer Sword2;
    ModelRenderer Sword3;
    ModelRenderer Sword4;
  
  public ModelShovel()
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
      Handle = new ModelRenderer(this, 30, 0);
      Handle.addBox(0F, 0F, 0F, 1, 10, 1);
      Handle.setRotationPoint(-0.5F, 12F, -0.5F);
      Handle.setTextureSize(64, 32);
      Handle.mirror = true;
      setRotation(Handle, 0F, 0F, 0F);
      Support1 = new ModelRenderer(this, 48, 0);
      Support1.addBox(0F, 0F, 0F, 5, 1, 2);
      Support1.setRotationPoint(-2.5F, 11F, -1F);
      Support1.setTextureSize(64, 32);
      Support1.mirror = true;
      setRotation(Support1, 0F, 0F, 0F);
      Sword2 = new ModelRenderer(this, 7, 1);
      Sword2.addBox(0F, 0F, 0F, 2, 6, 1);
      Sword2.setRotationPoint(-1F, 5F, -0.5F);
      Sword2.setTextureSize(64, 32);
      Sword2.mirror = true;
      setRotation(Sword2, 0F, 0F, 0F);
      Sword3 = new ModelRenderer(this, 14, 0);
      Sword3.addBox(0F, 0F, 0F, 1, 4, 1);
      Sword3.setRotationPoint(-2F, 7F, -0.5F);
      Sword3.setTextureSize(64, 32);
      Sword3.mirror = true;
      setRotation(Sword3, 0F, 0F, 0F);
      Sword4 = new ModelRenderer(this, 18, 0);
      Sword4.addBox(0F, 0F, 0F, 1, 5, 1);
      Sword4.setRotationPoint(1F, 6F, -0.5F);
      Sword4.setTextureSize(64, 32);
      Sword4.mirror = true;
      setRotation(Sword4, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Base1.render(f5);
    Base2.render(f5);
    Handle.render(f5);
    Support1.render(f5);
    Sword2.render(f5);
    Sword3.render(f5);
    Sword4.render(f5);
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
