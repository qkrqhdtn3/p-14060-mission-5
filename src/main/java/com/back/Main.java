package com.back;

public class Main {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
    private static void testRq1() {
        Rq rq = new Rq("목록?searchKeywordType=content&searchKeyword=자바&page=2");
        String searchKeywordType = rq.getParam("searchKeywordType", "");
        String searchKeyword = rq.getParam("searchKeyword", "");
        String sort = rq.getParam("sort", "idDesc");
        int page = rq.getParamAsInt("page", -1);
        int id = rq.getParamAsInt("id", -1);

        System.out.println("actionName : " + rq.getActionName());
        System.out.println("param searchKeywordType : " + searchKeywordType);
        System.out.println("param searchKeyword : " + searchKeyword);
        System.out.println("param sort : " + sort);
        System.out.println("param page : " + page);
        System.out.println("param id : " + id);
    }
}