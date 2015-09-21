/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import if4031.ChatService;
import if4031.ChatTool;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 *
 * @author TOLEP
 */
public class IrcServer {
    public static ChatHandler handler;
    public static ChatService.Processor processor;
    static Logger logger = Logger.getLogger(IrcServer.class);
    public static void main(String args[]){
        BasicConfigurator.configure();
        ChatTool chatTool = new ChatTool();
        try {
            handler = new ChatHandler();
            processor = new ChatService.Processor(handler);
            Runnable thread = new Runnable(){
                @Override
                public void run() {
                    handle(processor);
                }
            };
            new Thread(thread).start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
    public static void handle(ChatService.Processor processor){
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TThreadPoolServer(new
                    TThreadPoolServer.Args(serverTransport).processor(processor));
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
