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
public class ChatTool {
    private static List<User> users;
    private static List <Channel> channels;
    public static boolean isNicknameExist(String nickName){
        return false;
    }
    public static boolean isChannelExist(String channel){
        return false;
    }
    public static User getUser(String nickName){
        return null;
    }
    public static Channel getChannel(String channelName){
        return null;
    }
    public static void addUser(User user){
        users.add(user);
    }
    public static void addChannel(Channel channel){
        channels.add(channel);
    }
    public static String generateRandomNickName(){
        return null;
    }
    public static void removeUser(User user){
        users.remove(user);
    }
}
