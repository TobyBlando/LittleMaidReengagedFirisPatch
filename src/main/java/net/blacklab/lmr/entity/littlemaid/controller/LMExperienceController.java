package net.blacklab.lmr.entity.littlemaid.controller;

import java.util.UUID;

import net.blacklab.lib.vevent.VEventBus;
import net.blacklab.lmr.LittleMaidReengaged;
import net.blacklab.lmr.api.event.EventLMRE;
import net.blacklab.lmr.entity.littlemaid.EntityLittleMaid;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;

/**
 * メイドさんの経験値管理クラス
 * @author firis-games
 *
 */
public class LMExperienceController {

	protected EntityLittleMaid theMaid;

	private static final String uuidString = "NX_EXP_HP_BOOSTER";
	public static final UUID NX_EXP_HP_BOOSTER = UUID.nameUUIDFromBytes(uuidString.getBytes());

//	private boolean isWaitRevive = false;
//	// 死亡までの猶予時間
//	private int deathCount = 0;
//	// 復帰までに最低限必要になる時間
//	private int pauseCount = 0;
//	private int requiredSugarToRevive = 0;
//	private DamageSource deadCause = DamageSource.GENERIC;
	
	/**
	 * 取得経験値倍率
	 */
	private int gainExpBoost = 1;
	
	/**
	 * メイド経験値
	 */
	private float maidExperience = 0;
	
	/**
	 * コンストラクタ
	 * @param maid
	 */
	public LMExperienceController(EntityLittleMaid maid) {
		theMaid = maid;
	}

	public void onLevelUp(int level) {
		/*
		 * 最大HP上昇
		 */
		double modifyamount = 0;
		double prevamount = 0;
		if (level > 30) {
			modifyamount += (Math.min(level, 50)-30)/2;
		}
		if (level > 50) {
			modifyamount += MathHelper.floor((Math.min(level, 75)-50)/2.5f);
		}
		if (level > 75) {
			modifyamount += MathHelper.floor((Math.min(level, 150)-75)/7.5f);
		}
		if (level > 150) {
			modifyamount += (Math.min(level, 300)-150)/15;
		}
		IAttributeInstance maxHPattr = theMaid.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		AttributeModifier existedMod = maxHPattr.getModifier(NX_EXP_HP_BOOSTER);
		if (existedMod != null) {
			prevamount = existedMod.getAmount();
		}
		if (modifyamount != 0 || prevamount < modifyamount) {
			// Modifier書き換え
			float prevHP = theMaid.getHealth();
			AttributeModifier maxHPmod = new AttributeModifier(NX_EXP_HP_BOOSTER, uuidString, modifyamount, 0);
			if (existedMod != null) {
				maxHPattr.removeModifier(existedMod);
			}
			maxHPattr.applyModifier(maxHPmod);
			// たぶんremoveModifierした時に20を超える体力が削られちゃうので，再設定．
			theMaid.setHealth(prevHP);
		}
	}

//	public boolean onDeath(DamageSource cause) {
//		LittleMaidReengaged.Debug("HOOK CATCH");
//		if (this.getMaidLevel() >= 20 && !cause.getDamageType().equals("outOfWorld") && !cause.getDamageType().equals("lmmnx_timeover") && !isWaitRevive) {
//			// 復帰に必要な砂糖はレベルが低いほど大きく，猶予は少なく
//			LittleMaidReengaged.Debug("DISABLING Remote=%s", theMaid.getEntityWorld().isRemote);
//			theMaid.playSound("dig.glass");
//			deathCount = (int) Math.min(1200, 200 + Math.pow(this.getMaidLevel()-20, 1.8));
//			pauseCount = (int) Math.max(100, 600 - (this.getMaidLevel()-20)*6.5);
//			requiredSugarToRevive = Math.max(32, 64 - (int)((this.getMaidLevel()-20)/100f*32f));
//			deadCause = cause;
//			isWaitRevive = true;
//			LittleMaidReengaged.Debug("TURN ON COUNT=%d/%d", deathCount, pauseCount);
//			return true;
//		}
//		return false;
//	}

