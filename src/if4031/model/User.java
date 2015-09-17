/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package if4031.model;

import java.util.List;

/**
 *
 * @author user
 */
public class User {
    private String nickName;
    private List<String> channels;
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
}
