package com.back.domain.system.controller;

public class SystemController {
    private boolean isRunning = true;

    public boolean isRunning(){
        return isRunning;
    }
    public void handleCommand(String command){
        if(command.startsWith("종료")){
            isRunning = false;
        }
        else{
            System.out.println("알 수 없는 명령어");
        }
    }
}
