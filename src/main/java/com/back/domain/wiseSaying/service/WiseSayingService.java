package com.back.domain.wiseSaying.service;

import com.back.domain.wiseSaying.dao.WiseSayingDao;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import java.util.List;

public class WiseSayingService {
//    private final WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
    private final WiseSayingDao wiseSayingDao = new WiseSayingDao();

    public WiseSaying add(String content, String author) {
        int lastId = wiseSayingDao.getLastId() + 1;
        WiseSaying wiseSaying = new WiseSaying(lastId, content, author);
        wiseSayingDao.insertWiseSaying(wiseSaying);
        return wiseSaying;
    }

    public List<WiseSaying> list() {
        return wiseSayingDao.findAll();
    }

    public boolean delete(int id) {
        return wiseSayingDao.deleteWiseSaying(id);
    }

    public void update(WiseSaying wiseSaying) {
        wiseSayingDao.updateWiseSaying(wiseSaying);
    }

    //    public void build() {
//        wiseSayingRepository.build();
//    }

    public WiseSaying findById(int id) {
        return wiseSayingDao.findById(id);
    }

    public List<WiseSaying> findAllByContent(String content) {
        return wiseSayingDao.findAllByContent(content);
    }
    public List<WiseSaying> findAllByAuthor(String author) {
        return wiseSayingDao.findAllByAuthor(author);
    }
}
