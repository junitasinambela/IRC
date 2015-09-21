/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package if4031;

import if4031.model.Channel;
import if4031.model.User;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author user
 */
public class ChatTool {
    private static ArrayList<User> users;
    private static ArrayList <Channel> channels;
    private static ArrayList<String> listRandom;

    public ChatTool(){
        users = new ArrayList<>();
        channels = new ArrayList<>();
        listRandom = new ArrayList<>();
        listRandom.add("jun");
        listRandom.add("ita");
        listRandom.add("afik");
        listRandom.add("sin");
        listRandom.add("bela");
    }
    
    public static boolean isNicknameExist(String nickName){
        boolean exist = false;
        if(!users.isEmpty())
            for(User u : users){
                if(u.getNickName().equals(nickName)){
                    exist = true;
                    break;
                }
            }
        return exist;
    }
    public static boolean isChannelExist(String channel){
        boolean exist = false;
        if(!channels.isEmpty())
            for(Channel c : channels){
                if(c.getChannelName().equals(channel)){
                    exist = true;
                    break;
                }
            }
        return exist;
    }
    
    public static User getUser(String nickName){
        User user = null;
        for(User u : users){
            if(u.getNickName().equals(nickName)){
                user = u;
                break;
            }
        }
        return user;
    }
    public static Channel getChannel(String channelName){
        Channel channel = null;
        for(Channel c : channels){
            if(c.getChannelName().equals(channelName)){
                channel = c;
                break;
            }
        }
        return channel;
    }
    public static void addUser(User user){
        users.add(user);
    }
    public static void addChannel(Channel channel){
        channels.add(channel);
    }
    public static String generateRandomNickName(){
        Random rn = new Random();
        int idx = rn.nextInt(4);
        return listRandom.get(idx);
    }
    public static void removeUser(User user){
        users.remove(user);
    }
}
