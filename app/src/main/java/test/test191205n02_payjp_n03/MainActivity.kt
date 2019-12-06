package test.test191205n02_payjp_n03

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.util.*
import jp.pay.android.Payjp
import jp.pay.android.PayjpConfiguration
import jp.pay.android.Task
import jp.pay.android.model.CardBrand
import jp.pay.android.model.Token
import jp.pay.android.ui.widget.PayjpCardFormFragment
import jp.pay.android.ui.widget.PayjpCardFormView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mCardFormFragment = PayjpCardFormFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // または Payjp.init("pk_test_0383a1b8f91e8a6e3ea0e2a9")
        Payjp.init(
            PayjpConfiguration.Builder("pk_test_0ca997049dcd98ddfbc2f04a")
            .setDebugEnabled(BuildConfig.DEBUG)
            .setLocale(Locale.JAPAN)
            .build())

        //cardFormFragment = PayjpCardFormFragment.newInstance()
        //val cardFormFragment = PayjpCardFormFragment.newInstance()
        supportFragmentManager.beginTransaction()
            //.replace(R.id.card_form_view, cardFormFragment, TAG_CARD_FORM)
            //.replace(R.id.card_form_view, mCardFormFragment as Fragment, TAG_CARD_FORM)
            .replace(R.id.card_form_view, mCardFormFragment as Fragment, "TAG_CARD_FORM")
            .commit()
        submitButton.setOnClickListener {
            onClickSubmit()
        }
    }

    fun onClickSubmit() {
        Log.d("test191205n01", "test191205n01")

        //if (!cardFormFragment.validateCardForm()) return
        if (!mCardFormFragment.validateCardForm()) return
        //cardFormFragment.createToken().enqueue(object : Task.Callback<Token> {
        mCardFormFragment.createToken().enqueue(object : Task.Callback<Token> {
            override fun onSuccess(data: Token) {
                Log.i("CardFormViewSample", "token => $data")

                Snackbar.make(findViewById(android.R.id.content), "test191206n02 token => $data", Snackbar.LENGTH_SHORT).show()

                // httpリクエストを入れる変数
                val builder = Uri.Builder()

                //val task = AsyncHttpRequest(this)
                val task = AsyncHttpRequest(this@MainActivity)
                task.execute(builder)
            }

            override fun onError(throwable: Throwable) {
                Log.e("CardFormViewSample", "failure creating token", throwable)
            }
        })


        //Snackbar.make(findViewById(android.R.id.content), "test191206n01", Snackbar.LENGTH_SHORT).show()
    }
}
