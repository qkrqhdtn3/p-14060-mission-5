package com.back;

import com.back.domain.system.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;

import java.util.Scanner;

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final SystemController systemController = new SystemController();
    private final WiseSayingController wiseSayingController = new WiseSayingController(scanner);

//        init
    public App() {
    }

    public void run() {
        System.out.println("== 명언 앱 ==");
        while (systemController.isRunning()) {
            System.out.print("명령) ");
            String command = scanner.nextLine();
            if (command.startsWith("등록") || command.startsWith("목록")
                    || command.startsWith("삭제") || command.startsWith("수정")
                    || command.startsWith("빌드")) {
                wiseSayingController.handleCommand(command);
            } else {
                systemController.handleCommand(command);
            }
        }
    }
}
