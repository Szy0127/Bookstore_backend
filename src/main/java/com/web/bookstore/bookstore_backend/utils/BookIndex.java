package com.web.bookstore.bookstore_backend.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;

@NoArgsConstructor
@Getter
public class BookIndex {
    @Field
    private String id;

    @Field
    private String description;

    public BookIndex(String id, String description) {
        this.id = id;
        this.description = description;
    }

}
