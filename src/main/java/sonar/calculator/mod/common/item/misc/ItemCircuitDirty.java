package sonar.calculator.mod.common.item.misc;

import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import sonar.core.common.item.SonarItem;

import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCircuitDirty
  extends SonarItem
{
  private static final String __OBFID = "CL_00000032";
  
  @Override
@SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister register)
  {
    DirtyType[] atype = DirtyType.values();
    int i = atype.length;
    
    for (int j = 0; j < i; j++)
    {
      DirtyType type = atype[j];
      type.registerIcon(register);
    }
  }
  

  @SideOnly(Side.CLIENT)
  public IIcon getIconFromDamage(int p_77617_1_)
  {
    DirtyType type = DirtyType.getTypeFromDamage(p_77617_1_);
    return type.getIcon();
  }
  
  @SideOnly(Side.CLIENT)
  public void getSubItems(Item item, CreativeTabs tab, List list)
  {
    DirtyType[] atype = DirtyType.values();
    int i = atype.length;
    
    for (int j = 0; j < i; j++)
    {
      DirtyType type = atype[j];
      
      list.add(new ItemStack(this, 1, type.getItemDamage()));
    }
  }
  

  public String getUnlocalizedName(ItemStack stack)
  {
    DirtyType type = DirtyType.getTypeFromStack(stack);
    return getUnlocalizedName() + "." + type.name();
  }
  
  public static enum DirtyType
  {
    C1(0, "1"),  C2(1, "2"),  C3(2, "3"),  C4(3, "4"), 
    C5(4, "5"),  C6(5, "6"),  C7(6, "7"),  C8(7, "8"), 
    C9(8, "9"),  C10(9, "10"),  C11(10, "11"),  C12(11, "12"), 
    C13(12, "13"),  C14(13, "14");
    

    private static final Map circuits;
    private final int number;
    private final String name;
    @SideOnly(Side.CLIENT)
    private IIcon icon;
    private static final String __OBFID = "CL_00000033";
    
    private DirtyType(int number, String name)
    {
      this.number = number;
      this.name = name;
    }
    
    public int getItemDamage() {
      return this.number;
    }
    
    public String getUnlocalizedNamePart() { return this.name; }
    

    @SideOnly(Side.CLIENT)
    public void registerIcon(IIconRegister register)
    {
      this.icon = register.registerIcon("Calculator:circuits/circuit" + this.name + "_dirty");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon() {
      return this.icon;
    }
    
    public static DirtyType getTypeFromDamage(int par)
    {
      DirtyType type = (DirtyType)circuits.get(Integer.valueOf(par));
      return type == null ? C1 : type;
    }
    
    public static DirtyType getTypeFromStack(ItemStack p_150978_0_)
    {
      return (p_150978_0_.getItem() instanceof ItemCircuitDirty) ? getTypeFromDamage(p_150978_0_.getItemDamage()) : C1;
    }
    
    static
    {
      circuits = Maps.newHashMap();
      











































      DirtyType[] var0 = values();
      int var1 = var0.length;
      
      for (int var2 = 0; var2 < var1; var2++)
      {
        DirtyType var3 = var0[var2];
        circuits.put(Integer.valueOf(var3.getItemDamage()), var3);
      }
    }
  }
}
