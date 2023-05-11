//
// This file must be implemented when completing "ChatRobot activity"
//

import faces.IChatMessage;
import faces.IChatServer;
import faces.INameServer;
import faces.MessageListener;
import utils_rmi.ChatConfiguration;

import java.rmi.registry.LocateRegistry;

/**
 * ChatRobot implementation
 *
 * <p> Notice ChatRobot implements MessageListener. MUST not extend ChatClient.
 */

public class ChatRobot implements MessageListener {

    private ChatConfiguration conf;

    private IChatServer cs = null;

    public ChatRobot(ChatConfiguration conf) {
        this.conf = conf;
    }

    public static void main(String args[]) {
        ChatRobot cr = new ChatRobot(ChatConfiguration.parse(args));
        System.out.println("OK ==> 'ChatClient' running at " + cr.conf.getMyHost() + ":" + cr.conf.getMyPort());
        cr.work();
    }

    @Override
    public void messageArrived(IChatMessage msg) {
        //*****************************************************************
        // Activity: implement robot message processing


    }

    private void work() {

        String channelName = conf.getChannelName();
        if (channelName == null) channelName = "#Linux";
        System.out.println("Robot will connect to server: '" + conf.getServerName() + "'" +
                ", channel: '" + channelName + "'" +
                ", nick: '" + conf.getNick() + "'" +
                ", using name server: '" + conf.getNameServerHost() + ":" + conf.getNameServerPort() + "'");


        try {
            //*****************************************************************
            // Activity: implement robot connection and joining to channel



        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }




    }
}
