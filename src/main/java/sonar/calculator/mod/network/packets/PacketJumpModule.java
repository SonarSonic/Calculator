package sonar.calculator.mod.network.packets;

import java.util.Random;

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
import sonar.core.api.SonarAPI;
import sonar.core.api.utils.ActionType;

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
				ItemStack held = player.getHeldItemMainhand();
				if (message.pos.getY() <= 0) {
					return null;
				}
				if (!held.isEmpty() && held.getItem() instanceof IModuleProvider && SonarAPI.getEnergyHelper().canTransferEnergy(held)!=null) {
					//IEnergyContainerItem item = (IEnergyContainerItem) held.getItem();
					long maxRemove = SonarAPI.getEnergyHelper().extractEnergy(held, 1000, ActionType.SIMULATE);
					if (player.capabilities.isCreativeMode || maxRemove >= 1000) {
						player.setPositionAndUpdate(message.pos.getX() + 0.5, message.pos.getY() + 1, message.pos.getZ() + 0.5);

						player.getEntityWorld().playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDEREYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.8F);

						if (!player.capabilities.isCreativeMode){
							SonarAPI.getEnergyHelper().extractEnergy(held, 1000, ActionType.PERFORM);
						}
						return new PacketJumpModule(message.pos);
					}
				}

			} else if (player != null && ctx.side == Side.CLIENT) {
				Random rand = new Random();
				for (int i = 0; i < 32; ++i) {
					player.getEntityWorld().spawnParticle(EnumParticleTypes.PORTAL, player.posX, player.posY + rand.nextDouble() * 2.0D, player.posZ, rand.nextGaussian(), 0.0D, rand.nextGaussian(), new int[0]);
				}
				/*
				int i = 12;
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
	*/
			}
			return null;
		}
	}
}
