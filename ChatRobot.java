//
// This file must be implemented when completing "ChatRobot activity"
//

import faces.*;
import impl.ChatMessageImpl;
import impl.ChatUserImpl;
import utils_rmi.ChatConfiguration;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

/**
 * ChatRobot implementation
 *
 * <p> Notice ChatRobot implements MessageListener. MUST not extend ChatClient.
 */

public class ChatRobot implements MessageListener {

    private ChatConfiguration conf;
    private IChatServer cs = null;
    private INameServer ns = null; // Agrega esta línea para obtener la referencia al NameServer

    private IChatUser cu = null;

    private IChatChannel channel = null;

    public ChatRobot(ChatConfiguration conf) {
        this.conf = conf;
    }

    public static void main(String[] args) {
        ChatRobot cr = new ChatRobot(ChatConfiguration.parse(args));
        cr.work();
    }

    public void work() {
        try {
            //*****************************************************************
            // Activity: implement robot connection and joining to channel

            // Obtener referencia al NameServer
            String nshost = conf.getNameServerHost();
            int nsport = conf.getNameServerPort();
            ns = (INameServer) LocateRegistry.getRegistry(nshost, nsport).lookup(INameServer.NS_NAME);
            System.out.println("Referencia al NameServer obtenida: " + utils_rmi.RemoteUtils.remote2String(ns));

            // Obtener referencia al ChatServer
            String serverName = conf.getServerName();
            cs = (IChatServer) ns.lookup(serverName);
            System.out.println("Referencia al ChatServer obtenida: " + utils_rmi.RemoteUtils.remote2String(cs));

            // Conectarse al ChatServer
            String nick = conf.getNick();
            MessageListener ml = this;
            cu = new ChatUserImpl(nick,ml);
            cs.connectUser(cu);
            System.out.println("Conexión establecida con el usuario " + nick);

            // Hacer listado de channel
            IChatChannel[] channels = null;
            channels = cs.listChannels();
            String channelName = conf.getChannelName();
            boolean channelExists = false;

            //Mensajes recibidos
            for (IChatChannel channel : channels) {
                System.out.println("Canal: " + channel.getName());
                if (channel.getName().equals(channelName)) {
                    channelExists = true;
                    break;
                }
            }
            if (!channelExists) {
                System.out.println("El canal " + channelName + " no existe");
            } else {
                System.out.println("El canal " + channelName + " existe");
                System.out.println("Unido al canal " + channelName);
                channel = cs.getChannel(channelName);
                IChatUser[] users = channel.join(cu);
                for (IChatUser user : users) {
                    if (user.getNick().equals(nick)) {
                        continue;
                    }
                    System.out.println("Usuario: " + user.getNick());
                }
                for (IChatUser user : users) {
                    if (user.getNick().equals(nick)) {
                        continue;
                    }
                    ChatMessageImpl message = new ChatMessageImpl(cu, user, "(Personal) Hola, soy " + nick);
                    user.sendMessage(message);
                    break;
                }
//                public ChatMessageImpl (IChatUser src, IChatChannel dst, String str)
                ChatMessageImpl message = new ChatMessageImpl(cu, channel, "Hola, soy " + nick);
                channel.sendMessage(message);
//                capturar los mensajes del channel



            }








        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(IChatMessage icm) {
        String msg;
        IChatUser sender = null;
        Remote destination = null;
        boolean priv = false;
        try {
            msg = icm.getText();
            sender = icm.getSender();
            destination = icm.getDestination();
            priv = icm.isPrivate();

            if(!msg.contains("JOIN "+cu.getNick()) && msg.contains("JOIN")){
                channel.sendMessage(new ChatMessageImpl(cu, channel, "Hola buenas" + msg.substring(4)));
            }
            if (priv) {
                sender.sendMessage(new ChatMessageImpl(cu, sender, "Soy un robot, y la única respuesta que he " +
                        "aprendido hasta ahora es que 1+1 = 2"));
            }
            System.out.println(msg);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}