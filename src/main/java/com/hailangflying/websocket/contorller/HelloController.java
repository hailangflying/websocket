package com.hailangflying.websocket.contorller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.hailangflying.websocket.socket.SocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

/**
 * @program: websocket
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @description: 测试用
 * @author: lijh99
 * @create: 2019-03-13 11:06
 * @version: V1.0
 **/


@Controller
@RequestMapping("/hello")
public class HelloController {


    @RequestMapping(path = "/say/{id}/{name}",method = RequestMethod.GET)
    @ResponseBody
    public String sayHello(@PathVariable("id") Integer id, @PathVariable("name") String name){
        SocketIOServer ss = SocketServer.getSingleInstance();
        //  ss.sendEvent("advert_info","aaaaa");
        //      service.stopServer();
        //    assert  service==null;

        Collection<SocketIOClient> dd =ss.getBroadcastOperations().getClients();

        ss.getBroadcastOperations().sendEvent("advert_info", id+"=="+name);
        ss.getBroadcastOperations().sendEvent("advert_info", id+"=="+name);


        return "你好";
    }


    @RequestMapping("/bool")
    public boolean testBool(){
        return Boolean.FALSE;
    }
}

