package compent;

import conf.ConfClient;
import conf.Watcher;
import conf.impl.ZkFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by wanghongxing on 15/12/1.
 */
@Component
public class ConfWorker implements InitializingBean{

    private ConfClient confClient = null;

    @Override
    public void afterPropertiesSet() throws Exception {

        confClient = ZkFactory.getConfClient("127.0.0.1", "2181");

        confClient.handleNodeData("/zoo2", (path, message) ->
            System.out.println("get message from conf-center , path " + path + " message : " + message)
        );

        confClient.handleNodeData("/zoo1",(path, message) ->
                System.out.println("get message from conf-center , path " + path + " message : " + message)
        );

        Watcher watcher = new Watcher() {
            @Override
            public void process(String path, String message) {
                System.out.println("get message from conf-center , path " + path + " message : " + message);
            }
        };

        confClient.handleNodeData("/zoo3", watcher);

    }

}