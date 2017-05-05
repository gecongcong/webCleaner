package websocket;

import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

/**
 * websocket
 * @author Administrator
 *
 */
public class WebSocket implements ServerApplicationConfig {
	@Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scan) {
        // TODO Auto-generated method stub
        return scan;
    }
    /**
     * 接口方式启动
     */
    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(
            Set<Class<? extends Endpoint>> arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}
