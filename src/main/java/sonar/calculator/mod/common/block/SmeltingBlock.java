package sonar.calculator.mod.common.block;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.item.misc.UpgradeCircuit;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.calculator.mod.common.tileentity.TileEntityMachine;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.BlockInteraction;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SmeltingBlock extends SonarMachineBlock {
	@SideOnly(Side.CLIENT)
	private IIcon front, frontActive;
	@SideOnly(Side.CLIENT)
	private IIcon front2, frontActive2;
	@SideOnly(Side.CLIENT)
	private IIcon slot1;
	@SideOnly(Side.CLIENT)
	private IIcon slot2;
	public int type;

	public SmeltingBlock(int num) {
		super(SonarMaterials.machine);
		this.type = num;
	}

	@Override
	public boolean isOpaqueCube() {
		return type != 4 && type != 5 && type != 7 ? false : true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {

		switch (type) {
		case 4:
			this.front = iconRegister.registerIcon("Calculator:stoneseperator_still_front1");
			this.front2 = iconRegister.registerIcon("Calculator:stoneseperator_still_front2");
			this.frontActive = iconRegister.registerIcon("Calculator:stoneseperator_animate_front1");
			this.frontActive2 = iconRegister.registerIcon("Calculator:stoneseperator_animate_front2");
			this.slot1 = iconRegister.registerIcon("Calculator:stoneseperator_slot1");
			this.slot2 = iconRegister.registerIcon("Calculator:stoneseperator_slot2");
			break;
		case 5:
			this.front = iconRegister.registerIcon("Calculator:algorithmseperator1_slot1");
			this.front2 = iconRegister.registerIcon("Calculator:algorithmseperator1_slot2");
			this.frontActive = iconRegister.registerIcon("Calculator:algorithmseperatoranimate_front1");
			this.frontActive2 = iconRegister.registerIcon("Calculator:algorithmseperatoranimate_front2");
			this.slot1 = iconRegister.registerIcon("Calculator:algorithmseperator_slot1");
			this.slot2 = iconRegister.registerIcon("Calculator:algorithmseperator_slot2");
			break;
		case 7:
			this.front = iconRegister.registerIcon("Calculator:reinforced_furnace");
			this.front2 = iconRegister.registerIcon("Calculator:reinforced_furnace2");

			this.frontActive = iconRegister.registerIcon("Calculator:reinforced_furnace_active");
			this.frontActive2 = iconRegister.registerIcon("Calculator:reinforced_furnace2_active");
			this.slot1 = iconRegister.registerIcon("Calculator:stoneseperator_slot2");
			this.slot2 = iconRegister.registerIcon("Calculator:stoneseperator_slot1");
			break;
		}
		if (type < 4 || type == 6) {
			this.front = iconRegister.registerIcon("Calculator:overlays/machine_input");
			this.front2 = iconRegister.registerIcon("Calculator:overlays/machine_output");
			this.frontActive = iconRegister.registerIcon("Calculator:overlays/machine_input");
			this.frontActive2 = iconRegister.registerIcon("Calculator:overlays/machine_output");
			this.slot1 = iconRegister.registerIcon("Calculator:overlays/machine_input");
			this.slot2 = iconRegister.registerIcon("Calculator:overlays/machine_output");
			if (type == 6 || type == 3) {
				this.blockIcon = iconRegister.registerIcon("Calculator:analysis_side_slot1");
			} else {
				this.blockIcon = iconRegister.registerIcon("Calculator:machine_side");
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
		int metadata = access.getBlockMetadata(x, y, z);
		if (metadata == 0) {
			return getIcon(side, metadata);
		}
		TileEntity entity = access.getTileEntity(x, y, z);
		if (entity != null) {
			if (entity instanceof TileEntityAbstractProcess) {
				TileEntityAbstractProcess t = (TileEntityAbstractProcess) access.getTileEntity(x, y, z);
				if (side != metadata) {
					return t.getBlockTexture(side, metadata) ? this.slot1 : this.slot2;
				}
				if (side == metadata) {
					return t.isActive() ? (t.getBlockTexture(side, metadata) ? this.frontActive : this.frontActive2) : t.getBlockTexture(side, metadata) ? this.front : this.front2;
				}
			} else if (entity instanceof TileEntityAbstractProcess) {
				TileEntityAbstractProcess t = (TileEntityAbstractProcess) access.getTileEntity(x, y, z);
				if (side != metadata) {
					return t.getBlockTexture(side, metadata) ? this.slot1 : this.slot2;
				}
				if (side == metadata) {
					return t.isActive() ? (t.getBlockTexture(side, metadata) ? this.frontActive : this.frontActive2) : t.getBlockTexture(side, metadata) ? this.front : this.front2;
				}
			}
		}
		return this.slot1;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return type < 4 || type == 6 ? this.blockIcon : side == metadata ? this.slot2 : side == 0 ? this.slot1 : side == 1 ? this.slot1 : (metadata == 0) && (side == 3) ? this.front : this.slot1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null) {
			if (entity instanceof TileEntityAbstractProcess) {
				TileEntityAbstractProcess te = (TileEntityAbstractProcess) world.getTileEntity(x, y, z);
				if (te.isActive()) {
					spawnParticles(world, x, y, z, random);
				}
			} else if (entity instanceof TileEntityAbstractProcess) {
				TileEntityAbstractProcess te = (TileEntityAbstractProcess) world.getTileEntity(x, y, z);
				if (te.isActive()) {
					spawnParticles(world, x, y, z, random);
				}
			}

		}
	}

	public void spawnParticles(World world, int x, int y, int z, Random random) {
		int direction = world.getBlockMetadata(x, y, z);

		float x1 = x + 0.5F;
		float y1 = y + random.nextFloat();
		float z1 = z + 0.5F;

		float f = 0.1F;
		float f1 = random.nextFloat() * 0.6F - 0.3F;

		if (direction == 4) {
			world.spawnParticle("smoke", x1 - f, y1, z1 + f1, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", x1 - f, y1, z1 + f1, 0.0D, 0.0D, 0.0D);
		}

		if (direction == 5) {
			world.spawnParticle("smoke", x1 + f, y1, z1 + f1, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", x1 + f, y1, z1 + f1, 0.0D, 0.0D, 0.0D);
		}

		if (direction == 2) {
			world.spawnParticle("smoke", x1 + f1, y1, z1 - f, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", x1 + f1, y1, z1 - f, 0.0D, 0.0D, 0.0D);
		}

		if (direction == 3) {
			world.spawnParticle("smoke", x1 + f1, y1, z1 + f, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", x1 + f1, y1, z1 + f, 0.0D, 0.0D, 0.0D);
		}
	}

	public void openGui(World world, int x, int y, int z, EntityPlayer player) {
		switch (type) {
		case 0:
			player.openGui(Calculator.instance, CalculatorGui.ExtractionChamber, world, x, y, z);
			break;
		case 1:
			player.openGui(Calculator.instance, CalculatorGui.RestorationChamber, world, x, y, z);
			break;
		case 2:
			player.openGui(Calculator.instance, CalculatorGui.ReassemblyChamber, world, x, y, z);
			break;
		case 3:
			player.openGui(Calculator.instance, CalculatorGui.ProcessingChamber, world, x, y, z);
			break;
		case 4:
			player.openGui(Calculator.instance, CalculatorGui.StoneSeperator, world, x, y, z);
			break;
		case 5:
			player.openGui(Calculator.instance, CalculatorGui.AlgorithmSeperator, world, x, y, z);
			break;
		case 6:
			player.openGui(Calculator.instance, CalculatorGui.PrecisionChamber, world, x, y, z);
			break;
		case 7:
			player.openGui(Calculator.instance, CalculatorGui.ReinforcedFurnace, world, x, y, z);
			break;
		}
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, BlockInteraction interact) {

		if (player != null) {
			if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof UpgradeCircuit) {
				return false;
			} else if (player.getHeldItem() != null && player.getHeldItem().getItem() == Calculator.wrench) {
				return false;
			} else {
				if (!world.isRemote) {
					openGui(world, x, y, z, player);
				}
				return true;
			}
		}

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		switch (type) {
		case 0:
			return new TileEntityMachine.ExtractionChamber();
		case 1:
			return new TileEntityMachine.RestorationChamber();
		case 2:
			return new TileEntityMachine.ReassemblyChamber();
		case 3:
			return new TileEntityMachine.ProcessingChamber();
		case 4:
			return new TileEntityMachine.StoneSeperator();
		case 5:
			return new TileEntityMachine.AlgorithmSeperator();
		case 6:
			return new TileEntityMachine.PrecisionChamber();
		case 7:
			return new TileEntityMachine.ReinforcedFurnace();

		}
		return null;
	}

	@Override
	public boolean dropStandard(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			int energyUsage = 0;
			int speed = 0;

			switch (type) {
			case 0:
				energyUsage = CalculatorConfig.getInteger("Extraction Chamber" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Extraction Chamber" + "Base Speed");
				break;
			case 1:
				energyUsage = CalculatorConfig.getInteger("Restoration Chamber" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Restoration Chamber" + "Base Speed");
				break;
			case 2:
				energyUsage = CalculatorConfig.getInteger("Reassembly Chamber" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Reassembly Chamber" + "Base Speed");
				break;
			case 3:
				energyUsage = CalculatorConfig.getInteger("Processing Chamber" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Processing Chamber" + "Base Speed");
				break;
			case 4:
				energyUsage = CalculatorConfig.getInteger("Stone Seperator" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Stone Seperator" + "Base Speed");
				break;
			case 5:
				energyUsage = CalculatorConfig.getInteger("Algorithm Seperator" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Algorithm Seperator" + "Base Speed");
				break;
			case 6:
				energyUsage = CalculatorConfig.getInteger("Precision Chamber" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Precision Chamber" + "Base Speed");
				break;
			case 7:
				energyUsage = CalculatorConfig.getInteger("Reinforced Furnace" + "Energy Usage");
				speed = CalculatorConfig.getInteger("Reinforced Furnace" + "Base Speed");
				break;
			}
			list.add(FontHelper.translate("Process Speed: ")+ EnumChatFormatting.WHITE + speed + " ticks");
			list.add(FontHelper.translate("Energy Usage: ") + EnumChatFormatting.WHITE +  energyUsage + " RF per operation");
			list.add(FontHelper.translate("Consumption: ")+ EnumChatFormatting.WHITE + energyUsage / speed + " RF/t");
		}else{
			list.add("Hold shift for more info");			
		}
	}

}
