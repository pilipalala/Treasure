package com.wyj.spannable;

import android.content.res.XmlResourceParser;
import android.os.Bundle;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.LogUtil;

import org.xmlpull.v1.XmlPullParser;

public class XmlResourceParserActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initView() {
        return R.layout.activity_xml_resource_parser;
    }

    @Override
    protected void initData() {
        logXmlData();
    }

    public void logXmlData() {
        XmlResourceParser xmlParser = getResources().getXml(R.xml.xml);
        try {
            int event = xmlParser.getEventType(); //先获取当前解析器光标在哪
            while ((event != XmlResourceParser.END_DOCUMENT)) {
                switch (event) {
                    case XmlResourceParser.START_DOCUMENT:
                        LogUtil.d("xml解析开始");
                        break;
                    case XmlResourceParser.START_TAG:
                        //一般都是获取标签的属性值，所以在这里数据你需要的数据
                        LogUtil.d("当前标签是：" + xmlParser.getName());
                        if (xmlParser.getName().equals("Node")) {
                            //两种方法获取属性值
                            LogUtil.d("第一个属性：" + xmlParser.getAttributeName(0)
                                    + ": " + xmlParser.getAttributeValue(0));
                            LogUtil.d("第二个属性：" + xmlParser.getAttributeName(1) + ": "
                                    + xmlParser.getAttributeValue(null, "att2"));
                        }
                        break;
                    case XmlPullParser.TEXT:
                        LogUtil.d( "Text:" + xmlParser.getText());
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                event = xmlParser.next();   //将当前解析器光标往下一步移
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
