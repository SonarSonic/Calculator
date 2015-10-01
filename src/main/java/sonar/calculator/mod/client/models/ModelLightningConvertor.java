
package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLightningConvertor extends ModelBase
{
    ModelRenderer Lightning;
    ModelRenderer Base;
    ModelRenderer Middle1;
    ModelRenderer Power1;
    ModelRenderer Power2;
    ModelRenderer Stable1;
    ModelRenderer Stable2;
    ModelRenderer Stable3;
    ModelRenderer Stable4;
    ModelRenderer Middle2;
    ModelRenderer Power3;
  
  public ModelLightningConvertor()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      Lightning = new ModelRenderer(this, 0, 23);
      Lightning.addBox(0F, 0F, 0F, 6, 6, 1);
      Lightning.setRotationPoint(-3F, 16F, 7F);
      Lightning.setTextureSize(128, 64);
      Lightning.mirror = true;
      setRotation(Lightning, 0F, 0F, 0F);
      Base = new ModelRenderer(this, 0, 47);
      Base.addBox(0F, 0F, 0F, 16, 1, 16);
      Base.setRotationPoint(-8F, 23F, -8F);
      Base.setTextureSize(128, 64);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Middle1 = new ModelRenderer(this, 0, 30);
      Middle1.addBox(0F, 0F, 0F, 4, 3, 14);
      Middle1.setRotationPoint(-2F, 20F, -7F);
      Middle1.setTextureSize(128, 64);
      Middle1.mirror = true;
      setRotation(Middle1, 0F, 0F, 0F);
      Power1 = new ModelRenderer(this, 110, 0);
      Power1.addBox(0F, 0F, 0F, 8, 9, 1);
      Power1.setRotationPoint(-4F, 13F, -8F);
      Power1.setTextureSize(128, 64);
      Power1.mirror = true;
      setRotation(Power1, 0F, 0F, 0F);
      Power2 = new ModelRenderer(this, 96, 0);
      Power2.addBox(0F, 0F, 0F, 6, 7, 1);
      Power2.setRotationPoint(-3F, 14F, -7F);
      Power2.setTextureSize(128, 64);
      Power2.mirror = true;
      setRotation(Power2, 0F, 0F, 0F);
      Stable1 = new ModelRenderer(this, 0, 0);
      Stable1.addBox(0F, 0F, 0F, 10, 1, 1);
      Stable1.setRotationPoint(-5F, 22F, -6F);
      Stable1.setTextureSize(128, 64);
      Stable1.mirror = true;
      setRotation(Stable1, 0F, 0F, 0F);
      Stable2 = new ModelRenderer(this, 0, 0);
      Stable2.addBox(0F, 0F, 0F, 10, 1, 1);
      Stable2.setRotationPoint(-5F, 22F, 5F);
      Stable2.setTextureSize(128, 64);
      Stable2.mirror = true;
      setRotation(Stable2, 0F, 0F, 0F);
      Stable3 = new ModelRenderer(this, 0, 0);
      Stable3.addBox(0F, 0F, 0F, 12, 1, 1);
      Stable3.setRotationPoint(-6F, 22F, 1F);
      Stable3.setTextureSize(128, 64);
      Stable3.mirror = true;
      setRotation(Stable3, 0F, 0F, 0F);
      Stable4 = new ModelRenderer(this, 0, 0);
      Stable4.addBox(0F, 0F, 0F, 12, 1, 1);
      Stable4.setRotationPoint(-6F, 22F, -2F);
      Stable4.setTextureSize(128, 64);
      Stable4.mirror = true;
      setRotation(Stable4, 0F, 0F, 0F);
      Middle2 = new ModelRenderer(this, 0, 4);
      Middle2.addBox(0F, 0F, 0F, 2, 1, 13);
      Middle2.setRotationPoint(-1F, 19F, -6F);
      Middle2.setTextureSize(128, 64);
      Middle2.mirror = true;
      setRotation(Middle2, 0F, 0F, 0F);
      Power3 = new ModelRenderer(this, 14, 40);
      Power3.addBox(0F, 0F, 0F, 2, 1, 2);
      Power3.setRotationPoint(-1F, 18F, -6F);
      Power3.setTextureSize(128, 64);
      Power3.mirror = true;
      setRotation(Power3, 0F, 0F, 0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Lightning.render(f5);
    Base.render(f5);
    Middle1.render(f5);
    Power1.render(f5);
    Power2.render(f5);
    Stable1.render(f5);
    Stable2.render(f5);
    Stable3.render(f5);
    Stable4.render(f5);
    Middle2.render(f5);
    Power3.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
 @Override
public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) { super.setRotationAngles(f, f1, f2, f3, f4, f5, entity); }
  
  public void renderModel(float f5) {
	    Lightning.render(f5);
	    Base.render(f5);
	    Middle1.render(f5);
	    Power1.render(f5);
	    Power2.render(f5);
	    Stable1.render(f5);
	    Stable2.render(f5);
	    Stable3.render(f5);
	    Stable4.render(f5);
	    Middle2.render(f5);
	    Power3.render(f5);
  }
}
