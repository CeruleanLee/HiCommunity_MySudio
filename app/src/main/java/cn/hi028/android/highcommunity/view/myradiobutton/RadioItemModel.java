package cn.hi028.android.highcommunity.view.myradiobutton;

import android.content.res.ColorStateList;
/**
 * Created by lee.
 * function: 单选矩阵的适配器
 * purpose: 系统的RadioGroup是个耿直的LinearLayout╮(╯▽╰)╭  只能线性排列，无法完成
 * N行N列的布局。So 为了实现单选按钮的网格布局 ,重定义
 */
public class RadioItemModel {


    public String text; //RadioButton的文字
    public boolean isChecked;//是否选中
    public boolean hiddenRadio;//是否需要隐藏radio
    public ColorStateList textColor;//文字的不同状态的颜色

	public RadioItemModel(String text, boolean isChecked, boolean hiddenRadio, ColorStateList textColor) {
		this.text = text;
		this.isChecked = isChecked;
		this.hiddenRadio = hiddenRadio;
		this.textColor = textColor;
	}

	public RadioItemModel(String text) {
		this.isChecked = false;
		this.hiddenRadio = false;
		this.textColor = null;
		this.text = text;
	}




	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public boolean isChecked() {
		return isChecked;
	}

}