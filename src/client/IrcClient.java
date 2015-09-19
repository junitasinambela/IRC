/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;


import if4031.ChatService;
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
    public static void main(String [] args) {
        Runnable clientSend = new Runnable() {
            public void run() {
                try {
                    perform();
                } catch (TException ex) {
                    Logger.getLogger(IrcClient.class.getName()).log(Level.SEVERE, null, ex);
                }
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
       //while(true){
            String command = in.nextLine();
            sendCommand(command);
       //}
       transport.close();
    }
    private static void receiveMessage(ChatService.Client client){
        
    }
    private static void sendCommand(String command) throws TException{
        String[] parsed = parsing(command);
        switch(parsed[0]){
            case "NICK" : {
                if(client.setNickname(command) == 1){
                    
                }
            }
            case "JOIN" : {
                if(client.joinChannel(command) == 1){
                    Runnable clientReceive = new Runnable() {
                        public void run() {
                            receive();
                        }
                    };
                    new Thread(clientReceive).start();
                }
            }
            case "LEAVE" : {
                if(client.leaveChannel(command) == 1){
                    
                }
            }
            case "EXIT" : {
                if(client.exit() == 1){
                    
                }
            }
            default : {
                if(parsed[0].charAt(0) == '@'){
                    if(client.sendMessageTo(command, command) == 1){
                    
                    }
                }
                else{
                    if(client.sendMessage(command) == 1){
                    
                    }
                }
            }
        }
    }
    private static String[] parsing(String command){
        
        return null;
    }
    private static void receive(){
        
    }
}
