package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import sonar.calculator.mod.api.items.IModuleProvider;
import sonar.core.SonarCore;
import sonar.core.api.energy.EnergyType;
import sonar.core.api.utils.ActionType;
import sonar.core.handlers.energy.EnergyTransferHandler;

import java.util.Random;

public class PacketJumpModule implements IMessage {

	public BlockPos pos;

	public PacketJumpModule() {}

	public PacketJumpModule(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	public static class Handler implements IMessageHandler<PacketJumpModule, IMessage> {

		@Override
		public IMessage onMessage(PacketJumpModule message, MessageContext ctx) {
			EntityPlayer player = SonarCore.proxy.getPlayerEntity(ctx);
			if (player != null && ctx.side == Side.SERVER) {
				ItemStack held = player.getHeldItemMainhand();
				if (message.pos.getY() <= 0) {
					return null;
				}
				if (!held.isEmpty() && held.getItem() instanceof IModuleProvider) {

					long maxRemove = EnergyTransferHandler.INSTANCE_SC.dischargeItem(player.getHeldItemMainhand(), 1000, EnergyType.FE, ActionType.SIMULATE);
					if (player.capabilities.isCreativeMode || maxRemove >= 1000) {
						player.setPositionAndUpdate(message.pos.getX() + 0.5, message.pos.getY() + 1, message.pos.getZ() + 0.5);
						player.getEntityWorld().playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDEREYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.8F);
						if (!player.capabilities.isCreativeMode) {
							EnergyTransferHandler.INSTANCE_SC.dischargeItem(player.getHeldItemMainhand(), 1000, EnergyType.FE, ActionType.PERFORM);
						}
						return new PacketJumpModule(message.pos);
					}
				}
			} else if (player != null && ctx.side == Side.CLIENT) {
				Random rand = new Random();
				for (int i = 0; i < 32; ++i) {
					player.getEntityWorld().spawnParticle(EnumParticleTypes.PORTAL, player.posX, player.posY + rand.nextDouble() * 2.0D, player.posZ, rand.nextGaussian(), 0.0D, rand.nextGaussian());
				}
			}
			return null;
		}
	}
}
