/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package irc;

import java.util.List;

/**
 *
 * @author user
 */
public class ChatHandler {
    private User user;
    public String setNickname(String nickname){
        // check if any user use this nickname. if exist, generate random nickname
        if(ChatTool.isNicknameExist(nickname)){
            nickname = ChatTool.generateRandomNickName();
        }
        user.setNickName(nickname);
        return nickname;
    }
    public String joinChannel(String channel){
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
        return "ok";
    }
    public String leaveChannel(String channel){
        // check if the channel exist in user's channel list. if not, return error
        Channel c;
        if(user.isJoinChannel(channel)){
            c = ChatTool.getChannel(channel);
            user.leaveChannel(channel);
            c.userLeave(user.getNickName());
            return "ok";
        }
        else {
            return "error";
        }
    }
    public String sendMessage(String message){
        return null;
    }
    public String sendMessageTo(String channel, String message){
        return null;
    }
    public String exit(){
        List<String> channels = user.getChannels();
        Channel c;
        for(String channel : channels){
            c = ChatTool.getChannel(channel);
            c.userLeave(user.getNickName());
        }
        ChatTool.removeUser(user);
        return "ok";
    }
}
