package cn.hi028.android.highcommunity.bean;

import java.util.ArrayList;
import java.util.List;

public class Shop implements Comparable<Double>{
	public double shop_id;
	public String shop_name;
	public double distance; 
	public double longitude;
	public double latitude;
	public double deal_num;	
	public String shop_url;
	public String shop_murl;	  
	public List<Deal> deals;
	@Override
	public int compareTo(Double another) {
		// TODO Auto-generated method stub
		return 0;
	} 
}
