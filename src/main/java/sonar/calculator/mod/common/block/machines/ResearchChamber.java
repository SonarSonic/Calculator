package sonar.calculator.mod.common.block.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.IGuiTile;

public class ResearchChamber extends SonarMachineBlock {

	public ResearchChamber() {
		super(SonarMaterials.machine, true, true);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - (0.0625F * 8), 1.0F);
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null && !world.isRemote) {
			TileEntity target = world.getTileEntity(pos);
			if (target != null && target instanceof TileEntityResearchChamber) {
				TileEntityResearchChamber chamber = (TileEntityResearchChamber) target;
				String chamberName = chamber.playerName.getObject();
				if (chamberName.equals(player.getName())) {
					FMLNetworkHandler.openGui(player, Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
				} else {
					FontHelper.sendMessage("Access Denied: " + chamberName, world, player);
				}
			}
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack itemstack) {
		super.onBlockPlacedBy(world, pos, state, player, itemstack);
		TileEntity target = world.getTileEntity(pos);
		if (target != null && target instanceof TileEntityResearchChamber) {
			TileEntityResearchChamber chamber = (TileEntityResearchChamber) target;
			if (player != null && player instanceof EntityPlayer) {
				chamber.playerName.setObject(player.getName());
			}
		}
	}

	/* @Override public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack) { super.onBlockPlacedBy(world, x, y, z, entityplayer, itemstack); TileEntity target = world.getTileEntity(x, y, z); if (target != null && target instanceof TileEntityResearchChamber) { TileEntityResearchChamber chamber = (TileEntityResearchChamber) target; chamber.sendResearch(); } }
	 * 
	 * @Override public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) { if (player != null && world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityResearchChamber) { TileEntityResearchChamber entity = (TileEntityResearchChamber) world.getTileEntity(x, y, z); if (entity.slots()[0] == null && player.getHeldItemMainhand() != null) { if ((RecipeRegistry.CalculatorRecipes.instance().validInput(player.getHeldItemMainhand()) || player.getHeldItemMainhand().getItem() == Calculator.circuitBoard && player.getHeldItemMainhand().getItem() instanceof IStability && ((IStability) player.getHeldItemMainhand().getItem()).getStability(player.getHeldItemMainhand()))) { ItemStack stack = new ItemStack(player.getHeldItemMainhand().getItem(), 1, player.getHeldItemMainhand().getItemDamage()); stack.setTagCompound(player.getHeldItemMainhand().getTagCompound()); entity.slots()[0] = stack; player.getHeldItemMainhand().stackSize--; world.markBlockForUpdate(x, y, z); world.addBlockEvent(x, y, z, entity.blockType, 1, 0); } } else if (!world.isRemote && interact.type == BlockInteractionType.SHIFT_RIGHT && entity.slots()[0] != null) { EnumFacing dir = EnumFacing.getOrientation(entity.blockMetadata);
	 * 
	 * EntityItem item = new EntityItem(world, x + (dir.offsetX * 2), y + 1, z + (dir.offsetZ * 2), new ItemStack(entity.slots()[0].getItem(), 1, entity.slots()[0].getItemDamage())); ItemStack itemstack = entity.slots()[0]; if (itemstack.hasTagCompound()) { item.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy()); } entity.slots()[0] = null; world.spawnEntityInWorld(item); world.markBlockForUpdate(x, y, z); world.addBlockEvent(x, y, z, entity.blockType, 1, 0); } else if (!world.isRemote) { player.openGui(Calculator.instance, IGuiTile.ID, world, x, y, z); } } return true; } */
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityResearchChamber();
	}

}
