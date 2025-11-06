package com.back;

import com.back.standard.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AppTestRunner {
/*
        입출력 리다이렉션 이후 Scanner 객체 여러 개 사용 시 발생할 수 있는 에러와 해결방법
        https://velog.io/@qkrqhdtn3/%EC%9E%85%EC%B6%9C%EB%A0%A5-%EB%A6%AC%EB%8B%A4%EC%9D%B4%EB%A0%89%EC%85%98-%EC%9D%B4%ED%9B%84-Scanner-%EA%B0%9D%EC%B2%B4-%EC%97%AC%EB%9F%AC-%EA%B0%9C-%EC%82%AC%EC%9A%A9-%EC%8B%9C-%EB%B0%9C%EC%83%9D%ED%95%A0-%EC%88%98-%EC%9E%88%EB%8A%94-%EC%97%90%EB%9F%AC%EC%99%80-%ED%95%B4%EA%B2%B0%EB%B0%A9%EB%B2%95
        1. 상황
        다음과 같은 에러 발생할 시
        java.util.NoSuchElementException: No line found
        2. 원인
        App에서 scanner.nextLine() 진행 시
        App의 scanner buffer에 "등록\n현재를 사랑하라.\n작자미상\n종료\n"이 할당되고
        Controller에서 scanner.nextLine() 진행 시
        Controller의 scanner buffer에는 아무것도 할당되지 않는다.
        3. 결론
        App과 Controller에서 같은 Scanner 객체를 사용하자.
*/
    public static String run(String s) {
        TestUtil.setInFromString(s);
        ByteArrayOutputStream testOut = TestUtil.setOutToByteArray();
        try {
            App app = new App();
            app.run();
        } catch (Exception e) {
            System.out.println(e);
        }
        TestUtil.clearSetInFromString();
        TestUtil.clearSetOutToByteArray();
        return testOut.toString();
    }
}
