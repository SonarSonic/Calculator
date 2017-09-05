package sonar.calculator.mod.client.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelFlawlessCapacitor extends ModelBase {
  //fields
    ModelRenderer Bottom;
    ModelRenderer Front;
    ModelRenderer Back;
    ModelRenderer Right;
    ModelRenderer Left;
    ModelRenderer Top;
    ModelRenderer Power_Source;
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
    ModelRenderer Support12;
    ModelRenderer Side1;
    ModelRenderer Side2;
    ModelRenderer Side3;
    ModelRenderer Side4;
    ModelRenderer Side5;
    ModelRenderer Side6;
    ModelRenderer Side7;
    ModelRenderer Side8;
    ModelRenderer Side9;
    ModelRenderer Side10;
    ModelRenderer Side11;
    ModelRenderer Side12;
    ModelRenderer Support13;
    ModelRenderer Support14;
    ModelRenderer Support15;
    ModelRenderer Support16;
  
    public ModelFlawlessCapacitor() {
    textureWidth = 128;
    textureHeight = 64;
    
      Bottom = new ModelRenderer(this, 0, 0);
      Bottom.addBox(0F, 0F, 0F, 12, 1, 12);
      Bottom.setRotationPoint(-6F, 23F, -6F);
      Bottom.setTextureSize(128, 64);
      Bottom.mirror = true;
      setRotation(Bottom, 0F, 0F, 0F);
      Front = new ModelRenderer(this, 0, 0);
      Front.addBox(0F, 0F, 0F, 12, 1, 12);
      Front.setRotationPoint(-6F, 22F, -8F);
      Front.setTextureSize(128, 64);
      Front.mirror = true;
      setRotation(Front, 1.570796F, 0F, 0F);
      Back = new ModelRenderer(this, 0, 0);
      Back.addBox(0F, 0F, 0F, 12, 1, 12);
      Back.setRotationPoint(-6F, 22F, 7F);
      Back.setTextureSize(128, 64);
      Back.mirror = true;
      setRotation(Back, 1.570796F, 0F, 0F);
      Right = new ModelRenderer(this, 0, 0);
      Right.addBox(0F, 0F, 0F, 12, 1, 12);
      Right.setRotationPoint(7F, 22F, 6F);
      Right.setTextureSize(128, 64);
      Right.mirror = true;
      setRotation(Right, 1.570796F, 1.570796F, 0F);
      Left = new ModelRenderer(this, 0, 0);
      Left.addBox(0F, 0F, 0F, 12, 1, 12);
      Left.setRotationPoint(-8F, 22F, 6F);
      Left.setTextureSize(128, 64);
      Left.mirror = true;
      setRotation(Left, 1.570796F, 1.570796F, 0F);
      Top = new ModelRenderer(this, 0, 0);
      Top.addBox(0F, 0F, 0F, 12, 1, 12);
      Top.setRotationPoint(-6F, 8F, -6F);
      Top.setTextureSize(128, 64);
      Top.mirror = true;
      setRotation(Top, 0F, 0F, 0F);
      Power_Source = new ModelRenderer(this, 34, 48);
      Power_Source.addBox(0F, 0F, 0F, 8, 8, 8);
      Power_Source.setRotationPoint(-4F, 12F, -4F);
      Power_Source.setTextureSize(128, 64);
      Power_Source.mirror = true;
      setRotation(Power_Source, 0F, 0F, 0F);
      Support1 = new ModelRenderer(this, 0, 47);
      Support1.addBox(0F, 0F, 0F, 1, 1, 16);
      Support1.setRotationPoint(1F, 9F, -8F);
      Support1.setTextureSize(128, 64);
      Support1.mirror = true;
      setRotation(Support1, 0F, 0F, 0F);
      Support2 = new ModelRenderer(this, 0, 47);
      Support2.addBox(0F, 0F, 0F, 1, 1, 16);
      Support2.setRotationPoint(-2F, 9F, -8F);
      Support2.setTextureSize(128, 64);
      Support2.mirror = true;
      setRotation(Support2, 0F, 0F, 0F);
      Support3 = new ModelRenderer(this, 0, 47);
      Support3.addBox(0F, 0F, 0F, 1, 1, 16);
      Support3.setRotationPoint(-8F, 9F, -1F);
      Support3.setTextureSize(128, 64);
      Support3.mirror = true;
      setRotation(Support3, 0F, 1.570796F, 0F);
      Support4 = new ModelRenderer(this, 0, 47);
      Support4.addBox(0F, 0F, 0F, 1, 1, 16);
      Support4.setRotationPoint(-8F, 9F, 2F);
      Support4.setTextureSize(128, 64);
      Support4.mirror = true;
      setRotation(Support4, 0F, 1.570796F, 0F);
      Support5 = new ModelRenderer(this, 0, 47);
      Support5.addBox(0F, 0F, 0F, 1, 1, 16);
      Support5.setRotationPoint(-8F, 22F, -1F);
      Support5.setTextureSize(128, 64);
      Support5.mirror = true;
      setRotation(Support5, 0F, 1.570796F, 0F);
      Support6 = new ModelRenderer(this, 0, 47);
      Support6.addBox(0F, 0F, 0F, 1, 1, 16);
      Support6.setRotationPoint(-8F, 22F, 2F);
      Support6.setTextureSize(128, 64);
      Support6.mirror = true;
      setRotation(Support6, 0F, 1.570796F, 0F);
      Support7 = new ModelRenderer(this, 0, 47);
      Support7.addBox(0F, 0F, 0F, 1, 1, 16);
      Support7.setRotationPoint(1F, 22F, -8F);
      Support7.setTextureSize(128, 64);
      Support7.mirror = true;
      setRotation(Support7, 0F, 0F, 0F);
      Support8 = new ModelRenderer(this, 0, 47);
      Support8.addBox(0F, 0F, 0F, 1, 1, 16);
      Support8.setRotationPoint(-1F, 22F, -8F);
      Support8.setTextureSize(128, 64);
      Support8.mirror = true;
      setRotation(Support8, 0F, 0F, 0F);
      Support9 = new ModelRenderer(this, 0, 47);
      Support9.addBox(0F, 0F, 0F, 1, 1, 16);
      Support9.setRotationPoint(-8F, 14F, 7F);
      Support9.setTextureSize(128, 64);
      Support9.mirror = true;
      setRotation(Support9, 0F, 1.570796F, 0F);
      Support10 = new ModelRenderer(this, 0, 47);
      Support10.addBox(0F, 0F, 0F, 1, 1, 16);
      Support10.setRotationPoint(-8F, 17F, 7F);
      Support10.setTextureSize(128, 64);
      Support10.mirror = true;
      setRotation(Support10, 0F, 1.570796F, 0F);
      Support11 = new ModelRenderer(this, 0, 47);
      Support11.addBox(0F, 0F, 0F, 1, 1, 16);
      Support11.setRotationPoint(-8F, 14F, -6F);
      Support11.setTextureSize(128, 64);
      Support11.mirror = true;
      setRotation(Support11, 0F, 1.570796F, 0F);
      Support12 = new ModelRenderer(this, 0, 47);
      Support12.addBox(0F, 0F, 0F, 1, 1, 16);
      Support12.setRotationPoint(-8F, 17F, -6F);
      Support12.setTextureSize(128, 64);
      Support12.mirror = true;
      setRotation(Support12, 0F, 1.570796F, 0F);
      Side1 = new ModelRenderer(this, 0, 45);
      Side1.addBox(0F, 0F, 0F, 16, 1, 1);
      Side1.setRotationPoint(-8F, 8F, -8F);
      Side1.setTextureSize(128, 64);
      Side1.mirror = true;
      setRotation(Side1, 0F, 0F, 0F);
      Side2 = new ModelRenderer(this, 0, 45);
      Side2.addBox(0F, 0F, 0F, 16, 1, 1);
      Side2.setRotationPoint(-8F, 8F, 7F);
      Side2.setTextureSize(128, 64);
      Side2.mirror = true;
      setRotation(Side2, 0F, 0F, 0F);
      Side3 = new ModelRenderer(this, 0, 45);
      Side3.addBox(0F, 0F, 0F, 16, 1, 1);
      Side3.setRotationPoint(-8F, 8F, 8F);
      Side3.setTextureSize(128, 64);
      Side3.mirror = true;
      setRotation(Side3, 0F, 1.570796F, 0F);
      Side4 = new ModelRenderer(this, 0, 45);
      Side4.addBox(0F, 0F, 0F, 16, 1, 1);
      Side4.setRotationPoint(7F, 8F, 8F);
      Side4.setTextureSize(128, 64);
      Side4.mirror = true;
      setRotation(Side4, 0F, 1.570796F, 0F);
      Side5 = new ModelRenderer(this, 0, 45);
      Side5.addBox(0F, 0F, 0F, 16, 1, 1);
      Side5.setRotationPoint(-8F, 23F, -8F);
      Side5.setTextureSize(128, 64);
      Side5.mirror = true;
      setRotation(Side5, 0F, 0F, 0F);
      Side6 = new ModelRenderer(this, 0, 45);
      Side6.addBox(0F, 0F, 0F, 16, 1, 1);
      Side6.setRotationPoint(-8F, 23F, 7F);
      Side6.setTextureSize(128, 64);
      Side6.mirror = true;
      setRotation(Side6, 0F, 0F, 0F);
      Side7 = new ModelRenderer(this, 0, 45);
      Side7.addBox(0F, 0F, 0F, 16, 1, 1);
      Side7.setRotationPoint(7F, 23F, 8F);
      Side7.setTextureSize(128, 64);
      Side7.mirror = true;
      setRotation(Side7, 0F, 1.570796F, 0F);
      Side8 = new ModelRenderer(this, 0, 45);
      Side8.addBox(0F, 0F, 0F, 16, 1, 1);
      Side8.setRotationPoint(-8F, 23F, 8F);
      Side8.setTextureSize(128, 64);
      Side8.mirror = true;
      setRotation(Side8, 0F, 1.570796F, 0F);
      Side9 = new ModelRenderer(this, 0, 28);
      Side9.addBox(0F, 0F, 0F, 1, 16, 1);
      Side9.setRotationPoint(-8F, 8F, -8F);
      Side9.setTextureSize(128, 64);
      Side9.mirror = true;
      setRotation(Side9, 0F, 0F, 0F);
      Side10 = new ModelRenderer(this, 0, 28);
      Side10.addBox(0F, 0F, 0F, 1, 16, 1);
      Side10.setRotationPoint(-8F, 8F, 7F);
      Side10.setTextureSize(128, 64);
      Side10.mirror = true;
      setRotation(Side10, 0F, 0F, 0F);
      Side11 = new ModelRenderer(this, 0, 28);
      Side11.addBox(0F, 0F, 0F, 1, 16, 1);
      Side11.setRotationPoint(7F, 8F, -8F);
      Side11.setTextureSize(128, 64);
      Side11.mirror = true;
      setRotation(Side11, 0F, 0F, 0F);
      Side12 = new ModelRenderer(this, 0, 28);
      Side12.addBox(0F, 0F, 0F, 1, 16, 1);
      Side12.setRotationPoint(7F, 8F, 7F);
      Side12.setTextureSize(128, 64);
      Side12.mirror = true;
      setRotation(Side12, 0F, 0F, 0F);
      Support13 = new ModelRenderer(this, 0, 47);
      Support13.addBox(0F, 0F, 0F, 1, 1, 16);
      Support13.setRotationPoint(6F, 14F, -8F);
      Support13.setTextureSize(128, 64);
      Support13.mirror = true;
      setRotation(Support13, 0F, 0F, 0F);
      Support14 = new ModelRenderer(this, 0, 47);
      Support14.addBox(0F, 0F, 0F, 1, 1, 16);
      Support14.setRotationPoint(6F, 17F, -8F);
      Support14.setTextureSize(128, 64);
      Support14.mirror = true;
      setRotation(Support14, 0F, 0F, 0F);
      Support15 = new ModelRenderer(this, 0, 47);
      Support15.addBox(0F, 0F, 0F, 1, 1, 16);
      Support15.setRotationPoint(-7F, 14F, -8F);
      Support15.setTextureSize(128, 64);
      Support15.mirror = true;
      setRotation(Support15, 0F, 0F, 0F);
      Support16 = new ModelRenderer(this, 0, 47);
      Support16.addBox(0F, 0F, 0F, 1, 1, 16);
      Support16.setRotationPoint(-7F, 17F, -8F);
      Support16.setTextureSize(128, 64);
      Support16.mirror = true;
      setRotation(Support16, 0F, 0F, 0F);
  }
  
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Bottom.render(f5);
    Front.render(f5);
    Back.render(f5);
    Right.render(f5);
    Left.render(f5);
    Top.render(f5);
    Power_Source.render(f5);
    Support1.render(f5);
    Support2.render(f5);
    Support3.render(f5);
    Support4.render(f5);
    Support5.render(f5);
    Support6.render(f5);
    Support7.render(f5);
    Support8.render(f5);
    Support9.render(f5);
    Support10.render(f5);
    Support11.render(f5);
    Support12.render(f5);
    Side1.render(f5);
    Side2.render(f5);
    Side3.render(f5);
    Side4.render(f5);
    Side5.render(f5);
    Side6.render(f5);
    Side7.render(f5);
    Side8.render(f5);
    Side9.render(f5);
    Side10.render(f5);
    Side11.render(f5);
    Side12.render(f5);
    Support13.render(f5);
    Support14.render(f5);
    Support15.render(f5);
    Support16.render(f5);
  }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int[] output) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);    
    Power_Source.render(f5);
    Support1.render(f5);
    Support2.render(f5);
    Support3.render(f5);
    Support4.render(f5);
    Support5.render(f5);
    Support6.render(f5);
    Support7.render(f5);
    Support8.render(f5);
    Support9.render(f5);
    Support10.render(f5);
    Support11.render(f5);
    Support12.render(f5);
    Side1.render(f5);
    Side2.render(f5);
    Side3.render(f5);
    Side4.render(f5);
    Side5.render(f5);
    Side6.render(f5);
    Side7.render(f5);
    Side8.render(f5);
    Side9.render(f5);
    Side10.render(f5);
    Side11.render(f5);
    Side12.render(f5);
    Support13.render(f5);
    Support14.render(f5);
    Support15.render(f5);
    Support16.render(f5);
    
    if(output[0]==0){
        Bottom.render(f5);
    }
    if(output[1]==0){
        Top.render(f5);
    }
    if(output[2]==0){
    	Back.render(f5);
    }
    if(output[3]==0){
    	Front.render(f5);
    }
    if(output[4]==0){
    	Left.render(f5);
    }
    if(output[5]==0){
    	Right.render(f5);
    }

	Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("Calculator:textures/model/capacitor_export.png"));
    if(output[0]==1){
        Bottom.render(f5);
    }
    if(output[1]==1){
        Top.render(f5);
    }
    if(output[2]==1){
    	Back.render(f5);
    }
    if(output[3]==1){
    	Front.render(f5);
    }
    if(output[4]==1){
        Left.render(f5);
    }
    if(output[5]==1){
        Right.render(f5);
    }
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
