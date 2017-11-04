package com.wil.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wil.entity.Book;
import com.wil.entity.BookExample;
import com.wil.entity.BookType;
import com.wil.entity.BookTypeExample;
import com.wil.mapper.BookMapper;
import com.wil.mapper.BookTypeMapper;
import com.wil.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wil on 2017/11/4.
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookTypeMapper bookTypeMapper;

    @Override
    public Book findById(Integer id) {
        return bookMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Book> findByPageNo(Integer pageNo) {

        PageHelper.startPage(pageNo, 5);
        BookExample bookExample = new BookExample();
        bookExample.setOrderByClause("id desc");
        List<Book> bookList = bookMapper.selectByExample(bookExample);
        return new PageInfo<Book>(bookList);

    }

    @Override
    public void save(Book book) {
        book.setCurrentnum(book.getTotalnum());
        bookMapper.insert(book);
    }

    @Override
    public void edit(Book book) {
        bookMapper.updateByPrimaryKeySelective(book);
    }

    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<BookType> findAllType() {
        return bookTypeMapper.selectByExample(new BookTypeExample());
    }
}
