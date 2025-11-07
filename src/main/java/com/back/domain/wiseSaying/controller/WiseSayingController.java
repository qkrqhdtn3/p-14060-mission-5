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
        if (command.startsWith("등록")) {
            System.out.print("명언 : ");
            String content = scanner.nextLine();
            System.out.print("작가 : ");
            String author = scanner.nextLine();
            WiseSaying wiseSaying = wiseSayingService.add(content, author);
            System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
        } else if (command.startsWith("목록")) {
            if (command.split("\\?").length == 1) {
                List<WiseSaying> wiseSayings = wiseSayingService.list();
                int page = 1;
                System.out.println(makePage(page, wiseSayings));
            } else if (command.split("\\?").length > 1) {
                String[] params = command.split("\\?");
                if (params[1].startsWith("page=")) {
                    List<WiseSaying> wiseSayings = wiseSayingService.list();
                    int page = Integer.parseInt(params[1].split("=")[1]);
                    System.out.println(makePage(page, wiseSayings));
                } else if (params[1].startsWith("keywordType=content")) {
                    if (params[1].split("&")[1].startsWith("keyword=")) {
                        System.out.print("""
                                ------------------------
                                검색타입 : %s
                                검색어 : %s
                                ------------------------
                                """.formatted(params[1].split("&")[0].split("=")[1],
                                params[1].split("&")[1].split("=")[1]));
                        String content = params[1].split("&")[1].split("=")[1];
                        List<WiseSaying> wiseSayings = wiseSayingService.findAllByContent(content);
                        int page = 1;
                        if (params[1].split("&").length == 2) {
                            page = 1;
                        } else if (params[1].split("&")[2].startsWith("page=")) {
                            page = Integer.parseInt(params[1].split("&")[2].split("=")[1]);
                        }
                        System.out.println(makePage(page, wiseSayings));
                    } else {
                        System.out.println("목록?keywordType=content& not keyword= error");
                        return;
                    }
                } else if (params[1].startsWith("keywordType=author")) {
                    System.out.print("""
                            ------------------------
                            검색타입 : %s
                            검색어 : %s
                            ------------------------
                            """.formatted(params[1].split("&")[0].split("=")[1],
                            params[1].split("&")[1].split("=")[1]));
                    if (params[1].split("&")[1].startsWith("keyword=")) {
                        String author = params[1].split("&")[1].split("=")[1];
                        List<WiseSaying> wiseSayings = wiseSayingService.findAllByAuthor(author);
                        int page = 1;
                        if (params[1].split("&").length == 2) {
                            page = 1;
                        } else if (params[1].split("&")[2].startsWith("page=")) {
                            page = Integer.parseInt(params[1].split("&")[2].split("=")[1]);
                        }
                        System.out.println(makePage(page, wiseSayings));
                    } else {
                        System.out.println("목록?keywordType=author& not keyword= error");
                        return;
                    }
                } else {
                    System.out.println("목록? error");
                    return;
                }
            }
        } else if (command.startsWith("삭제")) {
//            int id = Integer.parseInt(command.substring(6, command.length()));
            int id = Integer.parseInt(command.split("\\?")[1].split("=")[1]);
//            System.out.println(command.split("\\?")[1]);
            if (wiseSayingService.delete(id)) {
                System.out.println(id + "번 명언이 삭제되었습니다.");
            } else {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            }
        } else if (command.startsWith("수정")) {
//            int id = Integer.parseInt(command.substring(6, command.length()));
            int id = Integer.parseInt(command.split("\\?")[1].split("=")[1]);
            WiseSaying wiseSaying = wiseSayingService.findById(id);
            if (wiseSaying == null) {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            } else {
                System.out.println("명언(기존) : " + wiseSaying.getContent());
                System.out.print("명언 : ");
                String content = scanner.nextLine();
                System.out.println("작가(기존) : " + wiseSaying.getAuthor());
                System.out.print("작가 : ");
                String author = scanner.nextLine();
                wiseSayingService.update(new WiseSaying(id, content, author));
            }
        }
        else if (command.startsWith("빌드")) {
            System.out.println("빌드 명령어는 더 이상 사용되지 않습니다.");
//            wiseSayingService.build();
//            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        }
    }

    public String makePage(int page, List<WiseSaying> wiseSayings) {
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
        pageContent.append("번호 / 작가 / 명언\n------------------------\n");
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
                pageInfo.append("[").append(i).append("] ");
            } else {
                pageInfo.append(i).append(" ");
            }
            if (i != totalPages) {
                pageInfo.append("/ ");
            }
        }
        return pageInfo.toString();
    }
}