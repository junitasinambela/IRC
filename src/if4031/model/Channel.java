/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package if4031.model;

import if4031.ChatTool;
import if4031.Message;
import java.util.List;

/**
 *
 * @author user
 */
public class Channel {
    private final String channelName;
    private List<String> users;
    public Channel(String channelName){
        this.channelName = channelName;
    }
    public String getChannelName(){
        return channelName;
    }
    public void userJoin(String nickName){
        users.add(nickName);
    }
    public void userLeave(String nickName){
        users.remove(nickName);
    }
    public void sendMessagetoMember(String nickSource, String message){
        User user;
        Message messageM = new Message();
        messageM.channel = channelName;
        messageM.message = message;
        messageM.nickname = nickSource;
        messageM.time = null;
        for(String nick : users){
            user = ChatTool.getUser(nick);
            user.saveMessage(messageM);
        }
    }
    public boolean isMember(String user){
        return users.contains(user);
    }
}
