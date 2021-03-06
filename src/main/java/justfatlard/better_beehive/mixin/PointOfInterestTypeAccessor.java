package justfatlard.better_beehive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Set;

@Mixin(PointOfInterestType.class)
public interface PointOfInterestTypeAccessor {

	@Invoker
	static PointOfInterestType invokeRegister(String id, Set<BlockState> set, int i, int j) {
		return null;
	}

	@Invoker
	static Set<BlockState> getGetAllStatesOf(Block block) {
		return null;
	}
}
