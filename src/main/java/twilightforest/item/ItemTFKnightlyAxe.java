package twilightforest.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import twilightforest.TwilightForestMod;

import com.google.common.collect.Multimap;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFKnightlyAxe extends ItemAxe {

	private static final int BONUS_DAMAGE = 2;
	private EntityPlayer bonusDamagePlayer;
	private Entity bonusDamageEntity;
	private float damageVsEntity;

	protected ItemTFKnightlyAxe(Item.ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.setCreativeTab(TFItems.creativeTab);
		this.damageVsEntity = 4 + par2EnumToolMaterial.getDamageVsEntity();

	}

    @Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return EnumRarity.RARE;
	}
    
    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with knightmetal ingots
        return par2ItemStack.getItem() == TFItems.knightMetal ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
    
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) 
    {
    	// extra damage to unarmored target
    	if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getTotalArmorValue() == 0)
    	{
    		this.bonusDamageEntity = entity;
    		this.bonusDamagePlayer = player;
    	}
    	
        return false;
    }
    
//    /**
//     * Returns the damage against a given entity.
//     */
//    @Override
//    public float getDamageVsEntity(Entity par1Entity, ItemStack itemStack)
//    {
//       	if (this.bonusDamagePlayer != null && this.bonusDamageEntity != null && par1Entity == this.bonusDamageEntity)
//       	{
//       		this.bonusDamagePlayer.onEnchantmentCritical(par1Entity);
//       		this.bonusDamagePlayer = null;
//       		this.bonusDamageEntity = null;
//       		return this.damageVsEntity + BONUS_DAMAGE;
//       	}
//       	else
//       	{
//       		return super.getDamageVsEntity(par1Entity, itemStack);
//       	}
//    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add(I18n.translateToLocal(getUnlocalizedName() + ".tooltip"));
	}
	
	@Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        // remove old damage value
        multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName());
        // add new one
        multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double)this.damageVsEntity, 0));
        return multimap;
    }
}