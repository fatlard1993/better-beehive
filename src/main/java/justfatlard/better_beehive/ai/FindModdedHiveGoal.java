package justfatlard.better_beehive.ai;

import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;

import justfatlard.better_beehive.entity.ModdedBeehiveBlockEntity;
import justfatlard.better_beehive.registry.BeePOI;
import justfatlard.better_beehive.util.IBeeAccessor;

import java.util.Optional;
import java.util.stream.Stream;

public class FindModdedHiveGoal extends NotAngryGoal {

	public FindModdedHiveGoal(BeeEntity bee) {
		super(bee);
	}

	@Override
	public boolean canBeeStart() {
		return this.getBee().age % 10 == 0 && !this.getBee().hasHive();
	}

	@Override
	public boolean canBeeContinue() {
		return false;
	}

	@Override
	public void start() {
		Stream<BlockPos> stream = this.method_23742(20);
		Optional<BlockPos> optional = stream.filter((blockPos) -> {
			BlockEntity blockEntity = this.getBee().world.getBlockEntity(blockPos);
			if ((blockEntity instanceof BeehiveBlockEntity && !((BeehiveBlockEntity) blockEntity).isFullOfBees()) || (blockEntity instanceof ModdedBeehiveBlockEntity && !((ModdedBeehiveBlockEntity) blockEntity).isFullOfBees())) {
				Path path = this.getBee().getNavigation().findPathTo(blockPos, 20);
				return path != null;
			} else {
				return false;
			}
		}).findFirst();
		optional.ifPresent((blockPos) -> {
			((IBeeAccessor) this.getBee()).setHivePos(blockPos);
		});
	}

	private Stream<BlockPos> method_23742(int i) {
		BlockPos blockPos = new BlockPos(this.getBee());
		if (this.getBee().world instanceof ServerWorld) {
			Stream<PointOfInterest> stream = ((ServerWorld) this.getBee().world).getPointOfInterestStorage().get((pointOfInterestType) ->
							pointOfInterestType == BeePOI.MODDED_BEEHIVES,
					blockPos,
					i,
					PointOfInterestStorage.OccupationStatus.ANY
			);
			return stream.map(PointOfInterest::getPos);
		} else {
			return Stream.empty();
		}
	}
}
