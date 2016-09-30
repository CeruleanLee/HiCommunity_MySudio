package com.don.tools;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * @author dong 2014年3月15日 11:05:43
 * @category 管理Bpi的事件发布和定阅
 */
public class BpiEventBus {

    private static HashMap<String, EventBusBean> hmBpiBus = new HashMap<String, EventBusBean>();

    /**
     * @author dong 修改Object..匹配问题
     * @category 发布消息 接收必须为Object[]
     */
    public static void post(String tag, Object... target) {
        getBpiBus(tag).getEventBus().post(target);
    }

    /**
     * @param tag    bus的标记
     * @param target 目标类
     * @author tanlet
     * @category 注册bus, 响应函数名有四种类型： <br>
     * 1.onEvent(Object[])：与发布同一线种. <br>
     * 2.onEventMainThread(Object[])：事件响应函数会在Android应用的主线程 <br>
     * 3.onEventBackgroundThread(Object[])：事件响应函数会在一个后台线程中执行 <br>
     * 4.onEventAsync(Object[])：事件响应函数在另外一个异步线程中执行。 <br>
     * 5.自定义方法名<br>
     * 参数不限。
     */
    public static void resgist(String tag, Object target) {
        getBpiBus(tag).getEventBus().register(target);
        getBpiBus(tag).AddObject(target);
    }

    /**
     * @param target 目标类
     * @param tab    bus的标记
     * @author dong
     * @category 注册bus, 响应函数名有四种类型： <br>
     * 1.onEvent(Object[])：与发布同一线种. <br>
     * 2.onEventMainThread(Object[])：事件响应函数会在Android应用的主线程 <br>
     * 3.onEventBackgroundThread(Object[])：事件响应函数会在一个后台线程中执行 <br>
     * 4.onEventAsync(Object[])：事件响应函数在另外一个异步线程中执行。 <br>
     * 5.自定义方法名<br>
     * 参数不限。
     */
    public static void resgist(Object target, String... tab) {
        for (int i = 0; i < tab.length; i++) {
            resgist(tab[i], target);
        }
    }

    /**
     * @param tag      ，bus的标记
     * @param target   ，目标类
     * @param priority 优先权
     * @author dong
     * @category 注册bus, 响应函数名有四种类型： <br>
     * 1.onEvent(Object[])：与发布同一线种. <br>
     * 2.onEventMainThread(Object[])：事件响应函数会在Android应用的主线程 <br>
     * 3.onEventBackgroundThread(Object[])：事件响应函数会在一个后台线程中执行 <br>
     * 4.onEventAsync(Object[])：事件响应函数在另外一个异步线程中执行。 <br>
     * 5.自定义方法名<br>
     */
    public static void resgist(String tag, Object target, int priority) {
        getBpiBus(tag).getEventBus().register(target, priority);
        getBpiBus(tag).AddObject(target);
    }

    /**
     * @param target   ，目标类
     * @param priority 优先权
     * @param tab      ，bus的标记
     * @author dong
     * @category 注册bus, 响应函数名有四种类型： <br>
     * 1.onEvent(Object[])：与发布同一线种. <br>
     * 2.onEventMainThread(Object[])：事件响应函数会在Android应用的主线程 <br>
     * 3.onEventBackgroundThread(Object[])：事件响应函数会在一个后台线程中执行 <br>
     * 4.onEventAsync(Object[])：事件响应函数在另外一个异步线程中执行。 <br>
     * 5.自定义方法名<br>
     */
    public static void resgist(Object target, int priority, String... tab) {
        for (int i = 0; i < tab.length; i++) {
            resgist(tab[i], target, priority);
        }
    }

    /**
     * @param tag    ，bus的标记
     * @param target ，目标类
     * @author tanlet
     * @category 取消bus
     */
    public static void unResgist(String tag, Object target) {
        getBpiBus(tag).getEventBus().unregister(target);
        getBpiBus(tag).RemoveObject(target);
        removeBus(tag);
    }

    /**
     * @param tab    ，bus的标记
     * @param target ，目标类
     * @author dong
     * @category 取消bus
     */
    public static void unResgist(Object target, String... tab) {
        for (int i = 0; i < tab.length; i++) {
            unResgist(tab[i], target);
        }
    }

    /**
     * @param tag bus的标记
     * @author dong
     * @category 自动生成唯一的EventBusBean
     */
    public static EventBusBean getBpiBus(String tag) {
        if (hmBpiBus.get(tag) != null) {
            return hmBpiBus.get(tag);
        } else {
            return getInstance(tag);
        }
    }

    /**
     * @param tag bus的标记
     * @author dong
     * @category 自动生成唯一的EventBusBean
     */
    public static EventBus getTheEventBus(String tag) {
        if (hmBpiBus.get(tag) != null) {
            return hmBpiBus.get(tag).getEventBus();
        } else {
            return getInstance(tag).getEventBus();
        }
    }

    /***
     * @param tag
     * @author dong 修改移除问题
     */
    public static void removeBus(String tag) {
        if (getBpiBus(tag).getObjectCount() == 0) {
            hmBpiBus.remove(tag);
        }
    }

    public static EventBusBean getInstance(String tag) {
        EventBusBean bus = new EventBusBean();
        hmBpiBus.put(tag, bus);
        return bus;
    }
}
