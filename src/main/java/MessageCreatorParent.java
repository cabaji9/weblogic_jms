import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

/**
 * Created by HW on 10/31/16.
 */
public class MessageCreatorParent {

    static final String WLS_ADDRESS="t3://localhost:7003";

    static final String WLS_CTX_FACTORY =
            "weblogic.jndi.WLInitialContextFactory";
//    static final String JMS_QUEUE_FACTORY =
//            "weblogic.jms.ConnectionFactory";

    //cuando usa foreing server.
    static final String JMS_QUEUE_FACTORY =
            "jms/ForeignAMQConnectionFactory";




    static final String JMS_QUEUE_NAME =
            "TestQueue";

    protected Context getContext() throws Exception{
        Context ct;
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.PROVIDER_URL, WLS_ADDRESS);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                WLS_CTX_FACTORY);
        env.put(Context.SECURITY_PRINCIPAL, "weblogic");
        env.put(Context.SECURITY_CREDENTIALS, "weblogic0");
// Get a server connection
        ct = new InitialContext(env);
        return ct;
    }
}
