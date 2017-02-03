/**
 * Created by hw on 2/2/17.
 */

import org.junit.Test;

import javax.naming.Context;

public class TestMq  {

    @Test
    public void testMq() throws Exception{
        Produce prod = new Produce();
        Context context = prod.getContext();
        try {
            prod.createMsg(context, "jms/TestQueue", "FUNCIONA2!");
            prod.consumeMsg(context, "jms/TestQueue");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            context.close();
        }
    }

}
