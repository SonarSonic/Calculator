package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;



public class ModelGenerator
  extends ModelBase
{
  ModelRenderer Support1;
  ModelRenderer Support2;
  ModelRenderer Support3;
  ModelRenderer Support4;
  ModelRenderer Support5;
  ModelRenderer Support6;
  ModelRenderer Support7;
  ModelRenderer Support8;
  ModelRenderer Support9;
  ModelRenderer Support10;
  ModelRenderer Support11;
  ModelRenderer Middle;
  
  public ModelGenerator()
  {
    this.textureWidth = 128;
    this.textureHeight = 64;
    
    this.Support1 = new ModelRenderer(this, 0, 0);
    this.Support1.addBox(0.0F, 0.0F, 0.0F, 1, 16, 1);
    this.Support1.setRotationPoint(6.0F, 8.0F, 6.0F);
    this.Support1.setTextureSize(128, 64);
    this.Support1.mirror = true;
    setRotation(this.Support1, 0.0F, 0.0F, 0.0F);
    this.Support2 = new ModelRenderer(this, 0, 23);
    this.Support2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 16);
    this.Support2.setRotationPoint(6.0F, 9.0F, -8.0F);
    this.Support2.setTextureSize(128, 64);
    this.Support2.mirror = true;
    setRotation(this.Support2, 0.0F, 0.0F, 0.0F);
    this.Support3 = new ModelRenderer(this, 0, 23);
    this.Support3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 16);
    this.Support3.setRotationPoint(-7.0F, 9.0F, -8.0F);
    this.Support3.setTextureSize(128, 64);
    this.Support3.mirror = true;
    setRotation(this.Support3, 0.0F, 0.0F, 0.0F);
    this.Support4 = new ModelRenderer(this, 0, 0);
    this.Support4.addBox(0.0F, 0.0F, 0.0F, 1, 16, 1);
    this.Support4.setRotationPoint(-7.0F, 8.0F, 6.0F);
    this.Support4.setTextureSize(128, 64);
    this.Support4.mirror = true;
    setRotation(this.Support4, 0.0F, 0.0F, 0.0F);
    this.Support5 = new ModelRenderer(this, 0, 0);
    this.Support5.addBox(0.0F, 0.0F, 0.0F, 1, 16, 1);
    this.Support5.setRotationPoint(-7.0F, 8.0F, -7.0F);
    this.Support5.setTextureSize(128, 64);
    this.Support5.mirror = true;
    setRotation(this.Support5, 0.0F, 0.0F, 0.0F);
    this.Support6 = new ModelRenderer(this, 0, 0);
    this.Support6.addBox(0.0F, 0.0F, 0.0F, 1, 16, 1);
    this.Support6.setRotationPoint(6.0F, 8.0F, -7.0F);
    this.Support6.setTextureSize(128, 64);
    this.Support6.mirror = true;
    setRotation(this.Support6, 0.0F, 0.0F, 0.0F);
    this.Support7 = new ModelRenderer(this, 4, 0);
    this.Support7.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.Support7.setRotationPoint(-8.0F, 9.0F, 6.0F);
    this.Support7.setTextureSize(128, 64);
    this.Support7.mirror = true;
    setRotation(this.Support7, 0.0F, 0.0F, 0.0F);
    this.Support8 = new ModelRenderer(this, 4, 0);
    this.Support8.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.Support8.setRotationPoint(-8.0F, 21.0F, 6.0F);
    this.Support8.setTextureSize(128, 64);
    this.Support8.mirror = true;
    setRotation(this.Support8, 0.0F, 0.0F, 0.0F);
    this.Support9 = new ModelRenderer(this, 4, 0);
    this.Support9.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.Support9.setRotationPoint(-8.0F, 9.0F, -7.0F);
    this.Support9.setTextureSize(128, 64);
    this.Support9.mirror = true;
    setRotation(this.Support9, 0.0F, 0.0F, 0.0F);
    this.Support10 = new ModelRenderer(this, 4, 0);
    this.Support10.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.Support10.setRotationPoint(-8.0F, 21.0F, -7.0F);
    this.Support10.setTextureSize(128, 64);
    this.Support10.mirror = true;
    setRotation(this.Support10, 0.0F, 0.0F, 0.0F);
    this.Support11 = new ModelRenderer(this, 0, 23);
    this.Support11.addBox(0.0F, 0.0F, 0.0F, 1, 1, 16);
    this.Support11.setRotationPoint(6.0F, 21.0F, -8.0F);
    this.Support11.setTextureSize(128, 64);
    this.Support11.mirror = true;
    setRotation(this.Support11, 0.0F, 0.0F, 0.0F);
    this.Middle = new ModelRenderer(this, 0, 40);
    this.Middle.addBox(0.0F, 0.0F, 0.0F, 12, 12, 12);
    this.Middle.setRotationPoint(-6.0F, 10.0F, -6.0F);
    this.Middle.setTextureSize(128, 64);
    this.Middle.mirror = true;
    setRotation(this.Middle, 0.0F, 0.0F, 0.0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Support1.render(f5);
    this.Support2.render(f5);
    this.Support3.render(f5);
    this.Support4.render(f5);
    this.Support5.render(f5);
    this.Support6.render(f5);
    this.Support7.render(f5);
    this.Support8.render(f5);
    this.Support9.render(f5);
    this.Support10.render(f5);
    this.Support11.render(f5);
    this.Middle.render(f5);
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
    this.Support1.render(f5);
    this.Support2.render(f5);
    this.Support3.render(f5);
    this.Support4.render(f5);
    this.Support5.render(f5);
    this.Support6.render(f5);
    this.Support7.render(f5);
    this.Support8.render(f5);
    this.Support9.render(f5);
    this.Support10.render(f5);
    this.Support11.render(f5);
    this.Middle.render(f5);
  }
}
