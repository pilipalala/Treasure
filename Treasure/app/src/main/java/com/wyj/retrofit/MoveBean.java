package com.wyj.retrofit;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2018/3/11
 * Time 17:31
 * TODO
 */
//http://api.douban.com/v2/movie/top250?start=0&count=10
public class MoveBean {

    /**
     * count : 10
     * start : 0
     * total : 250
     * subjects : [{"rating":{"max":10,"average":9.6,"stars":"50","min":0},"genres":["犯罪","剧情"],"title":"肖申克的救赎","casts":[{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.webp"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.webp"},"name":"鲍勃·冈顿","id":"1041179"}],"collect_count":1239578,"original_title":"The Shawshank Redemption","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp"},"name":"弗兰克·德拉邦特","id":"1047973"}],"year":"1994","images":{"small":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp","large":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp","medium":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp"},"alt":"https://movie.douban.com/subject/1292052/","id":"1292052"},{"rating":{"max":10,"average":9.5,"stars":"50","min":0},"genres":["剧情","爱情","同性"],"title":"霸王别姬","casts":[{"alt":"https://movie.douban.com/celebrity/1003494/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p67.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p67.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p67.webp"},"name":"张国荣","id":"1003494"},{"alt":"https://movie.douban.com/celebrity/1050265/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p46345.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p46345.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p46345.webp"},"name":"张丰毅","id":"1050265"},{"alt":"https://movie.douban.com/celebrity/1035641/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1399268395.47.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1399268395.47.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1399268395.47.webp"},"name":"巩俐","id":"1035641"}],"collect_count":893492,"original_title":"霸王别姬","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1023040/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1451727734.81.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1451727734.81.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1451727734.81.webp"},"name":"陈凯歌","id":"1023040"}],"year":"1993","images":{"small":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p1910813120.webp","large":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p1910813120.webp","medium":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p1910813120.webp"},"alt":"https://movie.douban.com/subject/1291546/","id":"1291546"},{"rating":{"max":10,"average":9.4,"stars":"50","min":0},"genres":["剧情","动作","犯罪"],"title":"这个杀手不太冷","casts":[{"alt":"https://movie.douban.com/celebrity/1025182/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p8833.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p8833.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p8833.webp"},"name":"让·雷诺","id":"1025182"},{"alt":"https://movie.douban.com/celebrity/1054454/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p2274.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p2274.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p2274.webp"},"name":"娜塔莉·波特曼","id":"1054454"},{"alt":"https://movie.douban.com/celebrity/1010507/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33896.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33896.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33896.webp"},"name":"加里·奥德曼","id":"1010507"}],"collect_count":1181848,"original_title":"Léon","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1031876/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33301.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33301.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33301.webp"},"name":"吕克·贝松","id":"1031876"}],"year":"1994","images":{"small":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p511118051.webp","large":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p511118051.webp","medium":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p511118051.webp"},"alt":"https://movie.douban.com/subject/1295644/","id":"1295644"},{"rating":{"max":10,"average":9.4,"stars":"50","min":0},"genres":["剧情","爱情"],"title":"阿甘正传","casts":[{"alt":"https://movie.douban.com/celebrity/1054450/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p28603.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p28603.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p28603.webp"},"name":"汤姆·汉克斯","id":"1054450"},{"alt":"https://movie.douban.com/celebrity/1002676/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p51737.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p51737.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p51737.webp"},"name":"罗宾·怀特","id":"1002676"},{"alt":"https://movie.douban.com/celebrity/1031848/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1345.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1345.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1345.webp"},"name":"加里·西尼斯","id":"1031848"}],"collect_count":1040708,"original_title":"Forrest Gump","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1053564/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p505.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p505.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p505.webp"},"name":"罗伯特·泽米吉斯","id":"1053564"}],"year":"1994","images":{"small":"http://img3.doubanio.com/view/photo/s_ratio_poster/public/p510876377.webp","large":"http://img3.doubanio.com/view/photo/s_ratio_poster/public/p510876377.webp","medium":"http://img3.doubanio.com/view/photo/s_ratio_poster/public/p510876377.webp"},"alt":"https://movie.douban.com/subject/1292720/","id":"1292720"},{"rating":{"max":10,"average":9.5,"stars":"50","min":0},"genres":["剧情","喜剧","爱情"],"title":"美丽人生","casts":[{"alt":"https://movie.douban.com/celebrity/1041004/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p26764.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p26764.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p26764.webp"},"name":"罗伯托·贝尼尼","id":"1041004"},{"alt":"https://movie.douban.com/celebrity/1000375/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p9548.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p9548.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p9548.webp"},"name":"尼可莱塔·布拉斯基","id":"1000375"},{"alt":"https://movie.douban.com/celebrity/1000368/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p45590.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p45590.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p45590.webp"},"name":"乔治·坎塔里尼","id":"1000368"}],"collect_count":582629,"original_title":"La vita è bella","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1041004/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p26764.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p26764.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p26764.webp"},"name":"罗伯托·贝尼尼","id":"1041004"}],"year":"1997","images":{"small":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p510861873.webp","large":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p510861873.webp","medium":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p510861873.webp"},"alt":"https://movie.douban.com/subject/1292063/","id":"1292063"},{"rating":{"max":10,"average":9.2,"stars":"50","min":0},"genres":["剧情","动画","奇幻"],"title":"千与千寻","casts":[{"alt":"https://movie.douban.com/celebrity/1023337/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1463193210.13.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1463193210.13.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1463193210.13.webp"},"name":"柊瑠美","id":"1023337"},{"alt":"https://movie.douban.com/celebrity/1005438/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p44986.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p44986.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p44986.webp"},"name":"入野自由","id":"1005438"},{"alt":"https://movie.douban.com/celebrity/1045797/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p18785.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p18785.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p18785.webp"},"name":"夏木真理","id":"1045797"}],"collect_count":942809,"original_title":"千と千尋の神隠し","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1054439/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p616.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p616.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p616.webp"},"name":"宫崎骏","id":"1054439"}],"year":"2001","images":{"small":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p1606727862.webp","large":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p1606727862.webp","medium":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p1606727862.webp"},"alt":"https://movie.douban.com/subject/1291561/","id":"1291561"},{"rating":{"max":10,"average":9.2,"stars":"50","min":0},"genres":["剧情","爱情","灾难"],"title":"泰坦尼克号","casts":[{"alt":"https://movie.douban.com/celebrity/1041029/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p814.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p814.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p814.webp"},"name":"莱昂纳多·迪卡普里奥","id":"1041029"},{"alt":"https://movie.douban.com/celebrity/1054446/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p53358.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p53358.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p53358.webp"},"name":"凯特·温丝莱特","id":"1054446"},{"alt":"https://movie.douban.com/celebrity/1031864/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p45186.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p45186.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p45186.webp"},"name":"比利·赞恩","id":"1031864"}],"collect_count":961182,"original_title":"Titanic","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1022571/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33715.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33715.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33715.webp"},"name":"詹姆斯·卡梅隆","id":"1022571"}],"year":"1997","images":{"small":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p457760035.webp","large":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p457760035.webp","medium":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p457760035.webp"},"alt":"https://movie.douban.com/subject/1292722/","id":"1292722"},{"rating":{"max":10,"average":9.4,"stars":"50","min":0},"genres":["剧情","历史","战争"],"title":"辛德勒的名单","casts":[{"alt":"https://movie.douban.com/celebrity/1031220/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p44906.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p44906.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p44906.webp"},"name":"连姆·尼森","id":"1031220"},{"alt":"https://movie.douban.com/celebrity/1054393/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1374649659.58.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1374649659.58.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1374649659.58.webp"},"name":"本·金斯利","id":"1054393"},{"alt":"https://movie.douban.com/celebrity/1006956/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p28941.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p28941.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p28941.webp"},"name":"拉尔夫·费因斯","id":"1006956"}],"collect_count":548571,"original_title":"Schindler's List","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1054440/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p617.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p617.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p617.webp"},"name":"史蒂文·斯皮尔伯格","id":"1054440"}],"year":"1993","images":{"small":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p492406163.webp","large":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p492406163.webp","medium":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p492406163.webp"},"alt":"https://movie.douban.com/subject/1295124/","id":"1295124"},{"rating":{"max":10,"average":9.3,"stars":"50","min":0},"genres":["剧情","科幻","悬疑"],"title":"盗梦空间","casts":[{"alt":"https://movie.douban.com/celebrity/1041029/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p814.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p814.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p814.webp"},"name":"莱昂纳多·迪卡普里奥","id":"1041029"},{"alt":"https://movie.douban.com/celebrity/1101703/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3517.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3517.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p3517.webp"},"name":"约瑟夫·高登-莱维特","id":"1101703"},{"alt":"https://movie.douban.com/celebrity/1012520/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p118.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p118.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p118.webp"},"name":"艾伦·佩吉","id":"1012520"}],"collect_count":1034483,"original_title":"Inception","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1054524/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p673.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p673.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p673.webp"},"name":"克里斯托弗·诺兰","id":"1054524"}],"year":"2010","images":{"small":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p513344864.webp","large":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p513344864.webp","medium":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p513344864.webp"},"alt":"https://movie.douban.com/subject/3541415/","id":"3541415"},{"rating":{"max":10,"average":9.3,"stars":"50","min":0},"genres":["爱情","科幻","动画"],"title":"机器人总动员","casts":[{"alt":"https://movie.douban.com/celebrity/1009535/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p13028.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p13028.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p13028.webp"},"name":"本·贝尔特","id":"1009535"},{"alt":"https://movie.douban.com/celebrity/1000389/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1519794715.93.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1519794715.93.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1519794715.93.webp"},"name":"艾丽莎·奈特","id":"1000389"},{"alt":"https://movie.douban.com/celebrity/1018022/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p31068.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p31068.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p31068.webp"},"name":"杰夫·格尔林","id":"1018022"}],"collect_count":671347,"original_title":"WALL·E","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1036450/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1467359656.96.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1467359656.96.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1467359656.96.webp"},"name":"安德鲁·斯坦顿","id":"1036450"}],"year":"2008","images":{"small":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p1461851991.webp","large":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p1461851991.webp","medium":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p1461851991.webp"},"alt":"https://movie.douban.com/subject/2131459/","id":"2131459"}]
     * title : 豆瓣电影Top250
     */

