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
            Rq rq = new Rq(command);
            if (rq.getActionName().equals("등록") || rq.getActionName().equals("목록") || rq.getActionName().equals("삭제") || rq.getActionName().equals("수정") || rq.getActionName().equals("빌드")) {
                wiseSayingController.handleCommand(command);
            }
            else {
                systemController.handleCommand(command);
            }
        }
    }
}
