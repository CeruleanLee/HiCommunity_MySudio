package net.duohuo.dhroid.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

/**
 * 日志工具�? 该类用于控制logcat控制台输出�?�一般方式文件输出开关， 以及定制独立文件输出
 * 
 * <pre>
 * 独立的日志文件的组织方式�?
 * 		此时 FILE_DIR = "/log/backup_restore/"  FILE_NAME = "backup_restore" FILE_SUFFIX = ".log"
 * 
 *  	/log/backup_restore/backup_restore_error.log                    (用于记录ERROR以上级别日志)  
 *  	/log/backup_restore/2012-08-26/backup_restore_2012-08-26.log    (用于记录全级别日�?)  
 *  	/log/backup_restore/2012-08-28/backup_restore_2012-08-28.log     (用于记录全级别日�?)
 * </pre>
 * 
 * @author Administrator
 */
public class LogUtil {
	/**
	 * �?关用于控制是否启用Log（包括logcat控制台输出�?�一般方式文件输出）
	 */
	private final static boolean logEnable = true;
	/**
	 * �?关用于控制是否启用Log（包括独立文件输出），需依赖logEnable置为true，此项配置才能起作用
	 */
	private final static boolean logToFileEnable = false;
	/**
	 * 是否�?要详细的log信息（true，输出额外信息例如：(Error 08-28 13:56:02.724)[Thread-main: AutoBackupActivity.java:319
	 * onDestroy()]，false 仅输出相关传入字符串信息�?
	 */
	private final static boolean extInfoEnable = true;
	/**
	 * 应用默认TAG
	 */
	private final static String APP_TAG = "";
	/**
	 * 日志启用级别，闭区间，大于等于该级别的日志会输出
	 */
	private final static int logLevel = Log.VERBOSE;
	/**
	 * 应用日志文件存放目录
	 */
	private final static String FILE_DIR = "/log/DianYuan/";
	/**
	 * 应用日志文件名称
	 */
	private final static String FILE_NAME = "DianYuan";
	/**
	 * 应用日志文件后缀
	 */
	private final static String FILE_SUFFIX = ".log";
	/**
	 * 应用日志ERROR文件名称
	 */
	private final static String ERROR_FILE_NAME = FILE_NAME + "_error";
	/**
	 * 该格式用于规范应用日志文件名称中的时间信�?
	 */
	private final static String FILE_DATE_FORMAT = "yyyy-MM-dd";
	/**
	 * 该格式用于规范应用日志文件内具体日志条目时间信息
	 */
	private final static String LOG_TIME_FORMAT = "MM-dd HH:mm:ss.SSS";

	public static void i(Object obj) {
		i(APP_TAG, obj);
	}

	public static void i(String tag, Object obj) {
		if (logEnable && logLevel <= Log.INFO) {
			log(APP_TAG + "_" + tag, obj, Log.INFO);
		}
	}

	public static void d(Object obj) {
		d(APP_TAG, obj);
	}

	public static void d(String tag, Object obj) {
		if (logEnable && logLevel <= Log.DEBUG) {
			log(APP_TAG + "_" + tag, obj, Log.DEBUG);
		}
	}

	public static void v(Object obj) {
		v(APP_TAG, obj);
	}

	public static void v(String tag, Object obj) {
		if (logEnable && logLevel <= Log.VERBOSE) {
			log(APP_TAG + "_" + tag, obj, Log.VERBOSE);
		}
	}

	public static void w(Object obj) {
		w(APP_TAG, obj);
	}

	public static void w(String tag, Object obj) {
		if (logEnable && logLevel <= Log.WARN) {
			log(APP_TAG + "_" + tag, obj, Log.WARN);
		}
	}

	public static void e(Object obj) {
		// FIXME:方法重载�?改进
		if (obj instanceof Throwable) {
			e((Throwable) obj);
			return;
		}
		e(APP_TAG, obj);
	}

	public static void e(String tag, Object obj) {
		if (logEnable && logLevel <= Log.ERROR) {
			log(APP_TAG + "_" + tag, obj, Log.ERROR);
		}
	}

	public static void e(Throwable ex) {
		if (logEnable && logLevel <= Log.ERROR) {
			log(APP_TAG, "\r\n" + Log.getStackTraceString(ex), Log.ERROR);
		}
	}

