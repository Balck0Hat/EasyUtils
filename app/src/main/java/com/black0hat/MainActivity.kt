package com.black0hat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.black0hat.customutils.CheckInterface
import com.black0hat.customutils.MyUtils
import com.black0hat.customutils.myToast
import com.black0hat.customutils.remote.Operator
import com.black0hat.customutils.utils.CommonUtils
import com.black0hat.customutils.utils.DialogUtils
import com.black0hat.customutils.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ImageUtils.getImageFromUrl(this, imageView, "")

        customToast.setOnClickListener {
            MyUtils.checkEmail("my email", object : CheckInterface {
                override fun execute(pass: Boolean) {
                    myToast.Tost(
                        context = this@MainActivity,
                        text = "text",
                        length = 2,
                        drawable = R.drawable.ic_launcher_background
                    )
                }
            })
            textView.setBackgroundColor(ContextCompat.getColor(this, R.color.default_toast))
//            MyUtils.volleyRequest(this, "id", "roomName") { accessToken, roomName ->
//                myToast.toast(this@MainActivity, "accessToken -> $accessToken \nroomName -> $roomName", 2)
//            }

            DialogUtils.dialog(this, R.layout.activity_main) { dialog ->
                dialog.material_button.setOnClickListener {
                    myToast.Tost(
                        context = this@MainActivity,
                        text = "material_button",
                        length = 2,
                        drawable = R.drawable.ic_launcher_background
                    )
                    dialog.dismiss()
                }
            }
            Operator.getString(
                url = "https://fandango-lizard-8074.twil.io/emoje-token?identity=id&type=peer-to-peer&room=roomName",
                onSuccess = {
                    myToast.toast(this, "${CommonUtils.getCountryName("jo")}\n" + "it -> $it", 2)
                },
                onError = {
                    myToast.toast(this, "it -> $it", 2)
                })


        }
    }

}