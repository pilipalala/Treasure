// IMyAidlInterface.aidl
package com.wyj.treasure;

// Declare any non-default types here with import statements
//手动导入
import com.wyj.process.Book;
interface IMyAidlInterface {

    String getInfor(String s);
    String getName(char bookName);

	//传递对象。
	String getBook(in Book book);//in 客户端->服务端    out （数据对象由服务端流向客户端）

}

