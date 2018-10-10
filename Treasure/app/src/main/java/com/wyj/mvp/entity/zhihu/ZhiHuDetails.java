package com.wyj.mvp.entity.zhihu;

import java.util.List;

/**
 * @author wangyujie
 * @date 2018/9/17.18:30
 * @describe 添加描述
 */
public class ZhiHuDetails {

    /**
     * body : <div class="main-wrap content-wrap">
     <div class="headline">

     <div class="img-place-holder"></div>



     </div>

     <div class="content-inner">




     <div class="question">
     <h2 class="question-title">为什么中国围棋近年来变强了？</h2>

     <div class="answer">

     <div class="meta">
     <img class="avatar" src="http://pic3.zhimg.com/v2-328e9303e1d7e485d2b923959651048e_is.jpg">
     <span class="author">聂卫平，</span><span class="bio">围棋职业九段</span>
     </div>

     <div class="content">
     <p>我经常说： 国运兴、棋运兴。</p>
     <p>作为近半个世纪围棋发展的见证人，我很欣慰看到我国围棋顶尖棋手的厚度强过任何一个国家，已经有很大成绩。</p>
     <p>我国围棋近年来越来越强，背后一定是有越来越大量的爱好者为之着迷，才有如今勇攀高峰的基础。</p>
     <p>我国人口基数大，但是优秀的棋手多集中在一线城市，交流也不够充分。</p>
     <p>首先让棋界整体提高的是围棋甲级联赛。</p>
     <p>在甲级联赛之前，国内最强的就是常昊，国际交流的机会更多，其他人锻炼的机会不够。</p>
     <p>围甲时代开始以后，古力为代表的年轻棋手成长非常快。围甲让我国围棋水平整体强了一大块。</p>
     <p>赛事慢慢变多，网络的普及迅速革新了信息传播的效率，这让越来越多的人可以非常快匹配到对手，获取资源，通过网络马上就能跟旗鼓相当的对手下棋。</p>
     <p>柯洁是网络时代成长的例子，他用一个账号每年会下上百盘。</p>
     <p>我们总会创造奇迹，但整个行业越来越好，一定是顺应了时代。</p>
     <p>围棋作为最高的智力竞技运动，中日擂台赛以后席卷全国，全国人民仿佛都建立了极大的智力上的自豪感，我也受到了党和国家的极大关注和鼓励。收到的信件多的拆不过来。</p>
     <p>就在擂台赛前的 60/70 年代，我国围棋水平长期较日本差距在一先以上，就是说我方每盘先下不用贴目，基本上难求一胜。曾经，北京只保留了七个棋手，平时还都要在京郊工厂做体力劳动。</p>
     <p>当时我国整体信心低到什么程度？</p>
     <p>86 年第二届中日擂台赛，对方把我队打到就剩我一人对阵，当时日本气势最盛的五位选手，我对记者讲，我对每个人都有五成的把握。言外之意就是说五盘都赢下来，可能还不到三十二分之一。</p>
     <p>第二天报道变成了这样：聂卫平认为中方赢下来的概率仍有百分之五十。我马上被约谈，被批评说你讲话也太狂了。</p>
     <p>最后连报道都刊登了更正声明。</p>
     <p>围棋人都有一个特点，客观认识自己，全力以赴去获胜。</p>
     <p>因为我有先天心脏病，所以每次出国重大比赛都要被特批出入境携带氧气瓶。遵照医嘱，非重大比赛只下卫生棋，也就是轻松一些的对局。</p>
     <p>我致力于培养后备人才，二十多年，聂卫平围棋道场已经走出二十三位世界冠军和全国冠军，以后当然还会越来越多。</p>
     <hr><p> </p>
     <p>欢迎关注 <a href="https://www.zhihu.com/remix/albums/1023297931847290880">知乎大学 · 极简围棋：棋圣聂卫平与欧冠樊麾带你入门</a></p>
     </div>
     </div>


     <div class="view-more"><a href="http://www.zhihu.com/question/21231439">查看知乎讨论<span class="js-question-holder"></span></a></div>

     </div>


     </div>
     </div><script type=“text/javascript”>window.daily=true</script>
     * image_source : Yestone 邑石网正版图库
     * title : 中国围棋似乎这几年变强了？聂卫平：你说得没错
     * image : https://pic2.zhimg.com/v2-59d912c6d64071d136bc32b0a09bac05.jpg
     * share_url : http://daily.zhihu.com/story/9695994
     * js : []
     * ga_prefix : 091716
     * images : ["https://pic4.zhimg.com/v2-a8579a51f37cd6d596f959d5c19e21db.jpg"]
     * type : 0
     * id : 9695994
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;
    private int type;
    private int id;
    private List<String> images;
    private List<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }
}
