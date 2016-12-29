package net.duohuo.dhroid.activity;

public interface BackHandledInterface {

	public abstract void setSelectedFragment(BackHandledFragment selectedFragment);
	public abstract  void onResultActivity(int id);
	public   void onResultActivity_Address(String address);
}