	private static void log(String tag, Object obj, int level) {
		try {
			String messge = buildMessge(obj, level);
			switch (level) {
				case Log.VERBOSE:
					Log.v(tag, messge);
					break;
				case Log.DEBUG:
					Log.d(tag, messge);
					break;
				case Log.INFO:
					Log.i(tag, messge);
					break;
				case Log.WARN:
					Log.w(tag, messge);
					break;
				case Log.ERROR:
					Log.e(tag, messge);
					break;
				default:
					Log.e(tag, "不支持的Log级别" + messge);
			}
			// 判断独立文件输出是否�?�?
			if (logToFileEnable) {
				// 全级别存储到�?般的日志文件
				saveToSDCard(messge, LogFileType.Ordinary);
				if (level >= Log.ERROR) {
					// Error以上级别日志存储到Error日志文件
					saveToSDCard(messge, LogFileType.ErrorFile);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构�?�日志信�?
	 */
	private static String buildMessge(Object obj, int level) {
		if (obj == null) {
			obj = "";
		}
		String retString = "";
		// 是否�?要显示详细信�?
		if (extInfoEnable) {
			String functionInfo = getMethodInfo();
			if (functionInfo == null) {
				functionInfo = "";
			}
			// 构�?�详细信息（日志级别、时间�?�方法及线程信息�?
			retString = "(" + getLevelInfo(level) + " " + getTimestamp() + ")" + functionInfo + "\r\n";
		}
		return retString + obj;
	}

	/**
	 * 从堆栈中取得必要方法信息�?
	 */
	private static String getMethodInfo() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}
			if (st.getClassName().equals(LogUtil.class.getName())) {
				continue;
			}
			return "[Thread-" + Thread.currentThread().getName() + ": " + st.getFileName() + ":"
					+ st.getLineNumber() + " " + st.getMethodName() + "()]";
		}
		return null;
	}

	/**
	 * 取得日志级别对应的显示字�?
	 */
	private static String getLevelInfo(int level) {
		switch (level) {
			case Log.VERBOSE:
				return "V";
			case Log.DEBUG:
				return "D";
			case Log.INFO:
				return "I";
			case Log.WARN:
				return "W";
			case Log.ERROR:
				return "E";
			default:
				return "不支持的Log级别";
		}
	}

	/**
	 * 获取日志具体时间字符�?
	 */
	private static String getTimestamp() {
		return new SimpleDateFormat(LOG_TIME_FORMAT).format(new Date());
	}

	/**
	 * 存储日志到SD卡文�?
	 */
	private static void saveToSDCard(String content, LogFileType type) throws Exception {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Writer bw = getDestLogWriter(type);
			bw.write("\r\n");
			bw.write(content);
			bw.flush();
			// 此处考虑性能，并没有关闭IO�?
		}
	}

	// 应用日志文件
	private static File targetFile = null;
	private static Writer targetFileWriter = null;
	// 应用日志当文件天时间�?
	private static long targetFileBeginTs = -1;
	private static long targetFileEndTs = -1;
	// 应用日志ERROR文件
	private static File targetErrFile = null;
	private static Writer targetErrFileWriter = null;

	/**
	 * 日志文件类型
	 */
	private enum LogFileType {
		/**
		 * �?般日志文�?(各级别全纪录)
		 */
		Ordinary,
		/**
		 * ERROR文件（记录ERROR以上级别�?
		 */
		ErrorFile
	}

	private static Writer getDestLogWriter(LogFileType type) throws Exception {
		if (type == LogFileType.ErrorFile) {
			if (targetErrFileWriter == null) {
				targetErrFileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
						getDestLogFile(LogFileType.ErrorFile), true)));
			}
			return targetErrFileWriter;
		} else {
			// 大于targetFileEndTs即视为新的周期（�?天），需重新取得Writer
			long currentTs = System.currentTimeMillis();
			if (targetFileWriter == null || currentTs > targetFileEndTs || currentTs < targetFileBeginTs) {
				targetFileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
						getDestLogFile(LogFileType.Ordinary), true)));
			}
			return targetFileWriter;
		}
	}

	private static File getDestLogFile(LogFileType type) throws Exception {
		File sdCardDir = Environment.getExternalStorageDirectory();
		String filePath = sdCardDir.getPath() + FILE_DIR;
		if (type == LogFileType.ErrorFile) {
			if (targetErrFile == null) {
				// 构�?�日志ERROR文件路径
				filePath = filePath + ERROR_FILE_NAME + FILE_SUFFIX;
				targetErrFile = new File(filePath);
				if (!targetErrFile.exists()) {
					targetErrFile.getParentFile().mkdirs();
					targetErrFile.createNewFile();
				}
			}
			return targetErrFile;
		} else {
			long currentTs = System.currentTimeMillis();
			if (targetFile == null || currentTs > targetFileEndTs || currentTs < targetFileBeginTs) {
				// 构�?�日志文件路�?
				String subDir = new SimpleDateFormat(FILE_DATE_FORMAT).format(new Date(currentTs));
				filePath = filePath + subDir + "/" + FILE_NAME + "_" + subDir + FILE_SUFFIX;
				targetFile = new File(filePath);
				if (!targetFile.exists()) {
					targetFile.getParentFile().mkdirs();
					targetFile.createNewFile();
					// TODO:此处用简便方式获取后�?天起始�?�，稍后修改
					targetFileBeginTs = new SimpleDateFormat("yyyy-MM-dd").parse(subDir).getTime();
					targetFileEndTs = targetFileBeginTs + 24 * 60 * 60 * 1000 - 1;
				}
			}
			return targetFile;
		}
	}
}