package com.back.domain.wiseSaying.service;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import java.util.List;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();

    public WiseSaying add(String content, String author){
        int lastId = wiseSayingRepository.getLastId() + 1;
        wiseSayingRepository.setLastId(lastId);
        WiseSaying wiseSaying = new WiseSaying(lastId, content, author);
        wiseSayingRepository.saveFile(wiseSaying);
        return wiseSaying;
    }

    public List<WiseSaying> list() {
        return wiseSayingRepository.findAll();
    }

    public boolean delete(int id) {
        return wiseSayingRepository.deleteFile(id);
    }

    public void update(WiseSaying wiseSaying) {
        wiseSayingRepository.updateFile(wiseSaying);
    }

    public void build() {
        wiseSayingRepository.build();
    }

    public WiseSaying findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public List<WiseSaying> findAllByContent(String content) {
        return wiseSayingRepository.findAllByContent(content);
    }

    public List<WiseSaying> findAllByAuthor(String author) {
        return wiseSayingRepository.findAllByAuthor(author);
    }
}
