package frc.team2158.robot;

import java.util.logging.Logger;

 class WebsocketStuff {
    private static final Logger LOGGER = Logger.getLogger(WebsocketStuff.class.getName());

     static void startWebsocket(){
        Robot.httpClient.websocket(2158, "tegra-ubuntu", "/server", websocket ->
                websocket.handler(data -> LOGGER.info(data.toString("UTF-8"))));
    }
}
