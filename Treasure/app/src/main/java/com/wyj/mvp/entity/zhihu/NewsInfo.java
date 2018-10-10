package com.wyj.mvp.entity.zhihu;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;


/**
 * @author wangyujie
 * @date 2018/9/18.15:13
 * @describe 收藏的实体
 */
@Entity
public class NewsInfo {
    @Id(autoincrement = true)
    private Long id;

    @Index(unique = true)
    private String newsId;

    private String imagePath;
    private String title;


    public NewsInfo(String newsId, String imagePath, String title) {
        this.newsId = newsId;
        this.imagePath = imagePath;
        this.title = title;
    }


    @Generated(hash = 1143340420)
    public NewsInfo(Long id, String newsId, String imagePath, String title) {
        this.id = id;
        this.newsId = newsId;
        this.imagePath = imagePath;
        this.title = title;
    }


    @Generated(hash = 859431180)
    public NewsInfo() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getNewsId() {
        return this.newsId;
    }


    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }


    public String getImagePath() {
        return this.imagePath;
    }


    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public String getTitle() {
        return this.title;
    }


    public void setTitle(String title) {
        this.title = title;
    }
}
