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
import java.util.Map;

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
    public PageInfo<Book> findPageWithType(Integer pageNo) {
        PageHelper.startPage(pageNo, 5);
        List<Book> bookList = bookMapper.findWithType();
        return new PageInfo<Book>(bookList);
    }

    @Override
    public PageInfo<Book> findByPageNo(Integer pageNo, Map<String, Object> queryParam) {
        PageHelper.startPage(pageNo, 5);
        List<Book> bookList = bookMapper.findByQueryParam(queryParam);
        return new PageInfo<Book>(bookList);
    }

    @Override
    public List<String> findAuthorList() {
        return bookMapper.findAllAuthor();
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

    @Override
    public BookType findTypeById(Integer id) {
        return bookTypeMapper.selectByPrimaryKey(id);
    }
}
