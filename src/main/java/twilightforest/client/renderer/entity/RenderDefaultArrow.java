package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.ResourceLocation;

public class RenderDefaultArrow<T extends ArrowEntity> extends ArrowRenderer<T> {
	public static final ResourceLocation RES_ARROW = new ResourceLocation("textures/entity/projectiles/arrow.png");

	public RenderDefaultArrow(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return RES_ARROW;
	}
}
