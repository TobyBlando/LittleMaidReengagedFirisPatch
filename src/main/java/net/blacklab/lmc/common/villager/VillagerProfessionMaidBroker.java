package net.blacklab.lmc.common.villager;

import java.util.Random;

import net.blacklab.lmr.LittleMaidReengaged;
import net.blacklab.lmr.LittleMaidReengaged.LMItems;
import net.blacklab.lmr.config.LMRConfig;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

/**
 * メイドさん斡旋村人
 * @author firis-games
 *
 */
public class VillagerProfessionMaidBroker extends VillagerProfession {
	
	/**
	 * コンストラクタ
	 */
	public VillagerProfessionMaidBroker() {
		super(LittleMaidReengaged.MODID + ":maid_broker", 
				LittleMaidReengaged.MODID + ":textures/entity/villager_maid_broker.png", 
				"minecraft:textures/entity/zombie_villager/zombie_villager.png");
		this.init();
	}
	
	/**
	 * 初期化処理
	 */
	private void init() {
		
		VillagerCareerMaidBroker career = new VillagerCareerMaidBroker(this);
		
		//レベル1トレード
		//売サトウキビ
		career.addTrade(1, 
				new EntityVillager.ListItemForEmeralds(
						Items.REEDS,
						new EntityVillager.PriceInfo(12, 18)));
		//買取砂糖
		career.addTrade(1,
				new EntityVillager.EmeraldForItems(Items.SUGAR, 
						new EntityVillager.PriceInfo(20, 30)));
		
		//売ケーキ
		career.addTrade(2, 
				new EntityVillager.ListItemForEmeralds(
						Items.CAKE, 
						new EntityVillager.PriceInfo(2, 3)));
		
		//レベル2トレード
		//買取ケーキ
		career.addTrade(2,
				new EntityVillager.EmeraldForItems(Items.CAKE, 
						new EntityVillager.PriceInfo(1, 1)));
		
		int contractRateMin = 16;
		int contractRateMax = 32;
		if (LMRConfig.cfg_general_villager_trade_rate == 1) {
			contractRateMin = 4;
			contractRateMax = 12;
		}
		if (LMRConfig.cfg_general_villager_trade_rate == 3) {
			contractRateMin = 24;
			contractRateMax = 64;
		}
		
		//レベル3トレード
		//売契約書
		career.addTrade(3, 
				new ItemAndEmeraldsToItem(Items.CAKE, 
						new EntityVillager.PriceInfo(1, 1), 
						LMItems.MAID_CONTRACT, 
						new EntityVillager.PriceInfo(1, 1),
						new EntityVillager.PriceInfo(contractRateMin, contractRateMax)));
		
		//レベル4トレード
		//売メイドシュガー
		career.addTrade(4, 
				new EntityVillager.ListItemForEmeralds(
						LMItems.MAID_SUGAR, 
						new EntityVillager.PriceInfo(6, 12)));
		
		//売りメイドキャリー
		career.addTrade(4, 
				new EntityVillager.ListItemForEmeralds(
						LMItems.MAID_CARRY, 
						new EntityVillager.PriceInfo(6, 12)));
		
	}
	
	/**
	 * エメラルドの複数指定対応版
	 * @author firis-games
	 *
	 */
	private static class ItemAndEmeraldsToItem extends EntityVillager.ItemAndEmeraldToItem {
		private PriceInfo emeraldPrice;
		public ItemAndEmeraldsToItem(Item p_i45813_1_, PriceInfo p_i45813_2_, Item p_i45813_3_, PriceInfo p_i45813_4_, PriceInfo emeraldPrice) {
			super(p_i45813_1_, p_i45813_2_, p_i45813_3_, p_i45813_4_);
			this.emeraldPrice = emeraldPrice;
		}
		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
        {
            int i = this.buyingPriceInfo.getPrice(random);
            int j = this.sellingPriceInfo.getPrice(random);
            int e = this.emeraldPrice.getPrice(random);
            recipeList.add(new MerchantRecipe(
            		new ItemStack(this.buyingItemStack.getItem(), i, this.buyingItemStack.getMetadata()), 
            		new ItemStack(Items.EMERALD, e), 
            		new ItemStack(this.sellingItemstack.getItem(), j, this.sellingItemstack.getMetadata())));
        }
	}

}
