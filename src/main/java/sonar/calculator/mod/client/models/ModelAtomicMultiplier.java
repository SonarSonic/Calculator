

package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAtomicMultiplier extends ModelBase
{
  //fields
    ModelRenderer Top1;
    ModelRenderer Rod2;
    ModelRenderer Rod3;
    ModelRenderer Rod4;
    ModelRenderer Bottom1;
    ModelRenderer Glass1;
    ModelRenderer Glass2;
    ModelRenderer Glass3Cover;
    ModelRenderer Glass4;
    ModelRenderer Centre2;
    ModelRenderer Centre1;
    ModelRenderer PowerPlate;
    ModelRenderer PowerPlate2;
    ModelRenderer Rod1;
    ModelRenderer Shape1;
  
  public ModelAtomicMultiplier()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      Top1 = new ModelRenderer(this, 30, 30);
      Top1.addBox(0F, 0F, 0F, 16, 1, 16);
      Top1.setRotationPoint(-8F, 11F, -8F);
      Top1.setTextureSize(128, 64);
      Top1.mirror = true;
      setRotation(Top1, 0F, 0F, 0F);
      Rod2 = new ModelRenderer(this, 0, 30);
      Rod2.addBox(5F, 0F, 0F, 1, 15, 1);
      Rod2.setRotationPoint(0F, 8F, 5F);
      Rod2.setTextureSize(128, 64);
      Rod2.mirror = true;
      setRotation(Rod2, 0F, 0F, 0F);
      Rod3 = new ModelRenderer(this, 0, 30);
      Rod3.addBox(0F, 0F, 0F, 1, 15, 1);
      Rod3.setRotationPoint(5F, 8F, -6F);
      Rod3.setTextureSize(128, 64);
      Rod3.mirror = true;
      setRotation(Rod3, 0F, 0F, 0F);
      Rod4 = new ModelRenderer(this, 0, 30);
      Rod4.addBox(0F, 0F, 0F, 1, 15, 1);
      Rod4.setRotationPoint(-6F, 8F, -6F);
      Rod4.setTextureSize(128, 64);
      Rod4.mirror = true;
      setRotation(Rod4, 0F, 0F, 0F);
      Bottom1 = new ModelRenderer(this, 30, 30);
      Bottom1.addBox(0F, 0F, 0F, 16, 1, 16);
      Bottom1.setRotationPoint(-8F, 23F, -8F);
      Bottom1.setTextureSize(128, 64);
      Bottom1.mirror = true;
      setRotation(Bottom1, 0F, 0F, 0F);
      Glass1 = new ModelRenderer(this, 50, 0);
      Glass1.addBox(0F, 0F, 0F, 1, 7, 4);
      Glass1.setRotationPoint(2F, 16F, -2F);
      Glass1.setTextureSize(128, 64);
      Glass1.mirror = true;
      setRotation(Glass1, 0F, 0F, 0F);
      Glass2 = new ModelRenderer(this, 50, 0);
      Glass2.addBox(0F, 0F, 0F, 1, 7, 4);
      Glass2.setRotationPoint(-3F, 16F, -2F);
      Glass2.setTextureSize(128, 64);
      Glass2.mirror = true;
      setRotation(Glass2, 0F, 0F, 0F);
      Glass3Cover = new ModelRenderer(this, 5, 0);
      Glass3Cover.addBox(0F, 0F, 0F, 4, 7, 1);
      Glass3Cover.setRotationPoint(-2F, 16F, -3F);
      Glass3Cover.setTextureSize(128, 64);
      Glass3Cover.mirror = true;
      setRotation(Glass3Cover, 0F, 0F, 0F);
      Glass4 = new ModelRenderer(this, 60, 0);
      Glass4.addBox(0F, 0F, 0F, 4, 7, 1);
      Glass4.setRotationPoint(-2F, 16F, 2F);
      Glass4.setTextureSize(128, 64);
      Glass4.mirror = true;
      setRotation(Glass4, 0F, 0F, 0F);
      Centre2 = new ModelRenderer(this, 0, 0);
      Centre2.addBox(0F, 0F, 0F, 6, 1, 6);
      Centre2.setRotationPoint(-3F, 15F, -3F);
      Centre2.setTextureSize(128, 64);
      Centre2.mirror = true;
      setRotation(Centre2, 0F, 0F, 0F);
      Centre1 = new ModelRenderer(this, 0, 0);
      Centre1.addBox(0F, 0F, 0F, 8, 3, 8);
      Centre1.setRotationPoint(-4F, 12F, -4F);
      Centre1.setTextureSize(128, 64);
      Centre1.mirror = true;
      setRotation(Centre1, 0F, 0F, 0F);
      PowerPlate = new ModelRenderer(this, 0, 55);
      PowerPlate.addBox(0F, 0F, 0F, 8, 8, 1);
      PowerPlate.setRotationPoint(-4F, 15F, -8F);
      PowerPlate.setTextureSize(128, 64);
      PowerPlate.mirror = true;
      setRotation(PowerPlate, 0F, 0F, 0F);
      PowerPlate2 = new ModelRenderer(this, 0, 0);
      PowerPlate2.addBox(0F, 0F, 0F, 6, 7, 4);
      PowerPlate2.setRotationPoint(-3F, 16F, -7F);
      PowerPlate2.setTextureSize(128, 64);
      PowerPlate2.mirror = true;
      setRotation(PowerPlate2, 0F, 0F, 0F);
      Rod1 = new ModelRenderer(this, 0, 30);
      Rod1.addBox(0F, 0F, 0F, 1, 15, 1);
      Rod1.setRotationPoint(-6F, 8F, 5F);
      Rod1.setTextureSize(128, 64);
      Rod1.mirror = true;
      setRotation(Rod1, 0F, 0F, 0F);
      Shape1 = new ModelRenderer(this, 0, 11);
      Shape1.addBox(0F, 0F, 0F, 8, 1, 8);
      Shape1.setRotationPoint(-4F, 10F, -4F);
      Shape1.setTextureSize(128, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Top1.render(f5);
    Rod2.render(f5);
    Rod3.render(f5);
    Rod4.render(f5);
    Bottom1.render(f5);
    Glass1.render(f5);
    Glass2.render(f5);
    Glass3Cover.render(f5);
    Glass4.render(f5);
    Centre2.render(f5);
    Centre1.render(f5);
    PowerPlate.render(f5);
    PowerPlate2.render(f5);
    Rod1.render(f5);
    Shape1.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  


  @Override
public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) { 
	  super.setRotationAngles(f, f1, f2, f3, f4, f5, entity); }
  
  public void renderModel(float f5) {
	  	Top1.render(f5);
	    Rod2.render(f5);
	    Rod3.render(f5);
	    Rod4.render(f5);
	    Bottom1.render(f5);
	    Glass1.render(f5);
	    Glass2.render(f5);
	    Glass3Cover.render(f5);
	    Glass4.render(f5);
	    Centre2.render(f5);
	    Centre1.render(f5);
	    PowerPlate.render(f5);
	    PowerPlate2.render(f5);
	    Rod1.render(f5);
	    Shape1.render(f5);
  }

}
