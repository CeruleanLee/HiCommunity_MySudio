package com.don.tools;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * 
 * @author dong
 * @category EventBus通知类Bean
 */
public class EventBusBean {

	EventBus eventBus=new EventBus();
	ArrayList<Object> classList=new ArrayList<Object>();

	public EventBus getEventBus() {
		return eventBus;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public ArrayList<Object> getClassList() {
		return classList;
	}

	public void setClassList(ArrayList<Object> classList) {
		this.classList = classList;
	}
	
	public int getObjectCount()
	{
		return classList.size();
	}
	
	public void AddObject(Object object)
	{
		this.classList.add(object);
	}
    
	public void RemoveObject(Object object)
	{
		this.classList.remove(object);
	}
}
