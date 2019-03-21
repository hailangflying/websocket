package com.hailangflying.websocket;

import com.hailangflying.websocket.event.PrintEvent;
import com.hailangflying.websocket.listener.MonitortListener;
import com.hailangflying.websocket.source.EventSource;

import java.lang.annotation.Annotation;

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
 * @create: 2019-03-13 15:31
 * @version: V1.0
 **/

public class EventTest {
    public static void main(String[] args) {
        final EventSource eventSource = new EventSource();
        eventSource.addListeners(new MonitortListener() {
            @Override
            public void handleEvent(PrintEvent printEvent) {

                printEvent.doEvent();

                if(printEvent.getSource().equals("openWindows")){
                    System.out.println("doOpen");
                }

                if(printEvent.getSource().equals("closeWindows")){
                    System.out.println("doClose");
                }
            }
        });


        System.out.println(11111);

        /*
         * 传入openWindows事件，通知所有的事件监听器
         * 对open事件感兴趣的listener将会执行
         */
        eventSource.notifyListenerEvents(new PrintEvent("openWindows"));


        /*
         * 传入closeWindows事件，通知所有的事件监听器
         * 对open事件感兴趣的listener将会执行
         */
        eventSource.notifyListenerEvents(new PrintEvent("closeWindows"));
    }
}
