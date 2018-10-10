package com.wyj.mvp.service.retrofit;

/**
 * @author wangyujie
 * @date 2018/9/19.15:13
 * @describe 添加描述
 */
public class HttpMoveResult<T> {
    public int count;
    public int start;
    public int total;
    public String title;
    public T subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }
}
