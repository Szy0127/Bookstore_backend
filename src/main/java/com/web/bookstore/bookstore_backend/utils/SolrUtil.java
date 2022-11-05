package com.web.bookstore.bookstore_backend.utils;

import com.web.bookstore.bookstore_backend.entity.Book;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SolrUtil {
    @Autowired
    private SolrClient solrClient;

//    @Autowired
//    private BookDao bookDao;

    //value无法给static注入
    @Value("${solr.collection}")
    private String collection;

    public boolean addBook(Book book) {

        try {
            solrClient.addBean(collection, new BookIndex(book.getBookID() + "", book.getDescription()));
            solrClient.commit(collection);
            return true;
        } catch (IOException | SolrServerException e) {
            return false;
        }
    }

    public boolean addBooks(List<Book> bookList) {
        try {
            for (Book book : bookList) {
                System.out.println(book);
                System.out.println(collection);
                System.out.println(new BookIndex(book.getBookID() + "", book.getDescription()));
                solrClient.addBean(collection, new BookIndex(book.getBookID() + "", book.getDescription()));
            }
            solrClient.commit(collection);
            return true;
        } catch (IOException | SolrServerException e) {
            return false;
        }
    }

    public boolean removeBook(Integer bookID){
        try {
            solrClient.deleteById(collection, bookID.toString());
            solrClient.commit(collection);
            return true;
        } catch (SolrServerException | IOException e) {
            return false;
        }
    }

    public List<Integer> query(String description){
        int row = 10;
        int start = 0;
        SolrQuery query = new SolrQuery();
        query.setQuery("description:"+description);
        query.setRows(row);//默认10  没法设置无穷
        List<Integer> res = new ArrayList<>();
        try{
            while(true){
                query.setStart(start);
                QueryResponse responseOne = solrClient.query(collection, query);
                long num = responseOne.getResults().getNumFound();
                List<BookIndex> books = responseOne.getBeans(BookIndex.class);
                for (BookIndex book : books) {
                    res.add(Integer.valueOf(book.getId()));
                }
                if(res.size() == num){
                    break;
                }
                start += row;
            }
            return res;
        } catch (SolrServerException | IOException e) {
            return res;
        }
    }
}
