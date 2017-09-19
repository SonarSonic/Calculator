package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMagneticFlux extends ModelBase {
  //fields
    ModelRenderer Base;
    ModelRenderer Base2;
    ModelRenderer Stand1;
    ModelRenderer Stand2;
    ModelRenderer Magnet1;
    ModelRenderer Magnet2;
    ModelRenderer Magnet3;
  
    public ModelMagneticFlux() {
    textureWidth = 128;
    textureHeight = 64;
    
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(0F, 0F, 0F, 16, 2, 16);
      Base.setRotationPoint(-8F, 22F, -8F);
      Base.setTextureSize(128, 64);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Base2 = new ModelRenderer(this, 0, 18);
      Base2.addBox(0F, 0F, 0F, 14, 1, 14);
      Base2.setRotationPoint(-7F, 21F, -7F);
      Base2.setTextureSize(128, 64);
      Base2.mirror = true;
      setRotation(Base2, 0F, 0F, 0F);
      Stand1 = new ModelRenderer(this, 0, 33);
      Stand1.addBox(0F, 0F, 0F, 8, 1, 8);
      Stand1.setRotationPoint(-4F, 18F, -4F);
      Stand1.setTextureSize(128, 64);
      Stand1.mirror = true;
      setRotation(Stand1, 0F, 0F, 0F);
      Stand2 = new ModelRenderer(this, 32, 33);
      Stand2.addBox(0F, 0F, 0F, 3, 2, 3);
      Stand2.setRotationPoint(-1.5F, 19F, -1.5F);
      Stand2.setTextureSize(128, 64);
      Stand2.mirror = true;
      setRotation(Stand2, 0F, 0F, 0F);
      Magnet1 = new ModelRenderer(this, 0, 42);
      Magnet1.addBox(0F, 0F, 0F, 1, 4, 1);
      Magnet1.setRotationPoint(1F, 12F, -0.5F);
      Magnet1.setTextureSize(128, 64);
      Magnet1.mirror = true;
      setRotation(Magnet1, 0F, 0F, 0F);
      Magnet2 = new ModelRenderer(this, 0, 42);
      Magnet2.addBox(0F, 0F, 0F, 1, 4, 1);
      Magnet2.setRotationPoint(-2F, 12F, -0.5F);
      Magnet2.setTextureSize(128, 64);
      Magnet2.mirror = true;
      setRotation(Magnet2, 0F, 0F, 0F);
      Magnet3 = new ModelRenderer(this, 4, 42);
      Magnet3.addBox(0F, 0F, 0F, 4, 1, 1);
      Magnet3.setRotationPoint(-2F, 16F, -0.5F);
      Magnet3.setTextureSize(128, 64);
      Magnet3.mirror = true;
      setRotation(Magnet3, 0F, 0F, 0F);
  }

    public void renderMagnet(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Magnet1.render(f5);
    Magnet2.render(f5);
    Magnet3.render(f5);
  }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Base.render(f5);
    Base2.render(f5);
    Stand1.render(f5);
    Stand2.render(f5);
  }
  
    private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
    public void setRotationAngles(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }
}
