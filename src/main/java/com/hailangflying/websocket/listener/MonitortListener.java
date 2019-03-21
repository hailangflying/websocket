package com.hailangflying.websocket.listener;


import com.hailangflying.websocket.event.PrintEvent;

import java.util.EventListener;

public interface MonitortListener extends EventListener {

    void handleEvent(PrintEvent printEvent);
}
