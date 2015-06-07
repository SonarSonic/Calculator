package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;



public class ModelCalculatorLocator
  extends ModelBase
{
  ModelRenderer LLOne;
  ModelRenderer LLTwo;
  ModelRenderer LLThree;
  ModelRenderer LLFour;
  ModelRenderer LLLOne;
  ModelRenderer LLLTwo;
  ModelRenderer LLLThree;
  ModelRenderer LLLFour;
  ModelRenderer LLLLOne;
  ModelRenderer LLLLTwo;
  ModelRenderer LLLLThree;
  ModelRenderer LLLLFour;
  ModelRenderer Bottom;
  ModelRenderer Top;
  ModelRenderer TopTwo;
  ModelRenderer Middle;
  ModelRenderer Side1;
  ModelRenderer Side2;
  ModelRenderer Side3;
  ModelRenderer Side4;
  ModelRenderer Top1;
  ModelRenderer Top2;
  ModelRenderer Top3;
  ModelRenderer Part1;
  ModelRenderer Part2;
  ModelRenderer Part3;
  ModelRenderer Part4;
  
  public ModelCalculatorLocator()
  {
    this.textureWidth = 128;
    this.textureHeight = 64;
    
    this.LLOne = new ModelRenderer(this, 0, 0);
    this.LLOne.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.LLOne.setRotationPoint(-8.0F, 20.0F, 5.0F);
    this.LLOne.setTextureSize(128, 64);
    this.LLOne.mirror = true;
    setRotation(this.LLOne, 0.0F, 0.0F, 0.0F);
    this.LLTwo = new ModelRenderer(this, 0, 0);
    this.LLTwo.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.LLTwo.setRotationPoint(-8.0F, 20.0F, -6.0F);
    this.LLTwo.setTextureSize(128, 64);
    this.LLTwo.mirror = true;
    setRotation(this.LLTwo, 0.0F, 0.0F, 0.0F);
    this.LLThree = new ModelRenderer(this, 0, 0);
    this.LLThree.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.LLThree.setRotationPoint(5.0F, 20.0F, 8.0F);
    this.LLThree.setTextureSize(128, 64);
    this.LLThree.mirror = true;
    setRotation(this.LLThree, 0.0F, 1.570796F, 0.0F);
    this.LLFour = new ModelRenderer(this, 0, 0);
    this.LLFour.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.LLFour.setRotationPoint(-6.0F, 20.0F, 8.0F);
    this.LLFour.setTextureSize(128, 64);
    this.LLFour.mirror = true;
    setRotation(this.LLFour, 0.0F, 1.570796F, 0.0F);
    this.LLLOne = new ModelRenderer(this, 0, 0);
    this.LLLOne.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.LLLOne.setRotationPoint(-6.0F, 17.0F, 8.0F);
    this.LLLOne.setTextureSize(128, 64);
    this.LLLOne.mirror = true;
    setRotation(this.LLLOne, 0.0F, 1.570796F, 0.0F);
    this.LLLTwo = new ModelRenderer(this, 0, 0);
    this.LLLTwo.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.LLLTwo.setRotationPoint(5.0F, 17.0F, 8.0F);
    this.LLLTwo.setTextureSize(128, 64);
    this.LLLTwo.mirror = true;
    setRotation(this.LLLTwo, 0.0F, 1.570796F, 0.0F);
    this.LLLThree = new ModelRenderer(this, 0, 0);
    this.LLLThree.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.LLLThree.setRotationPoint(-8.0F, 17.0F, -6.0F);
    this.LLLThree.setTextureSize(128, 64);
    this.LLLThree.mirror = true;
    setRotation(this.LLLThree, 0.0F, 0.0F, 0.0F);
    this.LLLFour = new ModelRenderer(this, 0, 0);
    this.LLLFour.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.LLLFour.setRotationPoint(-8.0F, 17.0F, 5.0F);
    this.LLLFour.setTextureSize(128, 64);
    this.LLLFour.mirror = true;
    setRotation(this.LLLFour, 0.0F, 0.0F, 0.0F);
    this.LLLLOne = new ModelRenderer(this, 0, 0);
    this.LLLLOne.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.LLLLOne.setRotationPoint(-8.0F, 14.0F, -6.0F);
    this.LLLLOne.setTextureSize(128, 64);
    this.LLLLOne.mirror = true;
    setRotation(this.LLLLOne, 0.0F, 0.0F, 0.0F);
    this.LLLLTwo = new ModelRenderer(this, 0, 0);
    this.LLLLTwo.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.LLLLTwo.setRotationPoint(-8.0F, 14.0F, 5.0F);
    this.LLLLTwo.setTextureSize(128, 64);
    this.LLLLTwo.mirror = true;
    setRotation(this.LLLLTwo, 0.0F, 0.0F, 0.0F);
    this.LLLLThree = new ModelRenderer(this, 0, 0);
    this.LLLLThree.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.LLLLThree.setRotationPoint(5.0F, 14.0F, 8.0F);
    this.LLLLThree.setTextureSize(128, 64);
    this.LLLLThree.mirror = true;
    setRotation(this.LLLLThree, 0.0F, 1.570796F, 0.0F);
    this.LLLLFour = new ModelRenderer(this, 0, 0);
    this.LLLLFour.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
    this.LLLLFour.setRotationPoint(-6.0F, 14.0F, 8.0F);
    this.LLLLFour.setTextureSize(128, 64);
    this.LLLLFour.mirror = true;
    setRotation(this.LLLLFour, 0.0F, 1.570796F, 0.0F);
    this.Bottom = new ModelRenderer(this, 0, 20);
    this.Bottom.addBox(0.0F, 0.0F, 0.0F, 16, 2, 16);
    this.Bottom.setRotationPoint(-8.0F, 22.0F, -8.0F);
    this.Bottom.setTextureSize(128, 64);
    this.Bottom.mirror = true;
    setRotation(this.Bottom, 0.0F, 0.0F, 0.0F);
    this.Top = new ModelRenderer(this, 0, 11);
    this.Top.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1);
    this.Top.setRotationPoint(-4.0F, 12.0F, -4.0F);
    this.Top.setTextureSize(128, 64);
    this.Top.mirror = true;
    setRotation(this.Top, 0.0F, 0.0F, 0.0F);
    this.TopTwo = new ModelRenderer(this, 0, 13);
    this.TopTwo.addBox(0.0F, 0.0F, 0.0F, 6, 1, 6);
    this.TopTwo.setRotationPoint(-3.0F, 13.0F, -3.0F);
    this.TopTwo.setTextureSize(128, 64);
    this.TopTwo.mirror = true;
    setRotation(this.TopTwo, 0.0F, 0.0F, 0.0F);
    this.Middle = new ModelRenderer(this, 48, 0);
    this.Middle.addBox(0.0F, 0.0F, 0.0F, 4, 8, 4);
    this.Middle.setRotationPoint(-2.0F, 14.0F, -2.0F);
    this.Middle.setTextureSize(128, 64);
    this.Middle.mirror = true;
    setRotation(this.Middle, 0.0F, 0.0F, 0.0F);
    this.Side1 = new ModelRenderer(this, 42, 0);
    this.Side1.addBox(0.0F, 0.0F, 0.0F, 1, 3, 2);
    this.Side1.setRotationPoint(2.0F, 19.0F, -1.0F);
    this.Side1.setTextureSize(128, 64);
    this.Side1.mirror = true;
    setRotation(this.Side1, 0.0F, 0.0F, 0.0F);
    this.Side2 = new ModelRenderer(this, 42, 0);
    this.Side2.addBox(0.0F, 0.0F, 0.0F, 1, 3, 2);
    this.Side2.setRotationPoint(-3.0F, 19.0F, -1.0F);
    this.Side2.setTextureSize(128, 64);
    this.Side2.mirror = true;
    setRotation(this.Side2, 0.0F, 0.0F, 0.0F);
    this.Side3 = new ModelRenderer(this, 42, 0);
    this.Side3.addBox(0.0F, 0.0F, 0.0F, 1, 3, 2);
    this.Side3.setRotationPoint(-1.0F, 19.0F, 3.0F);
    this.Side3.setTextureSize(128, 64);
    this.Side3.mirror = true;
    setRotation(this.Side3, 0.0F, 1.570796F, 0.0F);
    this.Side4 = new ModelRenderer(this, 42, 0);
    this.Side4.addBox(0.0F, 0.0F, 0.0F, 1, 3, 2);
    this.Side4.setRotationPoint(-1.0F, 19.0F, -2.0F);
    this.Side4.setTextureSize(128, 64);
    this.Side4.mirror = true;
    setRotation(this.Side4, 0.0F, 1.570796F, 0.0F);
    this.Top1 = new ModelRenderer(this, 0, 11);
    this.Top1.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1);
    this.Top1.setRotationPoint(-4.0F, 12.0F, 3.0F);
    this.Top1.setTextureSize(128, 64);
    this.Top1.mirror = true;
    setRotation(this.Top1, 0.0F, 0.0F, 0.0F);
    this.Top2 = new ModelRenderer(this, 50, 12);
    this.Top2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6);
    this.Top2.setRotationPoint(-4.0F, 12.0F, -3.0F);
    this.Top2.setTextureSize(128, 64);
    this.Top2.mirror = true;
    setRotation(this.Top2, 0.0F, 0.0F, 0.0F);
    this.Top3 = new ModelRenderer(this, 50, 12);
    this.Top3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6);
    this.Top3.setRotationPoint(3.0F, 12.0F, -3.0F);
    this.Top3.setTextureSize(128, 64);
    this.Top3.mirror = true;
    setRotation(this.Top3, 0.0F, 0.0F, 0.0F);
    this.Part1 = new ModelRenderer(this, 0, 2);
    this.Part1.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
    this.Part1.setRotationPoint(5.0F, 15.0F, -6.0F);
    this.Part1.setTextureSize(128, 64);
    this.Part1.mirror = true;
    setRotation(this.Part1, 0.0F, 0.0F, 0.0F);
    this.Part2 = new ModelRenderer(this, 0, 2);
    this.Part2.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
    this.Part2.setRotationPoint(-6.0F, 15.0F, 5.0F);
    this.Part2.setTextureSize(128, 64);
    this.Part2.mirror = true;
    setRotation(this.Part2, 0.0F, 0.0F, 0.0F);
    this.Part3 = new ModelRenderer(this, 0, 2);
    this.Part3.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
    this.Part3.setRotationPoint(-6.0F, 15.0F, -6.0F);
    this.Part3.setTextureSize(128, 64);
    this.Part3.mirror = true;
    setRotation(this.Part3, 0.0F, 0.0F, 0.0F);
    this.Part4 = new ModelRenderer(this, 0, 2);
    this.Part4.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
    this.Part4.setRotationPoint(5.0F, 15.0F, 5.0F);
    this.Part4.setTextureSize(128, 64);
    this.Part4.mirror = true;
    setRotation(this.Part4, 0.0F, 0.0F, 0.0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.LLOne.render(f5);
    this.LLTwo.render(f5);
    this.LLThree.render(f5);
    this.LLFour.render(f5);
    this.LLLOne.render(f5);
    this.LLLTwo.render(f5);
    this.LLLThree.render(f5);
    this.LLLFour.render(f5);
    this.LLLLOne.render(f5);
    this.LLLLTwo.render(f5);
    this.LLLLThree.render(f5);
    this.LLLLFour.render(f5);
    this.Bottom.render(f5);
    this.Top.render(f5);
    this.TopTwo.render(f5);
    this.Middle.render(f5);
    this.Side1.render(f5);
    this.Side2.render(f5);
    this.Side3.render(f5);
    this.Side4.render(f5);
    this.Top1.render(f5);
    this.Top2.render(f5);
    this.Top3.render(f5);
    this.Part1.render(f5);
    this.Part2.render(f5);
    this.Part3.render(f5);
    this.Part4.render(f5);
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
    this.LLOne.render(f5);
    this.LLTwo.render(f5);
    this.LLThree.render(f5);
    this.LLFour.render(f5);
    this.LLLOne.render(f5);
    this.LLLTwo.render(f5);
    this.LLLThree.render(f5);
    this.LLLFour.render(f5);
    this.LLLLOne.render(f5);
    this.LLLLTwo.render(f5);
    this.LLLLThree.render(f5);
    this.LLLLFour.render(f5);
    this.Bottom.render(f5);
    this.Top.render(f5);
    this.TopTwo.render(f5);
    this.Middle.render(f5);
    this.Side1.render(f5);
    this.Side2.render(f5);
    this.Side3.render(f5);
    this.Side4.render(f5);
    this.Top1.render(f5);
    this.Top2.render(f5);
    this.Top3.render(f5);
    this.Part1.render(f5);
    this.Part2.render(f5);
    this.Part3.render(f5);
    this.Part4.render(f5);
  }
}
