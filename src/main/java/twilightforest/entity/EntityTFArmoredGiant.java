package twilightforest.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;

public class EntityTFArmoredGiant extends EntityTFGiantMiner {

	public static final ResourceLocation LOOT_TABLE = TwilightForestMod.prefix("entities/armored_giant");

	public EntityTFArmoredGiant(EntityType<? extends EntityTFArmoredGiant> type, World world) {
		super(type, world);
	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		super.setEquipmentBasedOnDifficulty(difficulty);
		this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.STONE_SWORD));
		this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.IRON_HELMET));
		this.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
		this.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(Items.IRON_LEGGINGS));
		this.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(Items.IRON_BOOTS));
	}

	@Override
	public ResourceLocation getLootTable() {
		return LOOT_TABLE;
	}
}
