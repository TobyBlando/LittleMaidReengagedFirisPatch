package net.blacklab.lmr.entity.maidmodel.lmmodel;

import net.blacklab.lmr.entity.maidmodel.base.ModelLittleMaidBase;

public class ModelLittleMaid_Orign extends ModelLittleMaidBase {

	/**
	 * コンストラクタは全て継承させること
	 */
	public ModelLittleMaid_Orign() {
		super();
	}
	/**
	 * コンストラクタは全て継承させること
	 */
	public ModelLittleMaid_Orign(float psize) {
		super(psize);
	}
	/**
	 * コンストラクタは全て継承させること
	 */
	public ModelLittleMaid_Orign(float psize, float pyoffset, int pTextureWidth, int pTextureHeight) {
		super(psize, pyoffset, pTextureWidth, pTextureHeight);
	}


	@Override
	public String getUsingTexture() {
		return "default";
	}

}
