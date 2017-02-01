import javax.jms.*;
import javax.naming.Context;


/**
 * This produces to a queue in weblogic
 * Created by HW on 10/27/16.
 */
public class Produce extends MessageCreatorParent {


    public static void main(String args[]) throws Exception {
        Produce prod = new Produce();
        Context context = prod.getContext();
        try {
            prod.createMsg(context, "TestQueue", "FUNCIONA2!");
            prod.consumeMsg(context, "TestQueue");
        }
        catch(Exception e){
          e.printStackTrace();
        }
        finally {
            context.close();
        }
    }


    private void consumeMsg(Context context, String queueName) {
        QueueConnectionFactory qcf;
        QueueConnection qc = null;
        QueueSession qs = null;
        QueueSender sender = null;
        Queue queue;
        try {
// Set up JMS components
            qcf = (QueueConnectionFactory) context.lookup(JMS_QUEUE_FACTORY);
            qc = qcf.createQueueConnection();
            qs = qc.createQueueSession(false,
                    Session.AUTO_ACKNOWLEDGE);
// Get a handle to the JMS queue
            queue = (javax.jms.Queue) context.lookup(queueName);
            MessageConsumer consumer = qs.createConsumer(queue);
            qc.start();
            Message message =           consumer.receive(1000);
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println("Received: " + text);
            } else {
                System.out.println("Received: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                qc.stop();
                sender.close();
                qc.close();
                qs.close();
            } catch (Exception e) {
            }
        }


    }


    private void createMsg(Context context, String queueName, String msgTxt) {
        QueueConnectionFactory qcf;
        QueueConnection qc = null;
        QueueSession qs = null;
        QueueSender sender = null;
        Queue queue;
        TextMessage msg;

        try {
// Set up JMS components
            qcf = (QueueConnectionFactory) context.lookup(JMS_QUEUE_FACTORY);
            qc = qcf.createQueueConnection();
            qs = qc.createQueueSession(false,
                    Session.AUTO_ACKNOWLEDGE);
// Get a handle to the JMS queue
            queue = (javax.jms.Queue) context.lookup(queueName);
// Create ...
            msg = qs.createTextMessage();
            msg.setText(msgTxt);
// ... and send the message
            sender = qs.createSender(queue);
            sender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sender.close();
                qc.close();
                qs.close();

            } catch (Exception e) {
            }
        }
    }

}
