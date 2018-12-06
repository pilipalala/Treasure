package com.wyj.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.mode.ItemInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;

public class BitmapActivity extends BaseActivity {

    @BindView(R.id.iv_before_compression)
    ImageView mIvBeforeCompression;
    @BindView(R.id.iv_after_compression)
    ImageView mIvAfterCompression;
    private File imageFile;
    private File sdFile;
    private Bitmap mBitmap;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_bitmap;
    }

    @Override
    protected List<ItemInfo> getListData() {
        return null;
    }

    /**
     * 1、资源文件(drawable/mipmap/raw):
     * R.mipmap.*
     * R.drawable.*
     * R.raw.*
     */
    public void decodeOne() {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ai_1);
    }

    /**
     * 2、资源文件(assets):
     */
    public void decodeTwo() {
        Bitmap bitmap = null;
        try {
            InputStream is = mContext.getAssets().open("bitmap.png");
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 内存卡文件:
     */
    public void decodeThree() {
        imageFile = new File(sdFile, "bitmap.png");
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.toString(), options);
        //或者用下面的方法
//        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/bitmap.png");
    }

    @Override
    protected void initData() {
        sdFile = getExternalCacheDir();

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ai_4);
        mIvBeforeCompression.setImageBitmap(mBitmap);
    }

    /**
     * 1、 质量压缩
     * 原理：通过算法抠掉图片中的 一些 比如 某些点附近相近的像素，达到降低质量减少文件大小的目的
     * 减小了图片质量
     * 注意：他其实只能实现对file的影响，对加载这个图片出来的bitmap内存是无法节省的 还是那么大
     * 因为bitmap在内存中的大小是按照像素计算的 也就是width*height，对于质量压缩，并不会改变图片的真实像素
     * <p>
     * 使用场景：将图片压缩保存到本地，或者将图片上传到服务器。
     *
     * @param bitmap
     * @param file
     */
    private void compressImageToFile(Bitmap bitmap, File file) {
        //0~100 100质量不变
        int quality = 50;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(stream.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 2、尺寸压缩
     * 原理：通过减少单位尺寸的像素值，真正意义上的降低像素
     * <p>
     * 使用场景：缓存缩略图的时候(头像)
     *
     * @param bitmap
     * @param file
     */
    public void compressImageToFileBySize(Bitmap bitmap, File file) {
        //压缩尺寸倍数  值越大 图片的尺寸就越小
        int ratio = 4;

        int width = bitmap.getWidth() / ratio;
        int height = bitmap.getHeight() / ratio;
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, width, height);
        canvas.drawBitmap(bitmap, null, rect, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        result.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void qualityCompression(View view) {
        compressImageToFile(mBitmap, new File(sdFile, "zhiliang.jpeg"));
    }

    public void sizeCompression(View view) {
        compressImageToFileBySize(mBitmap, new File(sdFile, "daxiao.jpeg"));
    }
}
