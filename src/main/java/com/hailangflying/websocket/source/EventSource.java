package com.hailangflying.websocket.source;

import com.hailangflying.websocket.event.PrintEvent;
import com.hailangflying.websocket.listener.MonitortListener;

import java.util.Vector;

/**
 * @program: websocket
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @description:
 * @author: lijh99
 * @create: 2019-03-13 11:47
 * @version: V1.0
 **/


/**
 * 事件源
 * 事件源是事件对象的入口，包含监听器的注册、撤销、通知
 *
 */
public class EventSource {

    //监听器列表，如果监听事件源的事件，注册监听器可以加入此列表
    private Vector<MonitortListener> listeners = new Vector<>();

    //注册监听器
    public void addListeners(MonitortListener listener) {
        listeners.add(listener);
    }

    //删除监听器
    public void removeListeners(MonitortListener listener){
        int i = listeners.indexOf(listener);
        if(i>=0){
            listeners.remove(listener);
        }
    }


    //接受外部事件，通知所有的监听器
    public void notifyListenerEvents(PrintEvent event){
        for (MonitortListener listener:listeners){
            listener.handleEvent(event);
        }
    }



    //只是注册关闭窗口的事件
    public void addCloseWindowListener(MonitortListener listener){
        System.out.println("添加关闭窗口事件");
        listeners.add(listener);
    }

    public void doCloseWindowListener(){

    }

}
