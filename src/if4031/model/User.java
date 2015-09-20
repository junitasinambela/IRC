/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package if4031.model;

import if4031.Message;
import java.util.List;

/**
 *
 * @author user
 */
public class User {
    private String nickName;
    private List<String> channels;
    private List<Message> messages;
    public User(){
        nickName = null;
    }
    public void setNickName(String nickname){
        nickName = nickname;
    }
    public String getNickName(){
        return nickName;
    }
    public void joinChannel(String channel){
        if(isJoinChannel(channel)){
            
        }
        channels.add(channel);
    }
    public void leaveChannel(String channel){
        channels.remove(channel);
    }
    public boolean isJoinChannel(String channel){
        return channels.contains(channel);
    }
    public List<String> getChannels(){
        return channels;
    }
    public void saveMessage(Message message){
        messages.add(message);
    }
    public Message getFirstMessage(){
        Message message;
        if(messages.size() > 0){
            message = messages.get(0);
            messages.remove(0);
        }
        else message = null;
        return message;
    }
}
