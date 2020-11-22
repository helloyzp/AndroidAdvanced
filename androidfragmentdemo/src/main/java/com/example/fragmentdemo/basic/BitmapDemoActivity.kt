package com.example.fragmentdemo.basic

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.fragmentdemo.MyTransform
import com.example.fragmentdemo.ProgressDialog
import com.example.fragmentdemo.R
import org.jetbrains.anko.find

class BitmapDemoActivity: AppCompatActivity() {

    private lateinit var image: ImageView

    private  var bitmap: Bitmap? = null

    private  var bitmapFragment: BitmapFragment? = null

    private val progressDialog = ProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitmap)
        Log.i("Zero","${this::class.simpleName} onCreate")
        image = find(R.id.image)

        bitmapFragment = supportFragmentManager.findFragmentByTag("data") as? BitmapFragment
        if(bitmapFragment == null){
            bitmapFragment = BitmapFragment()
            supportFragmentManager.commit {
                add(bitmapFragment!!,"data")
            }
        }
        bitmap = bitmapFragment!!.data

        initData()

    }

    fun initData(){
        if(bitmap == null){
            Glide.with(this)
                    .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581860279772&di=45778adb4c9ba8ff793e93f1fe86961e&imgtype=0&src=http%3A%2F%2Fpic.4j4j.cn%2Fupload%2Fpic%2F20130627%2F9f48497805.jpg")
                    .transform(MyTransform(this,150))
                    .into(object: CustomViewTarget<ImageView, Drawable>(image){
                        override fun onStart() {
                            super.onStart()
                            progressDialog.show(supportFragmentManager,"")
                        }


                        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                            Log.i("Zero","onResourceReady $resource")
                            bitmapFragment!!.data = Bitmap.createBitmap(resource.toBitmap())
                            image.setImageDrawable(resource)
                            progressDialog.dismiss()
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                        }

                        override fun onResourceCleared(placeholder: Drawable?) {
                        }
                    })
        }else{
            image.setImageBitmap(bitmap)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i("Zero","${this::class.simpleName} onConfigurationChanged")
    }


}