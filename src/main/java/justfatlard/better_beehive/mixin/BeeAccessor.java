package justfatlard.better_beehive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;

import justfatlard.better_beehive.util.IBeeAccessor;

@Mixin(BeeEntity.class)
public abstract class BeeAccessor implements IBeeAccessor {
	@Shadow
	private BlockPos hivePos;

	@Shadow
	protected abstract boolean canEnterHive();

	@Override
	public boolean canBeeEnterHive() {
		return canEnterHive();
	}

	@Override
	public BlockPos getHivePos() {
		return hivePos;
	}

	@Override
	public void setHivePos(BlockPos pos) {
		this.hivePos = pos;
	}
}
