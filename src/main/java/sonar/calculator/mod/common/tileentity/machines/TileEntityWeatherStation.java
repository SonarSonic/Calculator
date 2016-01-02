package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityWeatherStation extends TileEntity {

	public double angle;
	public boolean loaded;
	public int x, z, ticks;
	
	public void updateEntity(){
		super.updateEntity();
		if(!loaded){
			loaded=true;
			this.setAngle();
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);	
		}
		if(ticks<25){
			ticks++;
		}else if(ticks>=25){
			ticks=0;
			this.setAngle();
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);	
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);		
		angle = nbt.getDouble("angle");
		loaded = nbt.getBoolean("loaded");
		x = nbt.getInteger("xMAST");
		z = nbt.getInteger("zMAST");
		ticks = nbt.getInteger("ticks");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble("angle", angle);
		nbt.setBoolean("loaded", loaded);
		nbt.setInteger("xMAST", x);
		nbt.setInteger("zMAST", z);
		nbt.setInteger("ticks", ticks);
	}
	
	public void setAngle(){		
		double distance=100;
		boolean found = false;
		for(int X=-10; X<=10; X++){
			for(int Z=-10; Z<=10; Z++){		
				if(this.worldObj.getTileEntity(xCoord+X, yCoord, zCoord+Z)!=null && this.worldObj.getTileEntity(xCoord+X, yCoord, zCoord+Z) instanceof TileEntityConductorMast){
					double mastDist = this.worldObj.getTileEntity(xCoord+X, yCoord, zCoord+Z).getDistanceFrom(xCoord, yCoord, zCoord);
					if(distance>mastDist){
						distance=mastDist;
					angle = Math.toDegrees(Math.atan2(Z, X)) -90;
					this.x = xCoord+X;
					this.z = zCoord+Z;
					found=true;
					}
				}				
			}
		}

		if(!found){
		this.angle=1000;
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}else{
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}

	}
	 @SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
	  return 65536.0D;
	}
	 
	 @Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}
}
