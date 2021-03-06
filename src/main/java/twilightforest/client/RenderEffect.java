package twilightforest.client;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import org.lwjgl.opengl.GL11;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.shield.IShieldCapability;
import twilightforest.client.renderer.entity.LayerShields;
import twilightforest.entity.boss.EntityTFLich;
import twilightforest.potions.PotionFrosted;

import java.util.Random;

public enum RenderEffect {

	ICE {

		private final Random random = new Random();

		@Override
		public boolean shouldRender(LivingEntity entity, boolean firstPerson) {
			return !firstPerson && entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(PotionFrosted.MODIFIER_UUID) != null;
		}

		@Override
		public void render(LivingEntity entity, EntityModel<? extends LivingEntity> renderer,
		                   double x, double y, double z, float partialTicks, boolean firstPerson) {

			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			Minecraft.getInstance().textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

			random.setSeed(entity.getEntityId() * entity.getEntityId() * 3121 + entity.getEntityId() * 45238971);

			// number of cubes
			int numCubes = (int) (entity.getHeight() / 0.4F);

			// make cubes
			for (int i = 0; i < numCubes; i++) {
				GlStateManager.pushMatrix();
				float dx = (float) (x + random.nextGaussian() * 0.2F * entity.getWidth());
				float dy = (float) (y + random.nextGaussian() * 0.2F * entity.getHeight()) + entity.getHeight() / 2F;
				float dz = (float) (z + random.nextGaussian() * 0.2F * entity.getWidth());
				GlStateManager.translatef(dx, dy, dz);
				GlStateManager.scalef(0.5F, 0.5F, 0.5F);
				GlStateManager.rotatef(random.nextFloat() * 360F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotatef(random.nextFloat() * 360F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotatef(random.nextFloat() * 360F, 0.0F, 0.0F, 1.0F);

				Minecraft.getInstance().getBlockRendererDispatcher().renderBlockBrightness(Blocks.ICE.getDefaultState(), 1);
				GlStateManager.popMatrix();
			}

			GlStateManager.disableBlend();
		}

	}, SHIELDS {
		private final LayerRenderer<LivingEntity, EntityModel<LivingEntity>> layer = new LayerShields<>();

		@Override
		public boolean shouldRender(LivingEntity entity, boolean firstPerson) {
			if (entity instanceof EntityTFLich) return false;
			entity.getCapability(CapabilityList.SHIELDS).map(c -> c.shieldsLeft() > 0).orElse(false);
		}

		@Override
		public void render(LivingEntity entity, EntityModel<? extends LivingEntity> renderer,
		                   double x, double y, double z, float partialTicks, boolean firstPerson) {

			GlStateManager.pushMatrix();
			GlStateManager.translated(x, y, z);
			GlStateManager.rotatef(180, 1, 0, 0);
			GlStateManager.translatef(0, 0.5F - entity.getEyeHeight(), 0);
			GlStateManager.enableBlend();
			GlStateManager.disableCull();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			layer.render(entity, 0, 0, partialTicks, 0, 0, 0, 0.0625F);
			GlStateManager.enableCull();
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	};

	static final RenderEffect[] VALUES = values();

	public boolean shouldRender(LivingEntity entity, boolean firstPerson) {
		return false;
	}

	public void render(LivingEntity entity, EntityModel<? extends LivingEntity> renderer,
	                   double x, double y, double z, float partialTicks, boolean firstPerson) {

	}
}
