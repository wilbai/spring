package com.wil.service;

import com.github.pagehelper.PageInfo;
import com.wil.entity.Book;
import com.wil.entity.BookType;

import java.util.List;

/**
 * Created by wil on 2017/11/4.
 */
public interface BookService {

    Book findById(Integer id);
    PageInfo<Book> findByPageNo(Integer pageNo);
    void save(Book book);
    void edit(Book book);
    void deleteById(Integer id);

    List<BookType> findAllType();


}
