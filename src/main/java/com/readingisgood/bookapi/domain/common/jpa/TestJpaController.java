package com.readingisgood.bookapi.domain.common.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestJpaController {

   /* private final BaseRepository<BaseEntity, Long> baseRepository;
    private int counter = 0;

    @GetMapping("/insert")
    public void insert() {
        BaseEntity baseEntity = new BaseEntity();
        counter++;
        baseEntity.setName(String.valueOf(counter));
        baseRepository.save(baseEntity);
    }*/
}
