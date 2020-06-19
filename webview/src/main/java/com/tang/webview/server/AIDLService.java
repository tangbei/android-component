package com.tang.webview.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.tang.webview.Book;
import com.tang.webview.IBookManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/2
 * Description: java类作用描述
 */
public class AIDLService extends Service {

    private List<Book> list = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        list = new ArrayList<>();
        initData();
    }

    private void initData() {
        list.add(new Book("三国演义"));
        list.add(new Book("武林外传"));
        list.add(new Book("红楼梦"));

    }

    private IBookManager.Stub stub = new IBookManager.Stub() {
        @Override
        public List<Book> getBook() throws RemoteException {
            return list;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (null != book){
                book.setName("我是修改后的书名哦");
                list.add(book);
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
