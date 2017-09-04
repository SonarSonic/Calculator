package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelWeatherStationBase extends ModelBase {
  //fields
    ModelRenderer Base;
    ModelRenderer Stand;
    ModelRenderer Stand2;
    ModelRenderer Stand3;
    ModelRenderer Stand4;
  
    public ModelWeatherStationBase() {
    textureWidth = 128;
    textureHeight = 64;
    
      Base = new ModelRenderer(this, 0, 47);
      Base.addBox(0F, 0F, 0F, 16, 1, 16);
      Base.setRotationPoint(-8F, 23F, -8F);
      Base.setTextureSize(128, 64);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Stand = new ModelRenderer(this, 0, 23);
      Stand.addBox(0F, 0F, 0F, 2, 20, 4);
      Stand.setRotationPoint(1F, 3F, -2F);
      Stand.setTextureSize(128, 64);
      Stand.mirror = true;
      setRotation(Stand, 0F, 0F, 0F);
      Stand2 = new ModelRenderer(this, 0, 16);
      Stand2.addBox(0F, 0F, 0F, 1, 4, 1);
      Stand2.setRotationPoint(1F, 18F, 2F);
      Stand2.setTextureSize(128, 64);
      Stand2.mirror = true;
      setRotation(Stand2, 0F, 0F, 0F);
      Stand3 = new ModelRenderer(this, 0, 16);
      Stand3.addBox(0F, 0F, 0F, 1, 4, 1);
      Stand3.setRotationPoint(1F, 18F, -3F);
      Stand3.setTextureSize(128, 64);
      Stand3.mirror = true;
      setRotation(Stand3, 0F, 0F, 0F);
      Stand4 = new ModelRenderer(this, 0, 0);
      Stand4.addBox(0F, 0F, 0F, 1, 6, 6);
      Stand4.setRotationPoint(0F, 17F, -3F);
      Stand4.setTextureSize(128, 64);
      Stand4.mirror = true;
      setRotation(Stand4, 0F, 0F, 0F);
  }
  
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

    GL11.glRotatef(-90, 0.0F, 1.0F, 0.0F);	 
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Stand.render(f5);
    Stand2.render(f5);
    Stand3.render(f5);
    Stand4.render(f5);
    GL11.glRotatef(+90, 0.0F, 1.0F, 0.0F);	
  }

    public void renderBase(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

    GL11.glRotatef(-90, 0.0F, 1.0F, 0.0F);	 
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Base.render(f5);
    GL11.glRotatef(+90, 0.0F, 1.0F, 0.0F);	
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
