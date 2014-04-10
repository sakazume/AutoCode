package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class SampleWebStarter {
    public static void main(final String[] pArgs) throws Exception {
    	Server s = new Server(8080);

        WebAppContext webContext = new WebAppContext();
        webContext.setResourceBase("src/main/webapp");
        webContext.setContextPath("/");
        webContext.setParentLoaderPriority(true);

        s.setHandler(webContext);

        s.start();
        s.join();

    }

    private static int getWebPort() {
        final String webPort = System.getenv("PORT"); //$NON-NLS-1$
        if (webPort == null || webPort.isEmpty()) {
            return 8080;
        }
        return Integer.parseInt(webPort);
    }
}
