package com.mathbeta.rational.common.jetty;

import com.mathbeta.rational.common.utils.ConfigHelper;
import com.mathbeta.rational.common.utils.DateUtil;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by 147458 on 2017/4/12.
 */
public class StartJetty {

    private static StartJetty sj = null;
    private static String authCode = null;

    private StartJetty() {
    }

    public static StartJetty getInstance() {
        if(sj == null) {
            sj = new StartJetty();
        }

        return sj;
    }

    public void startJetty() {
//        authCode = AuthorizationUtils.auth(ConfigHelper.getJettyParameter("server.name"));
        String rootPath = StartJetty.class.getResource("/").getFile().toString();
        rootPath = rootPath.replace("target/classes/", "src/main/webapp/");
        Server server = new Server(Integer.parseInt(ConfigHelper.getJettyParameter("server.port")));
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath(ConfigHelper.getJettyParameter("server.name"));
        webapp.setResourceBase(rootPath);
        webapp.setDescriptor(rootPath + "/" + ConfigHelper.getJettyParameter("server.descriptor"));
        webapp.setParentLoaderPriority(true);
        webapp.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(webapp);

        try {
            System.out.println("====================================================================================================");
            System.out.println("[" + DateUtil.getDateTime() + "] " + ConfigHelper.getJettyParameter("server.name") + " is starting......");
//            System.out.println("系统授权码：" + authCode);
//            License.checkLicence();
            server.start();
            System.out.println("[" + DateUtil.getDateTime() + "] " + ConfigHelper.getJettyParameter("server.name") + " has been started.");
            System.out.println("[" + DateUtil.getDateTime() + "] Please visit: http://127.0.0.1:" + ConfigHelper.getJettyParameter("server.port") + ConfigHelper.getJettyParameter("server.name"));
//            System.out.println("[" + DateUtil.getDateTime() + "] " + ConfigHelper.getJettyParameter("server.name") + " Start successfully.");
            System.out.println("====================================================================================================");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }
}
