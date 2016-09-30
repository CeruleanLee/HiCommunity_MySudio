package cn.hi028.android.highcommunity.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

public class SpannableStringBuild {

	public static SpannableString build(String source,int color, Object text){
		SpannableString spannableString = new SpannableString(source);
		ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
		int start = source.toString().indexOf(text.toString());
		spannableString.setSpan(colorSpan, start, start+text.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

}
