// IBookManager.aidl
package com.tang.webview;

import com.tang.webview.Book;

// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBook();

    void addBook(inout Book book);
}
