package twilightforest.world.feature;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.BlockTFMagicLog;
import twilightforest.block.TFBlocks;
import twilightforest.enums.MagicWoodVariant;
import twilightforest.world.TFWorld;

import java.util.Random;

public class TFGenSortingTree extends TFGenerator {

	protected BlockState treeState = TFBlocks.magic_log.getDefaultState().with(BlockTFMagicLog.VARIANT, MagicWoodVariant.SORT);
	protected BlockState branchState = treeState.with(BlockLog.LOG_AXIS, BlockLog.EnumAxis.NONE);
	protected BlockState leafState = TFBlocks.magic_leaves.getDefaultState().with(BlockTFMagicLog.VARIANT, MagicWoodVariant.SORT).with(BlockLeaves.CHECK_DECAY, false);
	protected BlockState rootState = TFBlocks.root.getDefaultState();

	public TFGenSortingTree() {
		this(false);
	}

	public TFGenSortingTree(boolean notify) {
		super(notify);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		// check soil
		Material materialUnder = world.getBlockState(pos.down()).getMaterial();
		if ((materialUnder != Material.GRASS && materialUnder != Material.GROUND) || pos.getY() >= TFWorld.MAXHEIGHT - 12) {
			return false;
		}

		// 3 block high trunk
		for (int dy = 0; dy < 4; dy++) {
			setBlockAndNotifyAdequately(world, pos.up(dy), treeState);
		}

		// leaves
		putLeaves(world, pos.up(2), false);
		putLeaves(world, pos.up(3), false);

		// sorting engine
		setBlockAndNotifyAdequately(world, pos.up(), TFBlocks.magic_log_core.getDefaultState().with(BlockTFMagicLog.VARIANT, MagicWoodVariant.SORT));

		return true;
	}

	private void putLeaves(World world, BlockPos pos, boolean bushy) {
		for (int lx = -1; lx <= 1; lx++) {
			for (int ly = -1; ly <= 1; ly++) {
				for (int lz = -1; lz <= 1; lz++) {
					if (!bushy && Math.abs(ly) > 0 && (Math.abs(lx) + Math.abs(lz)) > 1) {
						continue;
					}
					putLeafBlock(this, world, pos.add(lx, ly, lz), leafState);
				}
			}
		}
	}

}
