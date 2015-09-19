/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;


import if4031.ChatService;
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
    public static void main(String [] args) {
        try {
            TTransport transport;
            transport = new TSocket("localhost", 9090);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            ChatService.Client processor = new
                    ChatService.Client(protocol);
            Runnable clientSend = new Runnable() {
                public void run() {
                    receiveMessage(processor);
                }
            };
            Runnable clientReceive = new Runnable() {
                public void run() {
                    receiveMessage(processor);
                }
            };
            new Thread(clientSend).start();
            new Thread(clientReceive).start();
            perform(processor);
            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }
    private static void perform(ChatService.Client client) throws TException{
        
    }
    private static void receiveMessage(ChatService.Client client){
        
    }
}