    public int count;
    public int start;
    public int total;
    public String title;
    public List<SubjectsBean> subjects;

    public static class SubjectsBean {
        /**
         * rating : {"max":10,"average":9.6,"stars":"50","min":0}
         * genres : ["犯罪","剧情"]
         * title : 肖申克的救赎
         * casts : [{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.webp"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.webp"},"name":"鲍勃·冈顿","id":"1041179"}]
         * collect_count : 1239578
         * original_title : The Shawshank Redemption
         * subtype : movie
         * directors : [{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp"},"name":"弗兰克·德拉邦特","id":"1047973"}]
         * year : 1994
         * images : {"small":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp","large":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp","medium":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp"}
         * alt : https://movie.douban.com/subject/1292052/
         * id : 1292052
         */

        public RatingBean rating;
        public String title;
        public int collect_count;
        public String original_title;
        public String subtype;
        public String year;
        public ImagesBean images;
        public String alt;
        public String id;
        public List<String> genres;
        public List<CastsBean> casts;
        public List<DirectorsBean> directors;

        public static class RatingBean {
            /**
             * max : 10
             * average : 9.6
             * stars : 50
             * min : 0
             */

            public int max;
            public double average;
            public String stars;
            public int min;
        }

        public static class ImagesBean {
            /**
             * small : http://img7.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp
             * large : http://img7.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp
             * medium : http://img7.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp
             */

            public String small;
            public String large;
            public String medium;
        }

        public static class CastsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1054521/
             * avatars : {"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp"}
             * name : 蒂姆·罗宾斯
             * id : 1054521
             */

            public String alt;
            public AvatarsBean avatars;
            public String name;
            public String id;

            public static class AvatarsBean {
                /**
                 * small : http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp
                 * large : http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp
                 * medium : http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp
                 */

                public String small;
                public String large;
                public String medium;
            }
        }

        public static class DirectorsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1047973/
             * avatars : {"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp"}
             * name : 弗兰克·德拉邦特
             * id : 1047973
             */

            public String alt;
            public AvatarsBeanX avatars;
            public String name;
            public String id;

            public static class AvatarsBeanX {
                /**
                 * small : http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp
                 * large : http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp
                 * medium : http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp
                 */

                public String small;
                public String large;
                public String medium;
            }
        }
    }
}
