package com.back.domain.wiseSaying.entity;

import java.util.HashMap;
import java.util.Map;

public class WiseSaying {
    private int id;
    private String content;
    private String author;

    public WiseSaying(int id, String content, String author){
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String toJsonString(){
        String result;
//        result = "{\n" +
//                "  \"id\": " + id + ",\n" +
//                "  \"content\": \"" + content + "\",\n" +
//                "  \"author\": \"" + author + "\"\n" +
//                "}";

        Map<String, String> map = new HashMap<>();
        map.put("id", Integer.toString(id));
        map.put("content", content);
        map.put("author", author);

        StringBuffer sb = new StringBuffer("{\n");
        sb.append("  \"id\": " + map.get("id") + ",\n");
        sb.append("  \"content\": \"" + map.get("content") + "\",\n");
        sb.append("  \"author\": \"" + map.get("author") + "\"\n");
        sb.append("}");

        result = sb.toString();
        return result;
    }
}
