/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;


import if4031.ChatService;
import if4031.model.Message;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author TOLEP
 */
public class IrcClient {
    private static String nickname;
    private static int alive; // 0 for started, 1 for nickname has set, -1 for exit
    private static TTransport transport;
    private static ChatService.Client client;
    public static void main(String [] args) {
        nickname = null;
        alive = 0;
        try {
            transport = openSocket();
            client = openStream(transport);
        } catch (TTransportException ex) {
            Logger.getLogger(IrcClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(IrcClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        Runnable clientSend = () -> {
            perform();
        };
        new Thread(clientSend).start();
    }
    /*
     * Perform client side
     * @param 
     */
    private static void perform(){
        Scanner in = new Scanner(System.in);
        String command;
        try{
            while(nickname ==  null && alive == 0){
                command = in.nextLine();
                sendCommand(command, client);
            }
            while(nickname !=  null && alive == 1){
                command = in.nextLine();
                TTransport transport = openSocket();
                ChatService.Client client = openStream(transport);
                sendCommand(command, client);
            }
        }catch(TException ex){
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    /*
     * Call service method based on what user's input
     * @param String
     */
    private static void sendCommand(String command, ChatService.Client client) throws TException{
        String[] parsed = command.split(" ");
        switch(parsed[0]){
            case "/NICK" : {
                if (nickname != null) {
                    client.exit(nickname);
                }
                nickname = client.setNickname(parsed[1]);
                System.out.println("Your nickname is " + nickname);
                alive = 1;                    
                break;
            }
            case "/JOIN" : {
                if(nickname != null){
                    if(client.joinChannel(nickname, parsed[1]) == 1){
                        System.out.println("You joined " + parsed[1]);
                        receive();
                    }
                }
                break;
            }
            case "/LEAVE" : {
                if(nickname != null){
                    if(client.leaveChannel(nickname, parsed[1]) == 1){
                        System.out.println("You left " + parsed[1]);
                        receive();
                    }
                }
                break;
            }
            case "/EXIT" : {
                if(nickname != null){
                    if(client.exit(nickname) == 1){
                        alive = -1;
                        System.out.println("You exit ");
                    }
                }
                break;
            }
            default : {
                if(nickname != null){
                    String message;
                    if(parsed[0].startsWith("@")){
                        if(parsed.length > 1){
                            String channel = parsed[0].replaceFirst("@", "");
                            message = parsed[1];
                            if(parsed.length > 2){
                                for(int i = 2; i < parsed.length; i++){
                                    message = message + " " + parsed[i];
                                }
                            }
                            if(client.sendMessageTo(nickname, channel, message) == 1){}
                        }
                    }
                    else{
                        message = parsed[0];
                        if(parsed.length >= 2){
                            for(int i = 1; i < parsed.length; i++){
                                message = message + " " + parsed[i];
                            }
                        }
                        if(client.sendMessage(nickname, message) == 1){}
                    }
                    receive();
                }
            }
        }
    }
    private static void receive(){
        try {
            List<Message> messages = client.receiveMessage(nickname);
            if(!messages.isEmpty()){
                for(Message message : messages){
                    if(message.getChannel() != null && message.getMessage() != null && message.getNickname() != null){
                        System.out.println("[" + message.channel + "] (" + message.nickname + ") " + message.message);
                    }
                }
            }
        } catch (TTransportException ex) {

        } catch (TException ex) {

        }
    }
    private static TTransport openSocket() throws TTransportException, InterruptedException{
        TTransport transport = new TSocket("localhost", 9090);
        transport.open();
        //transport.wait(5000);
        return transport;
    }
    private static ChatService.Client openStream(TTransport transport){
        TProtocol protocol = new TBinaryProtocol(transport);
        ChatService.Client client = new ChatService.Client(protocol);
        return client;
    }
    private static void closeSocket(TTransport transport){
        if(transport.isOpen())
            transport.close();
    }
}
