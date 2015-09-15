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
}
