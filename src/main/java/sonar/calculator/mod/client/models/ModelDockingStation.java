package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDockingStation extends ModelBase {
  //fields
    ModelRenderer Base;
    ModelRenderer Centre;
    ModelRenderer Support1;
    ModelRenderer Support_2;
    ModelRenderer Con2;
    ModelRenderer Con3;
    ModelRenderer Con4;
    ModelRenderer Con5;
    ModelRenderer Con6;
    ModelRenderer Stem1;
    ModelRenderer Stem2;
    ModelRenderer Stem3;
    ModelRenderer Stem4;
    ModelRenderer Centre1;
    ModelRenderer Base2;
    ModelRenderer Centre2;
    ModelRenderer Con1;
  
    public ModelDockingStation() {
    textureWidth = 128;
    textureHeight = 64;
    
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(0F, 0F, 0F, 14, 1, 14);
      Base.setRotationPoint(-7F, 23F, -7F);
      Base.setTextureSize(128, 64);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Centre = new ModelRenderer(this, 0, 26);
      Centre.addBox(0F, 0F, 0F, 2, 12, 1);
      Centre.setRotationPoint(-1F, 11F, 0F);
      Centre.setTextureSize(128, 64);
      Centre.mirror = true;
      setRotation(Centre, 0F, 0F, 0F);
      Support1 = new ModelRenderer(this, 0, 16);
      Support1.addBox(0F, 0F, 0F, 4, 7, 1);
      Support1.setRotationPoint(-2F, 8.9F, 2F);
      Support1.setTextureSize(128, 64);
      Support1.mirror = true;
      setRotation(Support1, -0.7853982F, 0F, 0F);
      Support_2 = new ModelRenderer(this, 0, 24);
      Support_2.addBox(0F, 0F, 0F, 4, 1, 1);
      Support_2.setRotationPoint(-2F, 13F, -3.5F);
      Support_2.setTextureSize(128, 64);
      Support_2.mirror = true;
      setRotation(Support_2, 0.7853982F, 0F, 0F);
      Con2 = new ModelRenderer(this, 0, 52);
      Con2.addBox(0F, 0F, 0F, 1, 6, 6);
      Con2.setRotationPoint(-8F, 13F, -3F);
      Con2.setTextureSize(128, 64);
      Con2.mirror = true;
      setRotation(Con2, 0F, 0F, 0F);
      Con3 = new ModelRenderer(this, 0, 52);
      Con3.addBox(0F, 0F, 0F, 1, 6, 6);
      Con3.setRotationPoint(-3F, 13F, -7F);
      Con3.setTextureSize(128, 64);
      Con3.mirror = true;
      setRotation(Con3, 0F, 1.570796F, 0F);
      Con4 = new ModelRenderer(this, 0, 52);
      Con4.addBox(0F, 0F, 0F, 1, 6, 6);
      Con4.setRotationPoint(-3F, 13F, 8F);
      Con4.setTextureSize(128, 64);
      Con4.mirror = true;
      setRotation(Con4, 0F, 1.570796F, 0F);
      Con5 = new ModelRenderer(this, 0, 0);
      Con5.addBox(0F, 0F, 0F, 2, 2, 14);
      Con5.setRotationPoint(-1F, 15.001F, -7F);
      Con5.setTextureSize(128, 64);
      Con5.mirror = true;
      setRotation(Con5, 0F, 0F, 0F);
      Con6 = new ModelRenderer(this, 0, 0);
      Con6.addBox(0F, 0F, 0F, 2, 2, 14);
      Con6.setRotationPoint(-7F, 15.001F, 1F);
      Con6.setTextureSize(128, 64);
      Con6.mirror = true;
      setRotation(Con6, 0F, 1.570796F, 0F);
      Stem1 = new ModelRenderer(this, 0, 0);
      Stem1.addBox(0F, 0F, 0F, 2, 4, 2);
      Stem1.setRotationPoint(-1F, 18F, -4F);
      Stem1.setTextureSize(128, 64);
      Stem1.mirror = true;
      setRotation(Stem1, 0F, 0F, 0F);
      Stem2 = new ModelRenderer(this, 0, 0);
      Stem2.addBox(0F, 0F, 0F, 2, 4, 2);
      Stem2.setRotationPoint(-1F, 18F, 2F);
      Stem2.setTextureSize(128, 64);
      Stem2.mirror = true;
      setRotation(Stem2, 0F, 0F, 0F);
      Stem3 = new ModelRenderer(this, 0, 0);
      Stem3.addBox(0F, 0F, 0F, 2, 4, 2);
      Stem3.setRotationPoint(3F, 18F, -1F);
      Stem3.setTextureSize(128, 64);
      Stem3.mirror = true;
      setRotation(Stem3, 0F, 0F, 0F);
      Stem4 = new ModelRenderer(this, 0, 0);
      Stem4.addBox(0F, 0F, 0F, 2, 4, 2);
      Stem4.setRotationPoint(-5F, 18F, -1F);
      Stem4.setTextureSize(128, 64);
      Stem4.mirror = true;
      setRotation(Stem4, 0F, 0F, 0F);
      Centre1 = new ModelRenderer(this, 0, 0);
      Centre1.addBox(0F, 0F, 0F, 6, 2, 6);
      Centre1.setRotationPoint(-3F, 15F, -3F);
      Centre1.setTextureSize(128, 64);
      Centre1.mirror = true;
      setRotation(Centre1, 0F, 0F, 0F);
      Base2 = new ModelRenderer(this, 0, 0);
      Base2.addBox(0F, 0F, 0F, 10, 1, 10);
      Base2.setRotationPoint(-5F, 22F, -5F);
      Base2.setTextureSize(128, 64);
      Base2.mirror = true;
      setRotation(Base2, 0F, 0F, 0F);
      Centre2 = new ModelRenderer(this, 0, 0);
      Centre2.addBox(0F, 0F, 0F, 8, 1, 8);
      Centre2.setRotationPoint(-4F, 17F, -4F);
      Centre2.setTextureSize(128, 64);
      Centre2.mirror = true;
      setRotation(Centre2, 0F, 0F, 0F);
      Con1 = new ModelRenderer(this, 0, 52);
      Con1.addBox(0F, 0F, 0F, 1, 6, 6);
      Con1.setRotationPoint(7F, 13F, -3F);
      Con1.setTextureSize(128, 64);
      Con1.mirror = true;
      setRotation(Con1, 0F, 0F, 0F);
  }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Base.render(f5);
    Centre.render(f5);
    Support1.render(f5);
    Support_2.render(f5);
    Con2.render(f5);
    Con3.render(f5);
    Con4.render(f5);
    Con5.render(f5);
    Con6.render(f5);
    Stem1.render(f5);
    Stem2.render(f5);
    Stem3.render(f5);
    Stem4.render(f5);
    Centre1.render(f5);
    Base2.render(f5);
    Centre2.render(f5);
    Con1.render(f5);
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
