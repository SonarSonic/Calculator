package sonar.calculator.mod.common.block.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IStability;
import sonar.calculator.mod.api.IWrench;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipes;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarMaterials;
import sonar.core.utils.helpers.FontHelper;

public class ResearchChamber extends SonarMachineBlock implements IWrench {

	private Random rand = new Random();

	public ResearchChamber() {
		super(SonarMaterials.machine);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - (0.0625F*8), 1.0F);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entityplayer, ItemStack itemstack) {
		super.onBlockPlacedBy(world, x, y, z, entityplayer, itemstack);
		TileEntity target = world.getTileEntity(x, y, z);
		if(target!=null && target instanceof TileEntityResearchChamber){
			TileEntityResearchChamber chamber = (TileEntityResearchChamber) target;
			chamber.sendResearch();
		}
	}
	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null && world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityResearchChamber) {
			TileEntityResearchChamber entity = (TileEntityResearchChamber) world.getTileEntity(x, y, z);
			if (entity.slots[0] == null && player.getHeldItem() != null) {
				if ((CalculatorRecipes.recipes().getID(player.getHeldItem()) != 0 || player.getHeldItem().getItem() == Calculator.circuitBoard && player.getHeldItem().getItem() instanceof IStability
						&& ((IStability) player.getHeldItem().getItem()).getStability(player.getHeldItem()))) {
					ItemStack stack = new ItemStack(player.getHeldItem().getItem(), 1, player.getHeldItem().getItemDamage());
					stack.setTagCompound(player.getHeldItem().getTagCompound());
					entity.slots[0] = stack;
					player.getHeldItem().stackSize--;
					world.markBlockForUpdate(x, y, z);
					world.addBlockEvent(x, y, z, entity.blockType, 1, 0);
				}
			} else if (!world.isRemote && player.isSneaking() && entity.slots[0] != null) {
				if (player != null) {
					int[] unblocked = entity.unblockedList();
					if (entity.isBlocked(entity.lastResearch) && entity.lastResearch != 0) {
						entity.unblockItem(entity.lastResearch);
						if (CalculatorRecipes.recipes().discovery(entity.unblockedList(), entity.lastResearch)) {
							FontHelper.sendMessage(FontHelper.translate("research.recipeNew"), entity.getWorldObj(), player);
						}
					}

				}
				ForgeDirection dir = ForgeDirection.getOrientation(entity.blockMetadata);

				EntityItem item = new EntityItem(world, x + (dir.offsetX * 2), y + 1, z + (dir.offsetZ * 2), new ItemStack(entity.slots[0].getItem(), 1, entity.slots[0].getItemDamage()));
				ItemStack itemstack = entity.slots[0];
				if (itemstack.hasTagCompound()) {
					item.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
				}
				entity.slots[0] = null;
				world.spawnEntityInWorld(item);
				world.markBlockForUpdate(x, y, z);
				world.addBlockEvent(x, y, z, entity.blockType, 1, 0);
			} else if (!world.isRemote) {
				player.openGui(Calculator.instance, CalculatorGui.ResearchChamber, world, x, y, z);
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityResearchChamber();
	}

	@Override
	public boolean dropStandard(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		int max = stack.stackTagCompound.getInteger("Max");
		int stored = stack.stackTagCompound.getInteger("Stored");
		if (max != 0) {
			list.add(FontHelper.translate("research.recipe") + ": " + stored + "/" + max);
		}
	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
	}
}
