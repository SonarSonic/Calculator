package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelResearchChamber extends ModelBase {
  //fields
    ModelRenderer Base1;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Table1;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Arm1;
    ModelRenderer Arm2;
    ModelRenderer Table2;
    ModelRenderer Glass1;
    ModelRenderer Glass2;
    ModelRenderer Glass3;
    ModelRenderer Glass4;
    ModelRenderer Glass;
  
    public ModelResearchChamber() {
    textureWidth = 128;
    textureHeight = 64;
    
      Base1 = new ModelRenderer(this, 0, 47);
      Base1.addBox(0F, 0F, 0F, 14, 1, 16);
      Base1.setRotationPoint(-7F, 23F, -8F);
      Base1.setTextureSize(128, 64);
      Base1.mirror = true;
      setRotation(Base1, 0F, 0F, 0F);
      Leg1 = new ModelRenderer(this, 0, 36);
      Leg1.addBox(0F, 0F, 0F, 1, 5, 6);
      Leg1.setRotationPoint(5F, 18F, -7F);
      Leg1.setTextureSize(128, 64);
      Leg1.mirror = true;
      setRotation(Leg1, 0F, 0F, 0F);
      Leg2 = new ModelRenderer(this, 0, 36);
      Leg2.addBox(0F, 0F, 0F, 1, 5, 6);
      Leg2.setRotationPoint(-6F, 18F, -7F);
      Leg2.setTextureSize(128, 64);
      Leg2.mirror = true;
      setRotation(Leg2, 0F, 0F, 0F);
      Table1 = new ModelRenderer(this, 0, 0);
      Table1.addBox(0F, 0F, 0F, 16, 1, 16);
      Table1.setRotationPoint(-8F, 17F, -8F);
      Table1.setTextureSize(128, 64);
      Table1.mirror = true;
      setRotation(Table1, 0F, 0F, 0F);
      Leg3 = new ModelRenderer(this, 0, 36);
      Leg3.addBox(0F, 0F, 0F, 1, 5, 6);
      Leg3.setRotationPoint(5F, 18F, 1F);
      Leg3.setTextureSize(128, 64);
      Leg3.mirror = true;
      setRotation(Leg3, 0F, 0F, 0F);
      Leg4 = new ModelRenderer(this, 0, 36);
      Leg4.addBox(0F, 0F, 0F, 1, 5, 6);
      Leg4.setRotationPoint(-6F, 18F, 1F);
      Leg4.setTextureSize(128, 64);
      Leg4.mirror = true;
      setRotation(Leg4, 0F, 0F, 0F);
      Arm1 = new ModelRenderer(this, 14, 38);
      Arm1.addBox(0F, 0F, 0F, 4, 8, 1);
      Arm1.setRotationPoint(-2F, 10F, 1.2F);
      Arm1.setTextureSize(128, 64);
      Arm1.mirror = true;
      setRotation(Arm1, 0.7853982F, 0F, 0F);
      Arm2 = new ModelRenderer(this, 0, 34);
      Arm2.addBox(0F, 0F, 0F, 2, 1, 1);
      Arm2.setRotationPoint(-1F, 15F, 6F);
      Arm2.setTextureSize(128, 64);
      Arm2.mirror = true;
      setRotation(Arm2, 0F, 0F, 0F);
      Table2 = new ModelRenderer(this, 0, 17);
      Table2.addBox(0F, 0F, 0F, 14, 1, 14);
      Table2.setRotationPoint(-7F, 16F, -7F);
      Table2.setTextureSize(128, 64);
      Table2.mirror = true;
      setRotation(Table2, 0F, 0F, 0F);
      Glass1 = new ModelRenderer(this, 16, 0);
      Glass1.addBox(0F, 0F, 0F, 5, 1, 1);
      Glass1.setRotationPoint(-2.5F, 9F, 1F);
      Glass1.setTextureSize(128, 64);
      Glass1.mirror = true;
      setRotation(Glass1, 0F, 0F, 0F);
      Glass2 = new ModelRenderer(this, 16, 0);
      Glass2.addBox(0F, 0F, 0F, 5, 1, 1);
      Glass2.setRotationPoint(-2.5F, 9F, -4F);
      Glass2.setTextureSize(128, 64);
      Glass2.mirror = true;
      setRotation(Glass2, 0F, 0F, 0F);
      Glass3 = new ModelRenderer(this, 16, 0);
      Glass3.addBox(0F, 0F, 0F, 4, 1, 1);
      Glass3.setRotationPoint(-2.5F, 9F, 1F);
      Glass3.setTextureSize(128, 64);
      Glass3.mirror = true;
      setRotation(Glass3, 0F, 1.570796F, 0F);
      Glass4 = new ModelRenderer(this, 16, 0);
      Glass4.addBox(0F, 0F, 0F, 4, 1, 1);
      Glass4.setRotationPoint(1.5F, 9F, 1F);
      Glass4.setTextureSize(128, 64);
      Glass4.mirror = true;
      setRotation(Glass4, 0F, 1.570796F, 0F);
      Glass = new ModelRenderer(this, 0, 0);
      Glass.addBox(0F, 0F, 0F, 3, 1, 4);
      Glass.setRotationPoint(-1.5F, 9F, -3F);
      Glass.setTextureSize(128, 64);
      Glass.mirror = true;
      setRotation(Glass, 0F, 0F, 0F);
  }
  
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Base1.render(f5);
    Leg1.render(f5);
    Leg2.render(f5);
    Table1.render(f5);
    Leg3.render(f5);
    Leg4.render(f5);
    Arm1.render(f5);
    Arm2.render(f5);
    Table2.render(f5);
    Glass1.render(f5);
    Glass2.render(f5);
    Glass3.render(f5);
    Glass4.render(f5);
    Glass.render(f5);
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
