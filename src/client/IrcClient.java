/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;


import if4031.ChatService;
import if4031.Message;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author TOLEP
 */
public class IrcClient {
    private static TTransport transport;
    private static ChatService.Client client;
    private static String nickname;
    private static int alive; // 0 for started, 1 for nickname has set, -1 for exit
    public static void main(String [] args) {
        nickname = null;
        alive = 0;
        Runnable clientSend = () -> {
            try {
                perform();
            } catch (TException ex) {
                Logger.getLogger(IrcClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        new Thread(clientSend).start();
    }
    private static void perform() throws TException{
        transport = new TSocket("localhost", 9090);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        client = new ChatService.Client(protocol);
        Scanner in = new Scanner(System.in);
        while(nickname ==  null && alive == 0){
             String command = in.nextLine();
             sendCommand(command);
        }
        while(nickname !=  null && alive == 1){
             String command = in.nextLine();
             sendCommand(command);
        }
        transport.close();
    }
    private static void receiveMessage(ChatService.Client client){
        
    }
    private static void sendCommand(String command) throws TException{
        String[] parsed = command.split(" ");;
        switch(parsed[0]){
            case "NICK" : {
                nickname = client.setNickname(command);
                alive = 1;
                Runnable clientReceive = () -> {
                    receive();
                };
                new Thread(clientReceive).start();
                break;
            }
            case "JOIN" : {
                if(nickname != null){
                    if(client.joinChannel(nickname, command) == 1){}
                }
            }
            case "LEAVE" : {
                if(nickname != null){
                    if(client.leaveChannel(nickname, command) == 1){}
                }
            }
            case "EXIT" : {
                if(nickname != null){
                    if(client.exit(nickname) == 1){
                        alive = -1;
                    }
                }
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
                                    message = " " + parsed[i];
                                }
                            }
                            if(client.sendMessageTo(nickname, channel, message) == 1){}
                        }
                    }
                    else{
                        message = parsed[1];
                        if(parsed.length > 2){
                            for(int i = 2; i < parsed.length; i++){
                                message = " " + parsed[i];
                            }
                        }
                        if(client.sendMessage(nickname, message) == 1){}
                    }
                }
            }
        }
    }
    private static void receive(){
        while(alive == 1){
            try {
                Message message = client.receiveMessage(nickname);
                System.out.println("[" + message.channel + "] (" + message.nickname + ") " + message.message);
            } catch (TException ex) {
                ex.printStackTrace();
            }
        }
    }
}