	public void onUpdate() {
		
//		if (!theMaid.getEntityWorld().isRemote && isWaitRevive) {
//			LittleMaidReengaged.Debug("COUNT %d/%d", deathCount, pauseCount);
//			// 死亡判定
//			if (--deathCount <= 0 && !theMaid.isDead) {
//				isWaitRevive = false;
//				LittleMaidReengaged.Debug("TIMEOVER.");
//				theMaid.attackEntityFrom(
//						new DamageSource("lmmnx_timeover").setDamageBypassesArmor().setDamageAllowedInCreativeMode().setDamageIsAbsolute(),
//						Float.MAX_VALUE);
//				theMaid.playSound("mob.ghast.death");
//				theMaid.playSound("dig.glass");
//				if (theMaid.getMaidMasterEntity() != null) {
//					theMaid.getMaidMasterEntity().sendMessage(new TextComponentTranslation("littleMaidMob.chat.text.timedeath", CommonHelper.getDeadSource(deadCause)));
//				}
//			}
//
//			// 行動不能
//			if ((--pauseCount > 0 || deathCount > 0) && (theMaid.isMaidWait() || !theMaid.getMaidModeString().equals(EntityMode_DeathWait.mmode_DeathWait))) {
//				theMaid.setMaidWait(false);
//				theMaid.setMaidMode(EntityMode_DeathWait.mmode_DeathWait);
//			}
//			if (theMaid.onGround && deathCount%20 == 0 && !theMaid.isSneaking()) {
//				theMaid.setSneaking(true);
//			}
//
//			// 砂糖を持っているか？
//			int sugarCount = 0;
//			for (int i=0; i<18 && sugarCount < requiredSugarToRevive; i++) {
//				ItemStack stack = theMaid.maidInventory.mainInventory.get(i);
//				if (!stack.isEmpty() && ItemHelper.isSugar(stack)) {
//					stack.setCount(sugarCount + stack.getCount());
//				}
//			}
//			// 砂糖が規定数以上ある場合は死亡猶予
//			if (sugarCount >= requiredSugarToRevive) {
//				deathCount++;
//				// 復帰
//				if (deathCount > 0 && pauseCount <= 0) {
//					theMaid.setHealth(Math.min(8f + Math.min(24f, MathHelper.floor((this.getMaidLevel()-20)/100f*24f)), theMaid.getMaxHealth()));
//					theMaid.eatSugar(false,false,false);
//					for(int i=0; i<18 && requiredSugarToRevive > 0; i++) {
//						ItemStack stack = theMaid.maidInventory.mainInventory.get(i);
//						if (!stack.isEmpty() && ItemHelper.isSugar(stack)) {
//							int consumesize = Math.min(stack.getCount(), requiredSugarToRevive);
//							stack.setCount(stack.getCount() - consumesize);
//							if (stack.getCount() <= 0) {
//								stack = ItemStack.EMPTY;
//							}
//							requiredSugarToRevive -= consumesize;
//						}
//					}
//					theMaid.setMaidWait(false);
//					theMaid.setMaidMode(EntityMode_Basic.mmode_Escort);
//					isWaitRevive = false;
//				}
//			}
//		}
	}

//	public boolean onDeathUpdate() {
//		if (isWaitRevive && deathCount > 0) {
//			return true;
//		} else if (!theMaid.getEntityWorld().isRemote && theMaid.getHealth() <= 0f) {
//			theMaid.syncNet(LMRMessage.EnumPacketMode.CLIENT_ONDEATH, null);
//		}
//		return false;
//	}

