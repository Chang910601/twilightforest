package twilightforest.world.feature;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import twilightforest.block.TFBlocks;

public class TFGenSmallTwilightOak extends WorldGenTrees implements IBlockSettable {

	public TFGenSmallTwilightOak(boolean notify) {
		this(notify, 4);
	}

	public TFGenSmallTwilightOak(boolean notify, int minHeight) {
		this(notify, minHeight,
				TFBlocks.twilight_log.getDefaultState(),
				TFBlocks.twilight_leaves.getDefaultState().with(BlockLeaves.CHECK_DECAY, false)
		);
	}

	protected TFGenSmallTwilightOak(boolean notify, int minHeight, BlockState trunk, BlockState leaves) {
		super(notify, minHeight, trunk, leaves, false);
	}

	@Override
	public final void setBlockAndNotify(World world, BlockPos pos, BlockState state) {
		setBlockAndNotifyAdequately(world, pos, state);
	}
}
