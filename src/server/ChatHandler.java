/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import if4031.ChatService;
import if4031.ChatTool;
import if4031.model.Channel;
import if4031.model.Message;
import if4031.model.User;
import java.util.Date;
import java.util.List;
import org.apache.thrift.TException;

/**
 *
 * @author user
 */
public class ChatHandler implements ChatService.Iface{
    @Override
    public String setNickname(String nickname){
        Date date = new Date(System.currentTimeMillis());
        // check if any user use this nickname. if exist, generate random nickname
        if(ChatTool.isNicknameExist(nickname)){
            nickname = ChatTool.generateRandomNickName();
        }else {System.out.println("No nickname");}
        User user = new User(nickname);
        ChatTool.addUser(user);
        System.out.println(
                "[" + date.getYear() + "-" + date.getMonth() + "-" + date.getDate() + "] "
                        + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " NICKNAME " + nickname);
        return nickname;
    }
    
    @Override
    public int joinChannel(String nickname, String channel) throws TException {
        Date date = new Date(System.currentTimeMillis());
        System.out.println(
                "[" + date.getYear() + "-" + date.getMonth() + "-" + date.getDate() + "] "
                        + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + nickname + " JOIN " + channel );
        User user;
        user = ChatTool.getUser(nickname);
        Channel c;
        if(ChatTool.isChannelExist(channel)){
            c = ChatTool.getChannel(channel);
        }
        else {
            c = new Channel(channel);
            ChatTool.addChannel(c);
        }
        user.joinChannel(channel); // bagaimana jika channel tsb udah dia join?
        c.userJoin(user.getNickName());
        return 1;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public int leaveChannel(String nickname, String channel) throws TException {
        Date date = new Date(System.currentTimeMillis());
        System.out.println(
                "[" + date.getYear() + "-" + date.getMonth() + "-" + date.getDate() + "] "
                        + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + nickname + " LEAVE " + channel );
        User user = ChatTool.getUser(nickname);
        Channel c;
        if(ChatTool.isChannelExist(channel)){
            c = ChatTool.getChannel(channel);
            user.leaveChannel(channel);
            c.userLeave(user.getNickName());
            // if the channel has 0 member, delete the channel
            return 1;
        }
        else {
            return -1;
        }
    }

    @Override
    public int sendMessage(String nickname, String message) throws TException {
        Date date = new Date(System.currentTimeMillis());
        User user = ChatTool.getUser(nickname);
        Channel channel;
        for(String c : user.getChannels()){
            channel = ChatTool.getChannel(c);
            channel.sendMessagetoMember(nickname, message);
        }
        System.out.println(
                "[" + date.getYear() + "-" + date.getMonth() + "-" + date.getDate() + "] "
                        + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + nickname + " SEND MESSAGE");
        return 1;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int sendMessageTo(String nickname, String channel, String message) throws TException {
        Date date = new Date(System.currentTimeMillis());
        System.out.println(
                "[" + date.getYear() + "-" + date.getMonth() + "-" + date.getDate() + "] "
                        + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + nickname + " SEND MESSAGE TO " + channel);
        if(ChatTool.isChannelExist(channel)){
            Channel channelC = ChatTool.getChannel(channel);
            if(channelC.isMember(nickname)){
                channelC.sendMessagetoMember(nickname, message);
                System.out.println("Message added");
                return 1;
            }
            else return -1;
        }
        else return 0;
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int exit(String nickname) throws TException {
        Date date = new Date(System.currentTimeMillis());
        System.out.println(
                "[" + date.getYear() + "-" + date.getMonth() + "-" + date.getDate() + "] "
                        + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + nickname + " EXIT");
        User user = ChatTool.getUser(nickname);
        List<String> channels = user.getChannels();
        Channel c;
        for(String channel : channels){
            c = ChatTool.getChannel(channel);
            c.userLeave(user.getNickName());
        }
        ChatTool.removeUser(user);
        return 1;
    }

    @Override
    public List<Message> receiveMessage(String nickname){
        Date date = new Date(System.currentTimeMillis());
        System.out.println(
                "[" + date.getYear() + "-" + date.getMonth() + "-" + date.getDate() + "] "
                        + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + nickname + " RECEIVE MESSAGE");
        User user = ChatTool.getUser(nickname);
        List<Message> messages = user.getMessage();
        return messages;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
