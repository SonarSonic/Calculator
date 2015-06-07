package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPickaxe extends ModelBase
{
  //fields
    ModelRenderer Base1;
    ModelRenderer Base2;
    ModelRenderer Handle;
    ModelRenderer Axe1;
    ModelRenderer Axe2;
    ModelRenderer Axe3;
    ModelRenderer Axe4;
    ModelRenderer Axe5;
    ModelRenderer Axe6;
    ModelRenderer Axe7;
  
  public ModelPickaxe()
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
      Handle.addBox(0F, 0F, 0F, 1, 13, 1);
      Handle.setRotationPoint(-0.5F, 9F, -0.5F);
      Handle.setTextureSize(64, 32);
      Handle.mirror = true;
      setRotation(Handle, 0F, 0F, 0F);
      Axe1 = new ModelRenderer(this, 0, 7);
      Axe1.addBox(0F, 0F, 0F, 3, 3, 3);
      Axe1.setRotationPoint(-1.5F, 6F, -1.5F);
      Axe1.setTextureSize(64, 32);
      Axe1.mirror = true;
      setRotation(Axe1, 0F, 0F, 0F);
      Axe2 = new ModelRenderer(this, 0, 0);
      Axe2.addBox(0F, 0F, 0F, 5, 1, 2);
      Axe2.setRotationPoint(2.5F, 7F, -1F);
      Axe2.setTextureSize(64, 32);
      Axe2.mirror = true;
      setRotation(Axe2, 0F, 0F, 0.5235988F);
      Axe3 = new ModelRenderer(this, 0, 0);
      Axe3.addBox(0F, 0F, 0F, 5, 1, 2);
      Axe3.setRotationPoint(-2.5F, 7F, 1F);
      Axe3.setTextureSize(64, 32);
      Axe3.mirror = true;
      setRotation(Axe3, 0F, 3.141593F, -0.5235988F);
      Axe4 = new ModelRenderer(this, 0, 0);
      Axe4.addBox(0F, 0F, 0F, 5, 1, 2);
      Axe4.setRotationPoint(-2.5F, 7F, -1F);
      Axe4.setTextureSize(64, 32);
      Axe4.mirror = true;
      setRotation(Axe4, 0F, 0F, 0F);
      Axe5 = new ModelRenderer(this, 14, 0);
      Axe5.addBox(0F, 0F, 0F, 3, 2, 1);
      Axe5.setRotationPoint(2.5F, 6.5F, -0.5F);
      Axe5.setTextureSize(64, 32);
      Axe5.mirror = true;
      setRotation(Axe5, 0F, 0F, 0.5235988F);
      Axe6 = new ModelRenderer(this, 14, 0);
      Axe6.addBox(0F, 0F, 0F, 3, 2, 1);
      Axe6.setRotationPoint(-2.5F, 6.5F, 0.5F);
      Axe6.setTextureSize(64, 32);
      Axe6.mirror = true;
      setRotation(Axe6, 0F, -3.141593F, -0.5235988F);
      Axe7 = new ModelRenderer(this, 14, 0);
      Axe7.addBox(0F, 0F, 0F, 6, 2, 1);
      Axe7.setRotationPoint(-3F, 6.5F, -0.5F);
      Axe7.setTextureSize(64, 32);
      Axe7.mirror = true;
      setRotation(Axe7, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Base1.render(f5);
    Base2.render(f5);
    Handle.render(f5);
    Axe1.render(f5);
    Axe2.render(f5);
    Axe3.render(f5);
    Axe4.render(f5);
    Axe5.render(f5);
    Axe6.render(f5);
    Axe7.render(f5);
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
