package com.back.domain.wiseSaying.controller;

import com.back.Rq;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
    private final WiseSayingService wiseSayingService = new WiseSayingService();
    private final Scanner scanner;

    public WiseSayingController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void handleCommand(String command) {
        Rq rq = new Rq(command);
        if (rq.getActionName().equals("등록")) {
            actionWrite(rq);
        } else if (rq.getActionName().equals("목록")) {
            actionList(rq);
        } else if (rq.getActionName().equals("삭제")) {
            actionDelete(rq);
        } else if (rq.getActionName().equals("수정")) {
            actionUpdate(rq);
        } else if (rq.getActionName().equals("빌드")) {
            System.out.println("빌드 명령어는 더 이상 사용되지 않습니다.");
        }
    }

    public void actionWrite(Rq rq) {
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();
        WiseSaying wiseSaying = wiseSayingService.add(content, author);
        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.getId()));
    }

    public void actionList(Rq rq) {
//        keywordType이 없는 경우
        if (rq.getParam("keywordType", "").equals("")) {
            List<WiseSaying> wiseSayings = wiseSayingService.list();
//            paramter에 page가 있을 때는 그 값을, 없을 때는 1을 return
            int page = rq.getParamAsInt("page", 1);
            System.out.println(makePage(rq, page, wiseSayings));
        } else if (rq.getParam("keywordType", "").equals("content")) {
            String content = rq.getParam("keyword", "");
            List<WiseSaying> wiseSayings = wiseSayingService.findAllByContent(content);
            int page = rq.getParamAsInt("page", 1);
            System.out.println(makePage(rq, page, wiseSayings));
        } else if (rq.getParam("keywordType", "").equals("author")) {
            String author = rq.getParam("keyword", "");
            List<WiseSaying> wiseSayings = wiseSayingService.findAllByAuthor(author);
            int page = rq.getParamAsInt("page", 1);
            System.out.println(makePage(rq, page, wiseSayings));
        } else {
//            keywordType이 parameter에 있는 경우 content, author 둘 중 하나여야 함 1
            System.out.println("keywordType error 1");
        }
    }

    private void actionDelete(Rq rq) {
        int id = rq.getParamAsInt("id", -1);
        if (wiseSayingService.delete(id)) {
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
        } else {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        }
    }

    private void actionUpdate(Rq rq) {
        int id = rq.getParamAsInt("id", -1);
        WiseSaying wiseSaying = wiseSayingService.findById(id);
        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        } else {
//            텍스트 블록의 후행 공백은 \s로 표시할 수 있습니다.
            System.out.print("""
                    명언(기존) : %s
                    명언 :\s""".formatted(wiseSaying.getContent()));
            String content = scanner.nextLine();
            System.out.print("""
                    작가(기존) : %s
                    작가 :\s""".formatted(wiseSaying.getAuthor()));
            String author = scanner.nextLine();
            wiseSayingService.update(new WiseSaying(id, content, author));
            System.out.println("%d번 명언이 수정되었습니다.".formatted(id));
        }
    }

    public String makePage(Rq rq, int page, List<WiseSaying> wiseSayings) {
        StringBuilder pageContent = new StringBuilder();
//        if(wiseSayings == null){
//            return "글이 존재하지 않습니다.";
//        }
        int totalPages = wiseSayings.size() / 5;
        if (wiseSayings.size() % 5 != 0) {
            totalPages += 1;
        }
        if (page > totalPages) {
            return "존재하지 않는 페이지입니다.";
        }
//        else if (totalPages <= 0) {
//            return "글이 존재하지 않습니다.";
//        }
        //본문
        if (!rq.getParam("keywordType", "").equals("")) {
            pageContent.append("------------------------\n");
        }
        if (rq.getParam("keywordType", "").equals("content")) {
            pageContent.append("검색타입 : content\n");
            pageContent.append("검색어 : %s\n".formatted(rq.getParam("keyword", "")));
        } else if (rq.getParam("keywordType", "").equals("author")) {
            pageContent.append("검색타입 : author\n");
            pageContent.append("검색어 : %s\n".formatted(rq.getParam("keyword", "")));
        } else if (!rq.getParam("keywordType", "").equals("")) {
//            keywordType이 parameter에 있는 경우 content, author 둘 중 하나여야 함 2
            System.out.println("keywordType error 2");
            return "";
        }
        if (!rq.getParam("keywordType", "").equals("")) {
            pageContent.append("------------------------\n");
        }
        pageContent.append("""
                번호 / 작가 / 명언
                ------------------------
                """);
        int startIndex = (page - 1) * 5;
        int endIndex = Math.min(startIndex + 5, wiseSayings.size());
        for (int i = startIndex; i < endIndex; i++) {
            WiseSaying wiseSaying = wiseSayings.get(i);
            pageContent.append("%d / %s / %s\n".formatted(wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent()));
        }
        pageContent.append("------------------------\n");
        //페이징
        pageContent.append(pagingation(page, totalPages));
        return pageContent.toString();
    }

    public String pagingation(int currentPage, int totalPages) {
        StringBuilder pageInfo = new StringBuilder();
        pageInfo.append("페이지 : ");
        for (int i = 1; i <= totalPages; i++) {
            if (i == currentPage) {
                pageInfo.append("[%d] ".formatted(i));
            } else {
                pageInfo.append("%d ".formatted(i));
            }
            if (i != totalPages) {
                pageInfo.append("/ ");
            }
        }
        return pageInfo.toString();
    }
}