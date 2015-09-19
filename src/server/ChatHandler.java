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
import java.util.List;
import org.apache.thrift.TException;

/**
 *
 * @author user
 */
public class ChatHandler implements ChatService.Iface{
    private User user;
    
    @Override
    public int setNickname(String nickname){
        // check if any user use this nickname. if exist, generate random nickname
        if(ChatTool.isNicknameExist(nickname)){
            nickname = ChatTool.generateRandomNickName();
        }
        user.setNickName(nickname);
        return 1;
    }
    
    @Override
    public int joinChannel(String channel) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int leaveChannel(String channel) throws TException {
        // check if the channel exist. if not, create new channel
        Channel c;
        if(ChatTool.isChannelExist(channel)){
            c = ChatTool.getChannel(channel);
        }
        else {
            c = new Channel(channel);
        }
        user.joinChannel(channel); // bagaimana jika channel tsb udah dia join?
        c.userJoin(user.getNickName());
        return 1;    
    }

    @Override
    public int sendMessage(String message) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int sendMessageTo(String channel, String message) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int exit() throws TException {
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
    public Message receiveMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
