package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelProcessing extends ModelBase {
  //fields
    ModelRenderer Scanner1;
    ModelRenderer Scanner2;
    ModelRenderer Scanner3;
  
    public ModelProcessing() {
    textureWidth = 64;
    textureHeight = 32;
      Scanner1 = new ModelRenderer(this, 0, 0);
      Scanner1.addBox(0F, 0F, 0F, 1, 4, 3);
      Scanner1.setRotationPoint(-4F, 15F, 3F);
      Scanner1.setTextureSize(64, 32);
      Scanner1.mirror = true;
      setRotation(Scanner1, 0F, 0F, 0F);
      Scanner2 = new ModelRenderer(this, 0, 0);
      Scanner2.addBox(0F, 0F, 0F, 1, 4, 3);
      Scanner2.setRotationPoint(3F, 15F, 3F);
      Scanner2.setTextureSize(64, 32);
      Scanner2.mirror = true;
      setRotation(Scanner2, 0F, 0F, 0F);
      Scanner3 = new ModelRenderer(this, 0, 0);
      Scanner3.addBox(0F, 0F, 0F, 1, 7, 3);
      Scanner3.setRotationPoint(3.5F, 14F, 3F);
      Scanner3.setTextureSize(64, 32);
      Scanner3.mirror = true;
      setRotation(Scanner3, 0F, 0F, 1.570796F);
  }
  
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Scanner1.render(f5);
    Scanner2.render(f5);
    Scanner3.render(f5);
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
