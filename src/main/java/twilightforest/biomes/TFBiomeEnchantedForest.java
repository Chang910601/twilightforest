package twilightforest.biomes;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFFeature;
import twilightforest.block.BlockTFPlant;
import twilightforest.block.TFBlocks;
import twilightforest.enums.PlantVariant;
import twilightforest.world.feature.TFGenLargeRainboak;
import twilightforest.world.feature.TFGenSmallRainboak;
import twilightforest.world.feature.TFGenTallGrass;
import twilightforest.world.feature.TFGenVines;
import twilightforest.world.TFWorld;

import java.util.Random;

public class TFBiomeEnchantedForest extends TFBiomeBase {

	private final Random colorRNG;

	public TFBiomeEnchantedForest(Builder props) {
		super(props);
		colorRNG = new Random();

		getTFBiomeDecorator().setGrassPerChunk(12);
		getTFBiomeDecorator().setFlowersPerChunk(8);
	}

	@Override
	public int getGrassColor(BlockPos pos) {
		return (super.getGrassColor(pos) & 0xFFFF00) + getEnchantedColor(pos.getX(), pos.getZ());
	}

	@Override
	public int getFoliageColor(BlockPos pos) {
		return (super.getFoliageColor(pos) & 0xFFFF00) + getEnchantedColor(pos.getX(), pos.getZ());
	}

	/**
	 * Find a number between 0 and 255.  currently returns concentric circles from the biome center
	 */
	private int getEnchantedColor(int x, int z) {
		// center of the biome is at % 256 - 8
		int cx = 256 * Math.round((x - 8) / 256F) + 8;
		int cz = 256 * Math.round((z - 8) / 256F) + 8;

		int dist = (int) MathHelper.sqrt((cx - x) * (cx - x) + (cz - z) * (cz - z));
		int color = dist * 64;
		color %= 512;

		if (color > 255) {
			color = 511 - color;
		}

		color = 255 - color;

		int randomFlicker = colorRNG.nextInt(32) - 16;

		if (0 < color + randomFlicker && color + randomFlicker > 255) {
			color += randomFlicker;
		}

		return color;
	}

	//TODO: Move to feature decorator
	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random random) {
		if (random.nextInt(15) == 0) {
			return new TFGenSmallRainboak();
		} else if (random.nextInt(50) == 0) {
			return new TFGenLargeRainboak();
		} else if (random.nextInt(5) == 0) {
			return birchGen;
		} else if (random.nextInt(10) == 0) {
			return new WorldGenBigTree(false);
		} else {
			return TREE_FEATURE;
		}
	}

    //TODO: Move to feature decorator
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random random) {
		if (random.nextInt(3) > 0) {
			return new WorldGenTallGrass(BlockTallGrass.EnumType.FERN);
		} else if (random.nextInt(3) == 0) {
			return new TFGenTallGrass(TFBlocks.twilight_plant.getDefaultState().with(BlockTFPlant.VARIANT, PlantVariant.FIDDLEHEAD));
		} else {
			return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
		}
	}

    //TODO: Move to feature decorator
	@Override
	public void decorate(World world, Random rand, BlockPos pos) {

		BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos();

		WorldGenerator vines = new TFGenVines();
		for (int i = 0; i < 20; i++) {
			int rx = pos.getX() + rand.nextInt(16) + 8;
			int ry = TFWorld.SEALEVEL + 128;
			int rz = pos.getZ() + rand.nextInt(16) + 8;
			vines.generate(world, rand, mutPos.setPos(rx, ry, rz));
		}

		// tall ferns
		DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.FERN);
		for (int i = 0; i < 20; ++i) {
			int rx = pos.getX() + rand.nextInt(16) + 8;
			int rz = pos.getZ() + rand.nextInt(16) + 8;
			int ry = rand.nextInt(world.getHeight(rx, rz) + 32);
			DOUBLE_PLANT_GENERATOR.generate(world, rand, mutPos.setPos(rx, ry, rz));
		}

		super.decorate(world, rand, pos);
	}

    //TODO: Move to feature decorator
	//VanillaCopy from BiomeForest.pickRandomFlower, removed conditional
	@Override
	public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
		double d0 = MathHelper.clamp((1.0D + GRASS_COLOR_NOISE.getValue((double) pos.getX() / 48.0D, (double) pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
		BlockFlower.EnumFlowerType blockflower$enumflowertype = BlockFlower.EnumFlowerType.values()[(int) (d0 * (double) BlockFlower.EnumFlowerType.values().length)];
		return blockflower$enumflowertype == BlockFlower.EnumFlowerType.BLUE_ORCHID ? BlockFlower.EnumFlowerType.POPPY : blockflower$enumflowertype;
	}

    //TODO: Move to feature decorator
	@Override
	public void addDefaultFlowers() {
		for (BlockFlower.EnumFlowerType flowerType : Blocks.YELLOW_FLOWER.getTypeProperty().getAllowedValues()) {
			addFlower(Blocks.YELLOW_FLOWER.getDefaultState().with(Blocks.YELLOW_FLOWER.getTypeProperty(), flowerType), 10);
		}
		for (BlockFlower.EnumFlowerType flowerType : Blocks.RED_FLOWER.getTypeProperty().getAllowedValues()) {
			addFlower(Blocks.RED_FLOWER.getDefaultState().with(Blocks.RED_FLOWER.getTypeProperty(), flowerType), 10);
		}
	}

	@Override
	protected TFFeature getContainedFeature() {
		return TFFeature.QUEST_GROVE;
	}
}
