package sonar.calculator.mod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CalculatorPlanks extends Block {

	public int type;
	public CalculatorPlanks(int type) {
		super(Material.wood);
		this.type=type;
	    setHarvestLevel("axe", 0);
	    setHardness(0.7F);
	}
	
	  
	  @Override
	@SideOnly(Side.CLIENT)
	  public void registerBlockIcons(IIconRegister iconRegister) {		
		 switch(type){
		 case 0:this.blockIcon = iconRegister.registerIcon("Calculator:wood/planks_amethyst");break;
		 case 1:this.blockIcon = iconRegister.registerIcon("Calculator:wood/planks_tanzanite");break;
		 case 2:this.blockIcon = iconRegister.registerIcon("Calculator:wood/planks_pear");break;
		 case 3:this.blockIcon = iconRegister.registerIcon("Calculator:wood/planks_diamond");break;
		 }
	  }
	  @Override
	@SideOnly(Side.CLIENT)
	  public IIcon getIcon(int par1, int par2)
	  {
	    return this.blockIcon;
	  }
}
