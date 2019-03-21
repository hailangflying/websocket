package com.hailangflying.websocket.event;

import java.util.EventObject;

/**
 * @program: websocket
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @description: 事件对象
 * @author: lijh99
 * @create: 2019-03-13 11:44
 * @version: V1.0
 **/

public class PrintEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public PrintEvent(Object source) {
        super(source);
    }


    public void doEvent(){
        System.out.println("通知一个事件源 source：" + this.getSource());
    }


}
