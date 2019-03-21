package com.hailangflying.websocket.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: websocket
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @description: socket服务
 * @author: lijh99
 * @create: 2019-03-13 11:00
 * @version: V1.0
 **/
@Service
public class SocketServer {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static SocketIOServer server;
    private static Map<String, SocketIOClient> clientsMap = new HashMap<String,SocketIOClient>();


    public static SocketIOServer getSingleInstance(){
        if(null == server ) {
            synchronized(SocketIOServer.class){
                if(null == server) {
                    Configuration config = new Configuration();
                    config.setHostname("localhost");
                    config.setPort(9092);
                    config.setMaxFramePayloadLength(1024 * 1024);
                    config.setMaxHttpContentLength(1024 * 1024);
                    server = new SocketIOServer(config);

                }
            }
        }
        return server;
    }


    public SocketServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startServer();
                    logger.info("SocketIOServer启动成功！");
                } catch (InterruptedException e) {
                    logger.error("SocketIOServer启动失败！", e);
                }
            }
        },"SocketIOServer的启动线程").start();
    }





    public void startServer() throws InterruptedException {

        server = getSingleInstance();
        // 监听广告推送事件，advert_info为事件名称，自定义

        server.addEventListener("advert_info", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest)
                    throws ClassNotFoundException {
                // 客户端推送advert_info事件时，onData接受数据，这里是string类型的json数据，还可以为Byte[],object其他类型

                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1, sa.indexOf(":"));// 获取客户端连接的ip
                // Map params = client.getHandshakeData().getUrlParams();//
                // 获取客户端url参数

                System.out.println(clientIp + "：客户端：************" + data);
                // client.sendEvent("advert_info", data+"你好……");
            }
        });
        // 监听通知事件
        server.addEventListener("notice_info", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                // 同上
            }
        });

        /**
         * 监听其他事件
         */

        // 添加客户端连接事件
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1, sa.indexOf(":"));// 获取设备ip
                System.out.println(clientIp + "-------------------------" + "客户端已连接");
                // Map params = client.getHandshakeData().getUrlParams();
                String uuid = client.getSessionId().toString();
                clientsMap.put(uuid, client);
                // 给客户端发送消息
                client.sendEvent("advert_info", clientIp + "客户端你好，我是服务端，有什么能帮助你的？");
            }
        });
        // 添加客户端断开连接事件
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1, sa.indexOf(":"));// 获取设备ip
                System.out.println(clientIp + "-------------------------" + "客户端已断开连接");
                String uuid = client.getSessionId().toString();
                // 移除客户端实例
                clientsMap.remove(uuid);
                // 给客户端发送消息
                client.sendEvent("advert_info", clientIp + "客户端你好，我是服务端，期待下次和你见面");
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }





    public void stopServer() {
        if (server != null) {
            server.stop();
            server = null;
        }
    }




    /**
     * 给所有连接客户端推送消息
     *
     * @param eventType
     *            推送的事件类型
     * @param message
     *            推送的内容
     */
    public void sendMessageToAllClient(String eventType, String message) {
        Collection<SocketIOClient> clients = server.getAllClients();
        for (SocketIOClient client : clients) {
            client.sendEvent(eventType, message);
        }
    }


    /**
     * 给具体的客户端推送消息
     *
     * @param deviceId
     *            设备类型
     * @param eventType推送事件类型
     * @param message
     *            推送的消息内容
     */
    public void sendMessageToOneClient(String uuid, String eventType, String message) {
        try {
            if (uuid != null && !"".equals(uuid)) {
                SocketIOClient client = (SocketIOClient) clientsMap.get(uuid);
                if (client != null) {
                    client.sendEvent(eventType, message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SocketIOServer getServer() {
        return SocketServer.server;
    }
}
