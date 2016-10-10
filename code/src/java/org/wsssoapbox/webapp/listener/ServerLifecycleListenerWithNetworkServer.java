package org.wsssoapbox.webapp.listener;

public class ServerLifecycleListenerWithNetworkServer
    extends ServerLifecycleListener
{
    public static final String START_NETWORK_SERVER =
        "derby.drda.startNetworkServer";

    public static final String HOST =
        "derby.drda.host";

    public static final String PORT_NUMBER =
        "derby.drda.portNumber";

   @Override
    protected void initializeCloudscape() {
        System.setProperty(
            START_NETWORK_SERVER,
            "true"
        );
        super.initializeCloudscape();
    }


}
