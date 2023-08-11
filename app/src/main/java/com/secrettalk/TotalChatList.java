package com.secrettalk;

import java.util.HashMap;

public class TotalChatList {

    private HashMap<String, String> chatData;

    public TotalChatList() {
        this.settingMapData();
    }
    public void setChatData(String name, String chatList) {
        this.chatData.put(name, chatList);
    }

    public String getChatName(String dept) {
        return chatData.get(dept);
    }

    private void settingMapData(){
        chatData = new HashMap<String, String>();
        chatData.put("컴퓨터공학과20학번", "Chat_00");
        chatData.put("수학과16학번", "Chat_01");
        chatData.put("화학과16학번", "Chat_02");
        chatData.put("수학과18학번", "Chat_03");
        chatData.put("화학과질문방", "Chat_04");
        chatData.put("화학과18학번", "Chat_05");
        chatData.put("수학과질문방", "Chat_06");
        chatData.put("기우회", "Chat_07");

    }
}
