package sonar.calculator.mod.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.misc.GuiModuleSelector;
import sonar.calculator.mod.common.containers.ContainerModuleSelector;
import sonar.calculator.mod.network.packets.*;

import java.util.HashMap;
import java.util.Map;

public class CalculatorCommon {

	public static void registerPackets() {
		Calculator.network.registerMessage(PacketCalculatorScreen.Handler.class, PacketCalculatorScreen.class, 0, Side.CLIENT);
		Calculator.network.registerMessage(PacketTeleportLinks.Handler.class, PacketTeleportLinks.class, 1, Side.CLIENT);
		Calculator.network.registerMessage(PacketJumpModule.Handler.class, PacketJumpModule.class, 2, Side.SERVER);
		Calculator.network.registerMessage(PacketJumpModule.Handler.class, PacketJumpModule.class, 3, Side.CLIENT);
		Calculator.network.registerMessage(PacketModuleSelection.Handler.class, PacketModuleSelection.class, 4, Side.SERVER);
		Calculator.network.registerMessage(PacketPlayerResearch.Handler.class, PacketPlayerResearch.class, 5, Side.CLIENT);
	}

	public void registerRenderThings() {}
}
