/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package if4031.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class User {
    private final String nickName;
    private ArrayList<String> channels;
    private ArrayList<Message> messages;
    public User(String nickname){
        this.nickName = nickname;
        channels = new ArrayList<>();
        messages = new ArrayList<>();
    }
    public String getNickName(){
        return nickName;
    }
    public void joinChannel(String channel){
        if(isJoinChannel(channel)){
            
        }
        else channels.add(channel);
    }
    public void leaveChannel(String channel){
        channels.remove(channel);
    }
    public boolean isJoinChannel(String channel){
        if(channels.isEmpty()) return false;
        else return channels.contains(channel);
    }
    public List<String> getChannels(){
        return channels;
    }
    public void saveMessage(Message message){
        Message m = new Message(message);
        messages.add(m);
        System.out.println("Message added");
    }
    public Message getFirstMessage(){
        Message message = new Message();
        System.out.println("Size : NIIIHHHH " + messages.size());
        if(messages.isEmpty()){
            message = null;
        }
        else {
            message = messages.get(0);
            messages.remove(0);
        }
        return message;
    }
}
