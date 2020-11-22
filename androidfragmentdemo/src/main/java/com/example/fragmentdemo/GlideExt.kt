package com.example.fragmentdemo

import android.content.Context
import android.graphics.*
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.module.AppGlideModule
import java.security.MessageDigest

@GlideModule
class MyAppGlideModule : AppGlideModule()

class MyTransform(context: Context, private var radius: Int): BitmapTransformation(){
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return roundCrop(pool,toTransform)
    }
    private fun roundCrop(pool: BitmapPool, source: Bitmap): Bitmap {
        var result = pool.get(source.width, source.height, Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        }
        Thread.sleep(3000)
        //这时result只是一张指定了大小的空图，如果要求不高的话可以更改ARGB_8888以减少内存消耗
        val canvas = Canvas(result)
        val paint = Paint()
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rectF, radius.toFloat(), radius.toFloat(), paint)
        //操作其实很简单就是画了一个圆角矩形
        return result
    }
}
