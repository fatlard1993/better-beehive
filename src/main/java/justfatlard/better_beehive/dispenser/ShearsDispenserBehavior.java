package justfatlard.better_beehive.dispenser;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import justfatlard.better_beehive.block.ModdedBeehiveBlock;
import justfatlard.better_beehive.util.BeeState;

import java.util.Iterator;
import java.util.List;

public class ShearsDispenserBehavior extends FallibleItemDispenserBehavior {
	protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
		World world = pointer.getWorld();
		if (!world.isClient()) {
			this.success = false;
			BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
			List<SheepEntity> list = world.getNonSpectatingEntities(SheepEntity.class, new Box(blockPos));
			Iterator var6 = list.iterator();

			while(var6.hasNext()) {
				SheepEntity sheepEntity = (SheepEntity)var6.next();
				if (sheepEntity.isAlive() && !sheepEntity.isSheared() && !sheepEntity.isBaby()) {
					sheepEntity.dropItems();
					if (stack.damage(1, world.random, null)) {
						stack.setCount(0);
					}

					this.success = true;
					break;
				}
			}

			if (!this.success) {
				BlockState blockState = world.getBlockState(blockPos);
				if (blockState.matches(BlockTags.BEEHIVES)) {
					Block block = blockState.getBlock();
					if(block instanceof BeehiveBlock) {
						int i = blockState.get(BeehiveBlock.HONEY_LEVEL);
						if (i >= 5) {
							if (stack.damage(1, world.random, null)) {
								stack.setCount(0);
							}

							BeehiveBlock.dropHoneycomb(world, blockPos);
							((BeehiveBlock)block).takeHoney(world, blockState, blockPos, null, BeehiveBlockEntity.BeeState.BEE_RELEASED);
							this.success = true;
						}
					} else if(block instanceof ModdedBeehiveBlock) {
						int i = blockState.get(((ModdedBeehiveBlock) block).getHoneyProperty());
						if (i >= 5) {
							if (stack.damage(1, world.random, null)) {
								stack.setCount(0);
							}

							((ModdedBeehiveBlock)block).takeHoney(world, blockState, blockPos, null, BeeState.BEE_RELEASED);
							this.success = true;
						}
					}
				}
			}
		}

		return stack;
	}
}
