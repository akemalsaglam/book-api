package com.readingisgood.bookapi.domain.book;

import com.readingisgood.bookapi.domain.common.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends BaseRepository<BookEntity, UUID> {

}
