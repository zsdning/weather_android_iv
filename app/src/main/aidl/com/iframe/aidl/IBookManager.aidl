// IBookManager.aidl
package com.iframe.aidl;
import com.iframe.aidl.Book;
// Declare any non-default types here with import statements
//Just for AIDL TEST
interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBookList();
    void addBook(in Book book);
}
