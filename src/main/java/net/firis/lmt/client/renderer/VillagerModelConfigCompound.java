package net.firis.lmt.client.renderer;

import net.blacklab.lmr.entity.maidmodel.IModelConfigCompound;
import net.blacklab.lmr.entity.maidmodel.base.ModelMultiBase;
import net.blacklab.lmr.util.IModelCapsData;
import net.blacklab.lmr.util.manager.LMTextureBoxManager;
import net.blacklab.lmr.util.manager.pack.LMTextureBox;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

public class VillagerModelConfigCompound implements IModelConfigCompound {

	private LMTextureBox textureBox = null;
	private IModelCapsData modelCaps = null;
	
	public VillagerModelConfigCompound(EntityLivingBase entity) {
		textureBox = LMTextureBoxManager.instance.getDefaultLMTextureBox();
		modelCaps = new VillagerModelCapsData(entity);
	}
	
	@Override
	public IModelCapsData getModelCaps() {
		return modelCaps;
	}

	@Override
	public ResourceLocation getTextureLittleMaid() {
		return textureBox.getTextureLittleMaidDefault();
	}

	@Override
	public ResourceLocation getLightTextureLittleMaid() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ResourceLocation getTextureInnerArmor(EntityEquipmentSlot slot) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ResourceLocation getLightTextureInnerArmor(EntityEquipmentSlot slot) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ResourceLocation getTextureOuterArmor(EntityEquipmentSlot slot) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ResourceLocation getLightTextureOuterArmor(EntityEquipmentSlot slot) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ModelMultiBase getModelLittleMaid() {
		return textureBox.getModelLittleMaid();
	}

	@Override
	public ModelMultiBase getModelInnerArmor(EntityEquipmentSlot slot) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ModelMultiBase getModelOuterArmor(EntityEquipmentSlot slot) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean isArmorTypeVisible(int type) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean isInvisible() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public int getBrightnessForRender() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

}
