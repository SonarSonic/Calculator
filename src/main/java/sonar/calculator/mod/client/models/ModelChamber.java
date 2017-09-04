package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelChamber extends ModelBase {
  //fields
    ModelRenderer Corner1;
    ModelRenderer Corner2;
    ModelRenderer Corner3;
    ModelRenderer Corner4;
    ModelRenderer Side1;
    ModelRenderer Side2;
    ModelRenderer Side3;
    ModelRenderer Side4;
    ModelRenderer Side5;
    ModelRenderer Side6;
    ModelRenderer Side7;
    ModelRenderer Side8;
    ModelRenderer Tray;
    ModelRenderer Screen;
    ModelRenderer Side9;
    ModelRenderer Side10;
    ModelRenderer Side11;
    ModelRenderer Side12;
  
    public ModelChamber() {
    textureWidth = 64;
    textureHeight = 32;
    
      Corner1 = new ModelRenderer(this, 0, 0);
      Corner1.addBox(0F, 0F, 0F, 2, 16, 2);
      Corner1.setRotationPoint(6F, 8F, -8F);
      Corner1.setTextureSize(64, 32);
      Corner1.mirror = true;
      setRotation(Corner1, 0F, 0F, 0F);
      Corner2 = new ModelRenderer(this, 0, 0);
      Corner2.addBox(0F, 0F, 0F, 2, 16, 2);
      Corner2.setRotationPoint(-8F, 8F, -8F);
      Corner2.setTextureSize(64, 32);
      Corner2.mirror = true;
      setRotation(Corner2, 0F, 0F, 0F);
      Corner3 = new ModelRenderer(this, 0, 0);
      Corner3.addBox(0F, 0F, 0F, 2, 16, 2);
      Corner3.setRotationPoint(6F, 8F, 6F);
      Corner3.setTextureSize(64, 32);
      Corner3.mirror = true;
      setRotation(Corner3, 0F, 0F, 0F);
      Corner4 = new ModelRenderer(this, 0, 0);
      Corner4.addBox(0F, 0F, 0F, 2, 16, 2);
      Corner4.setRotationPoint(-8F, 8F, 6F);
      Corner4.setTextureSize(64, 32);
      Corner4.mirror = true;
      setRotation(Corner4, 0F, 0F, 0F);
      Side1 = new ModelRenderer(this, 0, 0);
      Side1.addBox(0F, 0F, 0F, 2, 12, 2);
      Side1.setRotationPoint(6F, 24F, -6F);
      Side1.setTextureSize(64, 32);
      Side1.mirror = true;
      setRotation(Side1, 1.570796F, 0F, 0F);
      Side2 = new ModelRenderer(this, 0, 0);
      Side2.addBox(0F, 0F, 0F, 2, 12, 2);
      Side2.setRotationPoint(-8F, 24F, -6F);
      Side2.setTextureSize(64, 32);
      Side2.mirror = true;
      setRotation(Side2, 1.570796F, 0F, 0F);
      Side3 = new ModelRenderer(this, 0, 0);
      Side3.addBox(0F, 0F, 0F, 2, 12, 2);
      Side3.setRotationPoint(6F, 10F, -6F);
      Side3.setTextureSize(64, 32);
      Side3.mirror = true;
      setRotation(Side3, 1.570796F, 0F, 0F);
      Side4 = new ModelRenderer(this, 0, 0);
      Side4.addBox(0F, 0F, 0F, 2, 12, 2);
      Side4.setRotationPoint(-8F, 10F, -6F);
      Side4.setTextureSize(64, 32);
      Side4.mirror = true;
      setRotation(Side4, 1.570796F, 0F, 0F);
      Side5 = new ModelRenderer(this, 0, 0);
      Side5.addBox(0F, 0F, 0F, 2, 12, 2);
      Side5.setRotationPoint(6F, 8F, -8F);
      Side5.setTextureSize(64, 32);
      Side5.mirror = true;
      setRotation(Side5, 0F, 0F, 1.570796F);
      Side6 = new ModelRenderer(this, 0, 0);
      Side6.addBox(0F, 0F, 0F, 2, 12, 2);
      Side6.setRotationPoint(6F, 22F, -8F);
      Side6.setTextureSize(64, 32);
      Side6.mirror = true;
      setRotation(Side6, 0F, 0F, 1.570796F);
      Side7 = new ModelRenderer(this, 0, 0);
      Side7.addBox(0F, 0F, 0F, 2, 12, 2);
      Side7.setRotationPoint(6F, 22F, 6F);
      Side7.setTextureSize(64, 32);
      Side7.mirror = true;
      setRotation(Side7, 0F, 0F, 1.570796F);
      Side8 = new ModelRenderer(this, 0, 0);
      Side8.addBox(0F, 0F, 0F, 2, 12, 2);
      Side8.setRotationPoint(6F, 8F, 6F);
      Side8.setTextureSize(64, 32);
      Side8.mirror = true;
      setRotation(Side8, 0F, 0F, 1.570796F);
      Tray = new ModelRenderer(this, 0, 19);
      Tray.addBox(0F, 0F, 0F, 12, 1, 12);
      Tray.setRotationPoint(-6F, 19F, -6F);
      Tray.setTextureSize(64, 32);
      Tray.mirror = true;
      setRotation(Tray, 0F, 0F, 0F);
      Screen = new ModelRenderer(this, 42, 0);
      Screen.addBox(0F, 0F, 0F, 10, 4, 1);
      Screen.setRotationPoint(-5F, 17.5F, -6F);
      Screen.setTextureSize(64, 32);
      Screen.mirror = true;
      setRotation(Screen, -0.4363323F, 0F, 0F);
      Side9 = new ModelRenderer(this, 0, 0);
      Side9.addBox(0F, 0F, 0F, 2, 2, 12);
      Side9.setRotationPoint(5F, 20F, -6F);
      Side9.setTextureSize(64, 32);
      Side9.mirror = true;
      setRotation(Side9, 0F, 0F, 0F);
      Side10 = new ModelRenderer(this, 0, 0);
      Side10.addBox(0F, 0F, 0F, 2, 2, 12);
      Side10.setRotationPoint(-7F, 20F, -6F);
      Side10.setTextureSize(64, 32);
      Side10.mirror = true;
      setRotation(Side10, 0F, 0F, 0F);
      Side11 = new ModelRenderer(this, 0, 0);
      Side11.addBox(0F, 0F, 0F, 2, 2, 12);
      Side11.setRotationPoint(-6F, 20F, 7F);
      Side11.setTextureSize(64, 32);
      Side11.mirror = true;
      setRotation(Side11, 0F, 1.570796F, 0F);
      Side12 = new ModelRenderer(this, 0, 0);
      Side12.addBox(0F, 0F, 0F, 2, 2, 12);
      Side12.setRotationPoint(-6F, 20F, -5F);
      Side12.setTextureSize(64, 32);
      Side12.mirror = true;
      setRotation(Side12, 0F, 1.570796F, 0F);
  }
  
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Corner1.render(f5);
    Corner2.render(f5);
    Corner3.render(f5);
    Corner4.render(f5);
    Side1.render(f5);
    Side2.render(f5);
    Side3.render(f5);
    Side4.render(f5);
    Side5.render(f5);
    Side6.render(f5);
    Side7.render(f5);
    Side8.render(f5);
    Tray.render(f5);
    Screen.render(f5);
    Side9.render(f5);
    Side10.render(f5);
    Side11.render(f5);
    Side12.render(f5);
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
