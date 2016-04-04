package sonar.calculator.mod.network.packets;

import io.netty.buffer.ByteBuf;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import sonar.calculator.mod.api.items.IModuleProvider;
import sonar.core.SonarCore;
import cofh.api.energy.IEnergyContainerItem;

public class PacketJumpModule implements IMessage {

	public BlockPos pos;

	public PacketJumpModule() {
	}

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

		public IMessage onMessage(PacketJumpModule message, MessageContext ctx) {
			EntityPlayer player = SonarCore.proxy.getPlayerEntity(ctx);
			if (player != null && ctx.side == Side.SERVER) {
				ItemStack held = player.getHeldItem();
				if (message.pos.getY() <= 0) {
					return null;
				}
				if (held != null && held.getItem() instanceof IModuleProvider && held.getItem() instanceof IEnergyContainerItem) {
					IEnergyContainerItem item = (IEnergyContainerItem) held.getItem();
					if (player.capabilities.isCreativeMode || item.getEnergyStored(held) > 1000) {
						player.setPositionAndUpdate(message.pos.getX() + 0.5, message.pos.getY() + 1, message.pos.getZ() + 0.5);
						player.getEntityWorld().playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
						if (!player.capabilities.isCreativeMode)
							item.extractEnergy(held, 1000, true);
						// held.getTagCompound().setInteger("last", player.getAge());
						return new PacketJumpModule(message.pos);
					}
				}

			} else if (player != null && ctx.side == Side.CLIENT) {
				int i = 12;
				Random rand = new Random();
				for (int l = 0; l < i; ++l) {
					double d6 = message.pos.getX() + rand.nextFloat();
					double d1 = message.pos.getY() + 1 + rand.nextFloat();
					d6 = message.pos.getZ() + rand.nextFloat();
					double d3 = 0.0D;
					double d4 = 0.0D;
					double d5 = 0.0D;
					int i1 = rand.nextInt(2) * 2 - 1;
					int j1 = rand.nextInt(2) * 2 - 1;
					d3 = (rand.nextFloat() - 0.5D) * 0.125D;
					d4 = (rand.nextFloat() - 0.5D) * 0.125D;
					d5 = (rand.nextFloat() - 0.5D) * 0.125D;
					double d2 = message.pos.getZ() + 0.5D + 0.25D * j1;
					d5 = rand.nextFloat() * 1.0F * j1;
					double d0 = message.pos.getX() + 0.5D + 0.25D * i1;
					d3 = rand.nextFloat() * 1.0F * i1;

					player.getEntityWorld().spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
				}
				player.getEntityWorld().playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);

			}
			return null;
		}
	}
}