	public void readEntityFromNBT(NBTTagCompound tagCompound) {
		
		//経験値
		maidExperience = tagCompound.getFloat(LittleMaidReengaged.MODID + ":MAID_EXP");
		this.setExpBooster(tagCompound.getInteger(LittleMaidReengaged.MODID + ":EXP_BOOST"));

//		isWaitRevive = tagCompound.getBoolean(LittleMaidReengaged.MODID + ":EXP_HANDLER_DEATH_WAIT");
//		deathCount = tagCompound.getInteger(LittleMaidReengaged.MODID + ":EXP_HANDLER_DEATH_DCNT");
//		pauseCount = tagCompound.getInteger(LittleMaidReengaged.MODID + ":EXP_HANDLER_DEATH_PCNT");
//		requiredSugarToRevive = tagCompound.getInteger(LittleMaidReengaged.MODID + ":EXP_HANDLER_DEATH_REQ");
//		String causeType = tagCompound.getString(LittleMaidReengaged.MODID + ":EXP_HANDLER_DEATH_CAUSE_T");
//		if (causeType != null && !causeType.isEmpty()) {
//			NBTTagCompound causeEntityTag = tagCompound.getCompoundTag(LittleMaidReengaged.MODID + ":HADNLER_DEATH_CAUSE_E");
//			if (causeEntityTag != null && causeEntityTag.getSize() != 0) {
//				Entity entity = EntityList.createEntityFromNBT(causeEntityTag, theMaid.getEntityWorld());
//				if (entity != null) {
//					deadCause = new EntityDamageSource(causeType, entity);
//				} else {
//					deadCause = new DamageSource(causeType);
//				}
//			} else {
//				deadCause = new DamageSource(causeType);
//			}
//		}
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		
		//経験値
		tagCompound.setFloat(LittleMaidReengaged.MODID + ":MAID_EXP", maidExperience);
		tagCompound.setInteger(LittleMaidReengaged.MODID + ":EXP_BOOST", gainExpBoost);
		
//		tagCompound.setBoolean(LittleMaidReengaged.MODID + ":EXP_HANDLER_DEATH_WAIT", isWaitRevive);
//		tagCompound.setInteger(LittleMaidReengaged.MODID + ":EXP_HANDLER_DEATH_DCNT", deathCount);
//		tagCompound.setInteger(LittleMaidReengaged.MODID + ":EXP_HANDLER_DEATH_PCNT", pauseCount);
//		tagCompound.setInteger(LittleMaidReengaged.MODID + ":EXP_HANDLER_DEATH_REQ", requiredSugarToRevive);
//		tagCompound.setString(LittleMaidReengaged.MODID + ":EXP_HANDLER_DEATH_CAUSE_T", deadCause.getDamageType());
//		if (deadCause.getTrueSource() != null) {
//			NBTTagCompound causeEntityTag = new NBTTagCompound();
//			deadCause.getTrueSource().writeToNBT(causeEntityTag);
//			tagCompound.setTag(LittleMaidReengaged.MODID + ":EXP_HANDLER_DEATH_CAUSE_E", causeEntityTag);
//		}
	}
	
	
	/**
	 *  レベルを取得
	 * @return
	 */
	public int getMaidLevel() {
		return ExperienceUtil.getLevelFromExp(maidExperience);
	}
	
	/**
	 *  現在経験値を取得
	 * @return
	 */
	public float getMaidExperience() {
		if (maidExperience <= 0) {
			return 0;
		}
		return maidExperience;
	}
	
	/**
	 * 現在経験値設定
	 * @param level
	 */
	public void setMaidExperience(float exp) {
		if (exp <= 0) {
			this.maidExperience = 0;
		}
		this.maidExperience = exp;
	}
	
	/**
	 * 経験値ブーストを取得
	 */
	public int getExpBooster() {
		return gainExpBoost;
	}

	/**
	 * 経験値ブーストを設定
	 * @param v ブースト値(0以上)
	 */
	public void setExpBooster(int v) {
		if (v < 1) {
			v = 1;
		}
		if (v > ExperienceUtil.getBoosterLimit(getMaidLevel())) {
			v = ExperienceUtil.getBoosterLimit(getMaidLevel());
		}
		gainExpBoost = v;
	}
	
	/**
	 * 経験値を追加
	 * @param value
	 */
	public void addMaidExperience(float value) {
		
		//サーバー側のみ
		if (!this.theMaid.isContractEX() || this.theMaid.getEntityWorld().isRemote) {
			return;
		}

		int currentLevel = getMaidLevel();
		if (maidExperience > 0) {
			value *= getExpBooster();
		}
		maidExperience += value;

		// レベルが下がってしまう場合はそれ以上引かない
		if (maidExperience < ExperienceUtil.getRequiredExpToLevel(currentLevel)) {
			maidExperience = ExperienceUtil.getRequiredExpToLevel(currentLevel);
		}

		// 最大レベル
		if (maidExperience > ExperienceUtil.getRequiredExpToLevel(ExperienceUtil.EXP_FUNCTION_MAX)) {
			maidExperience = ExperienceUtil.getRequiredExpToLevel(ExperienceUtil.EXP_FUNCTION_MAX);
		}

		boolean flag = false;
		for (;maidExperience >= ExperienceUtil.getRequiredExpToLevel(currentLevel+1); currentLevel++) {
			// 一度に複数レベルアップした場合にその分だけ呼ぶ
			if (!flag) {
				this.theMaid.playSound("random.levelup");
				flag = true;
			}
			this.onLevelUp(currentLevel + 1);
			VEventBus.instance.post(new EventLMRE.MaidLevelUpEvent(this.theMaid, getMaidLevel()));
		}
	}
	
}
