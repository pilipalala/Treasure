package com.wyj.process;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wangyujie
 *         on 2018/3/1.17:04
 *         TODO
 */

public class Book implements Parcelable {
    /**
     * 反序列化
     */
    public static Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            //这里是不是我们先进行了new一个对象，同时把Parcel对象传入。
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    private String bookName;
    private int bookId;
    private boolean isNew;

    public Book(String name, int bookId, boolean isNew) {
        this.bookName = name;
        this.bookId = bookId;
        this.isNew = isNew;
    }

    public Book(Parcel source) {
        //然后我们再生成这个对象的同时，再把这个对象的属性都赋值好，
        //切记要按照上面写入的顺序来读取出来赋值。
        bookName = source.readString();
        bookId = source.readInt();
        isNew = source.readInt() == 1;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookName=" + getBookName() +
                ", bookId=" + getBookId() +
                ", isNew=" + isNew() +
                '}';
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * @return 0 或 1 ，1 含有文件描述符
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 系列化
     *
     * @param dest  当前对象
     * @param flags 0 或 1，1 代表当前对象需要作为返回值，不能立即释放资源
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //记下 bookName,因为是String类型，所以用writeString
        dest.writeString(bookName);
        //记下 bookId,因为是Int类型，所以用writeInt
        dest.writeInt(bookId);
        /**记下 isNew ,因为是Boolean类型，
         * 但是没有writeBoolean,只有writeBooleanArray，
         * 所以我们用writeInt()来记录，1是true，0是false。
         * 额外说下writeBooleanArray内部其实还是用writeInt来记录的。
         * */

        dest.writeInt(isNew ? 1 : 0);
    }
}
