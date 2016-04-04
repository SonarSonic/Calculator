package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFabricationArm extends ModelBase {

	ModelRenderer Arm1;
	ModelRenderer Arm2;
	ModelRenderer Arm3;
	ModelRenderer Arm4;
	ModelRenderer Base;

	public ModelFabricationArm() {
		textureWidth = 64;
		textureHeight = 32;

		Arm1 = new ModelRenderer(this, 0, 24);
		Arm1.addBox(-1F, -0.5F, -6F, 1, 1, 7);
		Arm1.setRotationPoint(-1F, 22F, -0.5F);
		Arm1.setTextureSize(64, 32);
		Arm1.mirror = true;
		setRotation(Arm1, -0.7853982F, 0F, 0F);
		Arm2 = new ModelRenderer(this, 0, 24);
		Arm2.addBox(0F, -0.5F, -6F, 1, 1, 7);
		Arm2.setRotationPoint(1F, 22F, -0.5F);
		Arm2.setTextureSize(64, 32);
		Arm2.mirror = true;
		setRotation(Arm2, -0.7853982F, 0F, 0F);
		Arm3 = new ModelRenderer(this, 16, 24);
		Arm3.addBox(-1F, -0.5F, -6F, 2, 1, 7);
		Arm3.setRotationPoint(0F, 18F, -4.5F);
		Arm3.setTextureSize(64, 32);
		Arm3.mirror = true;
		setRotation(Arm3, 0F, 0F, 0F);
		Arm4 = new ModelRenderer(this, 0, 21);
		Arm4.addBox(-1F, 0.5F, -5F, 2, 2, 1);
		Arm4.setRotationPoint(0F, 18F, -4.5F);
		Arm4.setTextureSize(64, 32);
		Arm4.mirror = true;
		setRotation(Arm4, 0F, 0F, 0F);
		Base = new ModelRenderer(this, 6, 20);
		Base.addBox(-1F, -1F, -1F, 2, 2, 2);
		Base.setRotationPoint(0F, 23F, 0F);
		Base.setTextureSize(64, 32);
		Base.mirror = true;
		setRotation(Base, 0F, 0F, 0F);

	}

	public void renderTile(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, boolean lastActive, boolean isActive, int progress) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		//if (lastActive != isActive) {
		setRotation(Arm3, ((float)progress*0.50F)/100,0,0);
		setRotation(Arm4, ((float)progress*0.50F)/100,0,0);
			if (isActive) {
				// GET PROGRESS SET ANGLES :)
			} else {
				// start return process
			}
		//}
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Arm1.render(f5);
		Arm2.render(f5);
		Arm3.render(f5);
		Arm4.render(f5);
		Base.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
