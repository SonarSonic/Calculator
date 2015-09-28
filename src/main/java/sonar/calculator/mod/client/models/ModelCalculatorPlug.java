package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;



public class ModelCalculatorPlug
  extends ModelBase
{
  ModelRenderer First;
  ModelRenderer Second;
  ModelRenderer Third;
  ModelRenderer Fourth;
  ModelRenderer Fifth;
  ModelRenderer CubeOne;
  ModelRenderer CubeTwo;
  ModelRenderer CubeThree;
  ModelRenderer CubeFour;
  ModelRenderer BarOne;
  ModelRenderer BarTwo;
  ModelRenderer BarThree;
  ModelRenderer BarFour;
  ModelRenderer StraightOne;
  ModelRenderer StraightTwo;
  ModelRenderer StraightThree;
  ModelRenderer StraightFour;
  ModelRenderer Connection;
  
  public ModelCalculatorPlug()
  {
    this.textureWidth = 128;
    this.textureHeight = 64;
    
    this.First = new ModelRenderer(this, 64, 47);
    this.First.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16);
    this.First.setRotationPoint(-8.0F, 23.0F, -8.0F);
    this.First.setTextureSize(128, 64);
    this.First.mirror = true;
    setRotation(this.First, 0.0F, 0.0F, 0.0F);
    this.Second = new ModelRenderer(this, 0, 49);
    this.Second.addBox(0.0F, 0.0F, 0.0F, 14, 1, 14);
    this.Second.setRotationPoint(-7.0F, 22.0F, -7.0F);
    this.Second.setTextureSize(128, 64);
    this.Second.mirror = true;
    setRotation(this.Second, 0.0F, 0.0F, 0.0F);
    this.Third = new ModelRenderer(this, 0, 36);
    this.Third.addBox(0.0F, 0.0F, 0.0F, 12, 1, 12);
    this.Third.setRotationPoint(-6.0F, 21.0F, -6.0F);
    this.Third.setTextureSize(128, 64);
    this.Third.mirror = true;
    setRotation(this.Third, 0.0F, 0.0F, 0.0F);
    this.Fourth = new ModelRenderer(this, 0, 25);
    this.Fourth.addBox(0.0F, -1.0F, 0.0F, 10, 1, 10);
    this.Fourth.setRotationPoint(-5.0F, 21.0F, -5.0F);
    this.Fourth.setTextureSize(128, 64);
    this.Fourth.mirror = true;
    setRotation(this.Fourth, 0.0F, 0.0F, 0.0F);
    this.Fifth = new ModelRenderer(this, 96, 37);
    this.Fifth.addBox(0.0F, 0.0F, 0.0F, 8, 2, 8);
    this.Fifth.setRotationPoint(-4.0F, 18.0F, -4.0F);
    this.Fifth.setTextureSize(128, 64);
    this.Fifth.mirror = true;
    setRotation(this.Fifth, 0.0F, 0.0F, 0.0F);
    this.CubeOne = new ModelRenderer(this, 0, 21);
    this.CubeOne.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
    this.CubeOne.setRotationPoint(-4.0F, 17.0F, -4.0F);
    this.CubeOne.setTextureSize(128, 64);
    this.CubeOne.mirror = true;
    setRotation(this.CubeOne, 0.0F, 0.0F, 0.0F);
    this.CubeTwo = new ModelRenderer(this, 8, 21);
    this.CubeTwo.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
    this.CubeTwo.setRotationPoint(2.0F, 17.0F, -4.0F);
    this.CubeTwo.setTextureSize(128, 64);
    this.CubeTwo.mirror = true;
    setRotation(this.CubeTwo, 0.0F, 0.0F, 0.0F);
    this.CubeThree = new ModelRenderer(this, 16, 21);
    this.CubeThree.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
    this.CubeThree.setRotationPoint(2.0F, 17.0F, 2.0F);
    this.CubeThree.setTextureSize(128, 64);
    this.CubeThree.mirror = true;
    setRotation(this.CubeThree, 0.0F, 0.0F, 0.0F);
    this.CubeFour = new ModelRenderer(this, 24, 21);
    this.CubeFour.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
    this.CubeFour.setRotationPoint(-4.0F, 17.0F, 2.0F);
    this.CubeFour.setTextureSize(128, 64);
    this.CubeFour.mirror = true;
    setRotation(this.CubeFour, 0.0F, 0.0F, 0.0F);
    this.BarOne = new ModelRenderer(this, 94, 0);
    this.BarOne.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.BarOne.setRotationPoint(-8.0F, 17.0F, 5.0F);
    this.BarOne.setTextureSize(128, 64);
    this.BarOne.mirror = true;
    setRotation(this.BarOne, 0.0F, 0.0F, 0.0F);
    this.BarTwo = new ModelRenderer(this, 94, 2);
    this.BarTwo.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.BarTwo.setRotationPoint(-8.0F, 17.0F, -6.0F);
    this.BarTwo.setTextureSize(128, 64);
    this.BarTwo.mirror = true;
    setRotation(this.BarTwo, 0.0F, 0.0F, 0.0F);
    this.BarThree = new ModelRenderer(this, 94, 4);
    this.BarThree.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.BarThree.setRotationPoint(5.0F, 17.0F, 8.0F);
    this.BarThree.setTextureSize(128, 64);
    this.BarThree.mirror = true;
    setRotation(this.BarThree, 0.0F, 1.570796F, 0.0F);
    this.BarFour = new ModelRenderer(this, 94, 6);
    this.BarFour.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.BarFour.setRotationPoint(-6.0F, 17.0F, 8.0F);
    this.BarFour.setTextureSize(128, 64);
    this.BarFour.mirror = true;
    setRotation(this.BarFour, 0.0F, 1.570796F, 0.0F);
    this.StraightOne = new ModelRenderer(this, 0, 17);
    this.StraightOne.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.StraightOne.setRotationPoint(5.0F, 18.0F, -6.0F);
    this.StraightOne.setTextureSize(128, 64);
    this.StraightOne.mirror = true;
    setRotation(this.StraightOne, 0.0F, 0.0F, 0.0F);
    this.StraightTwo = new ModelRenderer(this, 4, 17);
    this.StraightTwo.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.StraightTwo.setRotationPoint(-6.0F, 18.0F, 5.0F);
    this.StraightTwo.setTextureSize(128, 64);
    this.StraightTwo.mirror = true;
    setRotation(this.StraightTwo, 0.0F, 0.0F, 0.0F);
    this.StraightThree = new ModelRenderer(this, 8, 17);
    this.StraightThree.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.StraightThree.setRotationPoint(5.0F, 18.0F, 5.0F);
    this.StraightThree.setTextureSize(128, 64);
    this.StraightThree.mirror = true;
    setRotation(this.StraightThree, 0.0F, 0.0F, 0.0F);
    this.StraightFour = new ModelRenderer(this, 12, 17);
    this.StraightFour.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.StraightFour.setRotationPoint(-6.0F, 18.0F, -6.0F);
    this.StraightFour.setTextureSize(128, 64);
    this.StraightFour.mirror = true;
    setRotation(this.StraightFour, 0.0F, 0.0F, 0.0F);
    this.Connection = new ModelRenderer(this, 0, 0);
    this.Connection.addBox(0.0F, 0.0F, 0.0F, 4, 2, 4);
    this.Connection.setRotationPoint(-2.0F, 16.0F, -2.0F);
    this.Connection.setTextureSize(128, 64);
    this.Connection.mirror = true;
    setRotation(this.Connection, 0.0F, 0.0F, 0.0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.First.render(f5);
    this.Second.render(f5);
    this.Third.render(f5);
    this.Fourth.render(f5);
    this.Fifth.render(f5);
    this.CubeOne.render(f5);
    this.CubeTwo.render(f5);
    this.CubeThree.render(f5);
    this.CubeFour.render(f5);
    this.BarOne.render(f5);
    this.BarTwo.render(f5);
    this.BarThree.render(f5);
    this.BarFour.render(f5);
    this.StraightOne.render(f5);
    this.StraightTwo.render(f5);
    this.StraightThree.render(f5);
    this.StraightFour.render(f5);
    this.Connection.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  


  @Override
public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) { super.setRotationAngles(f, f1, f2, f3, f4, f5, entity); }
  
  public void renderModel(float f) {
    this.First.render(f);
    this.Second.render(f);
    this.Third.render(f);
    this.Fourth.render(f);
    this.Fifth.render(f);
    this.CubeOne.render(f);
    this.CubeTwo.render(f);
    this.CubeThree.render(f);
    this.CubeFour.render(f);
    this.BarOne.render(f);
    this.BarTwo.render(f);
    this.BarThree.render(f);
    this.BarFour.render(f);
    this.StraightOne.render(f);
    this.StraightTwo.render(f);
    this.StraightThree.render(f);
    this.StraightFour.render(f);
    this.Connection.render(f);
  }
}
