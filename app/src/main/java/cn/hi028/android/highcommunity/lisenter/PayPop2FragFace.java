package cn.hi028.android.highcommunity.lisenter;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.bean.Goods_info;

public interface PayPop2FragFace {
	public void backAllList(List<Goods_info> goodslist);

	public void setNumAndAmount(int num, double amount);

	public void goPay(ArrayList<Goods_info> data);
}
