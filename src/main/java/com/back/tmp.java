/*
package com.back;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// 최대한 라이브러리를 사용하지 않고 구현
public class tmp {
    public static void main(String[] args) {
        // 작가명(author)와 명언내용(content)에는 특수문자를 입력하지 않는다
        String author;
        String content;
        int contentArrInd = 0;
        int lastId;
        int deleteFileNum;
        int updateFileNum;
        String cmd;
        String tempStr;
        Scanner sc = new Scanner(System.in);

//        init
        String lastIdPath = "db/wiseSaying/lastId.txt";
        File lastIdFile = new File(lastIdPath);
        new File("db/wiseSaying").mkdirs();
        if(!lastIdFile.exists()){
            try {
                lastIdFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(lastIdPath);
                fileOutputStream.write("0".getBytes());
                fileOutputStream.close();
            } catch(Exception e){
                System.out.println(e);
            }
        }

        System.out.println("== 명언 앱 ==");
        System.out.print("명령) ");
        cmd = sc.nextLine();
        while (true) {
            if (cmd.compareTo("종료") == 0) {
                return;
            }
            else if (cmd.compareTo("등록") == 0) {
                lastId = getLastId() + 1;
                setLastId(lastId);
                System.out.print("명언 : ");
                content = sc.nextLine();
                System.out.print("작가 : ");
                author = sc.nextLine();
                insertFile(lastId, author, content);
                System.out.println(lastId + "번 명언이 등록되었습니다.");
            }
            else if(cmd.compareTo("목록") == 0){
                System.out.println(" 번호 / 작가 / 명언\n------------------------");
                readAllFile();
                for(int i = contentArrInd - 1 ; i >= 0 ; i--){
                }
            }
            else if(cmd.substring(0, 2).compareTo("삭제") == 0){
                deleteFileNum = Integer.parseInt(cmd.substring(6, cmd.length()));
                File file = new File("db/wiseSaying/" + deleteFileNum + ".json");
                if(!file.exists()){
                    System.out.println(deleteFileNum + "번 명언은 존재하지 않습니다.");
                }
                else{
                    deleteFile(deleteFileNum);
                    System.out.println(deleteFileNum + "번 명언이 삭제되었습니다.");
                }
            }
            else if(cmd.substring(0, 2).compareTo("수정") == 0){
                updateFileNum = Integer.parseInt(cmd.substring(6, cmd.length()));
                File file = new File("db/wiseSaying/" + updateFileNum + ".json");
                if(!file.exists()){
                    System.out.println(updateFileNum + "번 명언은 존재하지 않습니다.");
                }
                else{
                    Map<String, String> map = getJsonToMap(updateFileNum);
                    System.out.println("명언(기존) : " + map.get("content"));
                    System.out.print("명언 : ");
                    content = sc.nextLine();
                    System.out.println("작가(기존) : " + map.get("author"));
                    System.out.print("작가 : ");
                    author = sc.nextLine();
                    updateFile(updateFileNum, author, content);
                }
            }else if(cmd.compareTo("빌드") == 0) {
                build();
                System.out.println("data.json 파일의 내용이 갱신되었습니다.");
            }
            System.out.print("명령) ");
            cmd = sc.nextLine();
        }
    }
    public static String makeJson(int id, String author, String content){
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
    public static Map<String,String> getJsonToMap(int id){
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader("db/wiseSaying/" + id + ".json"));
            StringBuilder sb = new StringBuilder();
            String s;
            Map<String,String> map = new HashMap<>();
            while((s = bufferedReader.readLine()) != null){
                sb.append(s);
            }

            bufferedReader.close();
            s = sb.toString();

            String target;
            int targetInd;
            String result;

            target = "\"id\"";
            targetInd = s.indexOf(target) + 6;
            result = s.substring(targetInd, s.substring(targetInd).indexOf(",") + targetInd);
            map.put("id", result);

            target = "\"content\"";
            targetInd = s.indexOf(target) + 12;
            result = s.substring(targetInd, s.substring(targetInd).indexOf("\",") + targetInd);
            map.put("content", result);

            target = "\"author\"";
            targetInd = s.indexOf(target) + 11;
            result = s.substring(targetInd, s.substring(targetInd).indexOf("\"") + targetInd);
            map.put("author", result);

            return map;
        } catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    public static void insertFile(int fileNum, String author, String content){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("db/wiseSaying/" + fileNum + ".json");
            byte b[] = makeJson(fileNum, author, content).getBytes();
            fileOutputStream.write(b);
            fileOutputStream.close();
        } catch(Exception e){
            System.out.println(e);
        }
    }
    public static void readFile(int fileNum, String author, String content){
        try {
            FileInputStream fileInputStream = new FileInputStream("db/wiseSaying/" + fileNum + ".json");
        } catch(Exception e){
            System.out.println(e);
        }
        try {
            FileInputStream fileInputStream = new FileInputStream("db/wiseSaying/lastId.txt");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            int i = 0;
            String s = "";
            while((i = bufferedInputStream.read()) != -1){
                s += (char) i;
            }
            fileInputStream.close();
        } catch(Exception e) {
            System.out.println(e);
//            return -1;
        }
    }
    public static void updateFile(int fileNum, String author, String content){
        deleteFile(fileNum);
        insertFile(fileNum, author, content);
    }
    public static void deleteFile(int fileNum){
        File file = new File("db/wiseSaying/" + fileNum + ".json");
        if(file.exists()){
            if(!file.delete()){
                System.out.println("UpdateFileError");
            }
        }
        if(fileNum == getLastId()){
            setLastId(fileNum - 1);
        }
    }
    public static Map<String,String>[] makeMapArr(){
        File file = new File("db/wiseSaying/");
        if(file.isDirectory()){
            Map<String,String>[] result = new HashMap[100];
            String[] fileNameList = file.list();
            String target;
            int targetInd;
            String targetId;
            target = ".json";
            Map<String,String> map = new HashMap<>();
            for(int i = 0 ; i < fileNameList.length; i++){
                targetInd = fileNameList[i].indexOf(target);
                if(targetInd == -1){
                    continue;
                }
                if(!fileNameList[i].equals("data.json")){
                    targetId = fileNameList[i].substring(0, targetInd);
                    result[i] = getJsonToMap(Integer.parseInt(targetId));
                }
            }
            return result;
        }
        return null;
    }
    public static void readAllFile(){
        Map<String,String>[] mapArr = makeMapArr();
        for(int i = 0 ; i < mapArr.length ; i++){
            if(mapArr[i] == null){
                break;
            }
            System.out.println(mapArr[i].get("id") + " / " + mapArr[i].get("author") + " / " + mapArr[i].get("content"));
        }
    }
    public static void build(){
        Map<String,String>[] mapArr = makeMapArr();
        StringBuffer sb = new StringBuffer("[\n");
        for(int i = 0 ; i < mapArr.length ; i++){
//            if(mapArr[i] == null){
//                break;
//            }
            sb.append("  {\n");
            sb.append("    \"id\": " + mapArr[i].get("id") + ",\n");
            sb.append("    \"content\": \"" + mapArr[i].get("content") + "\",\n");
            sb.append("    \"author\": \"" + mapArr[i].get("author") + "\"\n");
            if(mapArr[i + 1] != null){
                sb.append("  },\n");
            }
            else{
                sb.append("  }\n");
                break;
            }
        }
        sb.append("]");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("db/wiseSaying/data.json");
            byte b[] = sb.toString().getBytes();
            fileOutputStream.write(b);
            fileOutputStream.close();
        } catch(Exception e){
            System.out.println(e);
        }
    }
    public static int getLastId(){
        try {
            FileInputStream fileInputStream = new FileInputStream("db/wiseSaying/lastId.txt");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            int i = 0;
            String s = "";
            while((i = bufferedInputStream.read()) != -1){
                s += (char) i;
            }
            fileInputStream.close();
            return Integer.parseInt(s);
        } catch(Exception e) {
            System.out.println(e);
            return -1;
        }
    }
    public static void setLastId(int lastId){
//        File file = new File("db/wiseSaying/lastId.txt");
//        if(file.exists()){
//            if(!file.delete()){
//                System.out.println("UpdateFileError");
//            }
//        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("db/wiseSaying/lastId.txt");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            byte b[] = Integer.toString(lastId).getBytes();
            fileOutputStream.write(b);
            fileOutputStream.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}

*/
