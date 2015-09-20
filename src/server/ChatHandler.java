/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import if4031.ChatService;
import if4031.ChatTool;
import if4031.model.Channel;
import if4031.Message;
import if4031.model.User;
import java.util.List;
import org.apache.thrift.TException;

/**
 *
 * @author user
 */
public class ChatHandler implements ChatService.Iface{
    @Override
    public String setNickname(String nickname){
        // check if any user use this nickname. if exist, generate random nickname
        if(ChatTool.isNicknameExist(nickname)){
            nickname = ChatTool.generateRandomNickName();
        }
        User user = new User();
        user.setNickName(nickname);
        ChatTool.addUser(user);
        return nickname;
    }
    
    @Override
    public int joinChannel(String nickname, String channel) throws TException {
        User user;
        if(ChatTool.isNicknameExist(nickname)){
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
        }
        else return 0;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int leaveChannel(String nickname, String channel) throws TException {
        User user;
        // check if the channel exist. if not, create new channel
        if(ChatTool.isNicknameExist(nickname)){
            user = ChatTool.getUser(nickname);
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
        else return 0;
    }

    @Override
    public int sendMessage(String nickname, String message) throws TException {
        User user = ChatTool.getUser(nickname);
        Channel channel;
        for(String c : user.getChannels()){
            channel = ChatTool.getChannel(c);
            channel.sendMessagetoMember(nickname, message);
        }
        return 1;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int sendMessageTo(String nickname, String channel, String message) throws TException {
        if(ChatTool.isChannelExist(channel)){
            Channel channelC = ChatTool.getChannel(channel);
            if(channelC.isMember(nickname)){
                channelC.sendMessagetoMember(nickname, message);
                return 1;
            }
            else return -1;
        }
        else return 0;
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int exit(String nickname) throws TException {
        User user;
        if(ChatTool.isNicknameExist(nickname)){
            user = ChatTool.getUser(nickname);
            List<String> channels = user.getChannels();
            Channel c;
            for(String channel : channels){
                c = ChatTool.getChannel(channel);
                c.userLeave(user.getNickName());
            }
            ChatTool.removeUser(user);
        }
        return 1;
    }

    @Override
    public Message receiveMessage(String nickname) {
        User user = ChatTool.getUser(nickname);
        return user.getFirstMessage();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
