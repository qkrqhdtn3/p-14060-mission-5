package com.back;

import java.util.HashMap;
import java.util.Map;

public class Rq {
    private final String actionName;
    private final Map<String, String> paramsMap = new HashMap<>();

    public Rq(String command) {
        actionName = command.split("\\?")[0];
//        ex)등록, 목록, 빌드
        if (command.split("\\?").length == 1) {

        } else if (command.split("\\?").length > 1) {
            String[] params = command.split("\\?");
            if (params[1].split("&").length == 1) {
                paramsMap.put(params[1].split("=")[0], params[1].split("=")[1]);
            } else if (params[1].split("&").length > 1) {
                for (int i = 0; i < params[1].split("&").length; i++) {
                    paramsMap.put(params[1].split("&")[i].split("=")[0], params[1].split("&")[i].split("=")[1]);
                }
            } else {
//                이런 case가 있을지 모르겠으므로 발견하기 위한 예외처리
                throwException("Rq if (params[1].split(\"&\").length == 1)의 else 예외");
            }
            Map<String, String> paramsMap = new HashMap<>();
        } else {
//            이런 case가 있을지 모르겠으므로 발견하기 위한 예외처리
            throwException("Rq if(command.split(\"\\\\?\").length == 1)의 else 예외");
        }
    }

    public String getActionName() {
        return actionName;
    }

    public String getParam(String paramName, String defaultValue) {
        return paramsMap.getOrDefault(paramName, defaultValue);
    }

    public int getParamAsInt(String paramName, int defaultValue) {
        return Integer.parseInt(paramsMap.getOrDefault(paramName, Integer.toString(defaultValue)));
    }

    public void throwException(String string) {
        try {
            throw new Exception(string);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
