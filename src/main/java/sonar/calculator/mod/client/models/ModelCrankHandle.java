package sonar.calculator.mod.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCrankHandle extends ModelBase {
	ModelRenderer base;
	ModelRenderer piece;
	ModelRenderer grasp;
	ModelRenderer joint;

	public ModelCrankHandle() {
		textureWidth = 64;
		textureHeight = 32;

		base = new ModelRenderer(this, 0, 14);
		base.addBox(0F, 0F, 0F, 2, 3, 2);
		base.setRotationPoint(-1F, 21F, -1F);
		base.setTextureSize(64, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		piece = new ModelRenderer(this, 0, 8);
		piece.addBox(0F, 0F, 0F, 2, 4, 2);
		piece.setRotationPoint(3F, 15F, -1F);
		piece.setTextureSize(64, 32);
		piece.mirror = true;
		setRotation(piece, 0F, 0F, 0F);
		grasp = new ModelRenderer(this, 0, 4);
		grasp.addBox(0F, 0F, 0F, 3, 1, 3);
		grasp.setRotationPoint(2.5F, 14F, -1.5F);
		grasp.setTextureSize(64, 32);
		grasp.mirror = true;
		setRotation(grasp, 0F, 0F, 0F);
		joint = new ModelRenderer(this, 0, 0);
		joint.addBox(0F, 0F, 0F, 6, 2, 2);
		joint.setRotationPoint(-1F, 19F, -1F);
		joint.setTextureSize(64, 32);
		joint.mirror = true;
		setRotation(joint, 0F, 0F, 0F);
	}

    @Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		base.render(f5);
		piece.render(f5);
		grasp.render(f5);
		joint.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
	}
}