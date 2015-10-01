
package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCalculator extends ModelBase
{
  //fields
    ModelRenderer Calc;
  
  public ModelCalculator()
  {
    textureWidth = 128;
    textureHeight = 64;    
      
    Calc = new ModelRenderer(this, 118, 0);
    Calc.addBox(0F, 0F, 0F, 4, 7, 1);
    Calc.setRotationPoint(-2F, 7.9F, 2F);
    Calc.setTextureSize(128, 64);
    Calc.mirror = true;
    setRotation(Calc, -0.7853982F, 0F, 0F);
     
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
   
    Calc.render(f5);
   
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
