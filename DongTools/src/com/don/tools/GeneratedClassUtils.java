package com.don.tools;

/**
 * 
 * @author linxd
 * @category 解决xxxx_跨包找不到问题
 */
public class GeneratedClassUtils {
	//Activity
	@SuppressWarnings("rawtypes")
	public static Class get(Class clazz) {
		if (clazz == null) {
			return null;
		}
		else if (clazz.getCanonicalName().endsWith("_")) {
			return clazz;
		}
		String name = clazz.getCanonicalName() + "_";
		try {
			Class result = Class.forName(name);
			return result;
		} catch (ClassNotFoundException e) {
			new RuntimeException("Cannot find class for" + name, e);
		}
		return null;
	}
    //fragment(不混淆时使用,用包名.*来避免)
	@SuppressWarnings("rawtypes")
	public static Object newInstance(Class clazz) {
		Object result = null;
		try {
			Class mclass=get(clazz);
			result = mclass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			Debug.verbose("GeneratedClassUtils 生成类出错: " + e.toString());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			Debug.verbose("GeneratedClassUtils 生成类出错: " + e.toString());
		}
		return result;
	}

}
