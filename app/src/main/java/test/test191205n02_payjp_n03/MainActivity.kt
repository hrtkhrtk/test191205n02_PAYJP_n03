package test.test191205n02_payjp_n03

import android.app.DatePickerDialog
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
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T





class MainActivity : AppCompatActivity() {

    val mCardFormFragment = PayjpCardFormFragment.newInstance()
    var mUtc :Long? = null

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


        dateButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val cal = Calendar.getInstance() // 参考：http://www.jp-z.jp/changelog/2011-06-03-1.html
                    cal.set(year, month - 1, dayOfMonth, 23, 59, 59) // その日が終わるまで
                    mUtc = cal.timeInMillis
                    val dateString = year.toString() + "/" + String.format("%02d", month + 1) + "/" + String.format("%02d", dayOfMonth)
                    dateButton.text = dateString
                //}, mYear, mMonth, mDay)
                }, 0, 0, 0) // 仮置き
            datePickerDialog.show()
        }

        submitButton.setOnClickListener {v ->
            if (mUtc == null) {
                Snackbar.make(v, "日付を入力してください", Snackbar.LENGTH_LONG).show()
            } else {
                onClickSubmit()
            }
        }
    }

    fun onClickSubmit() {
        Log.d("test191205n01", "test191205n01")

        //if (!cardFormFragment.validateCardForm()) return
        if (!mCardFormFragment.validateCardForm()) return
        //cardFormFragment.createToken().enqueue(object : Task.Callback<Token> {
        mCardFormFragment.createToken().enqueue(object : Task.Callback<Token> {
            override fun onSuccess(data: Token) {
                Log.i("CardFormViewSample", "token => $data") // 不要
                Log.d("test191205n03", data.card.id)
                Log.d("test191205n04", data.id)

                Snackbar.make(findViewById(android.R.id.content), "test191206n02 token => $data", Snackbar.LENGTH_SHORT).show()

                // httpリクエストを入れる変数
                val builder = Uri.Builder()

                //val task = AsyncHttpRequest(this)
                //val task = AsyncHttpRequest(this@MainActivity)
                //val task = AsyncHttpRequest(this@MainActivity, data.id, 456.toLong(), 1576000000000.toLong(), "TCZazfEKrbUN8WMvl3j2qCEqsLI3")
                val task = AsyncHttpRequest(this@MainActivity, data.id, 456.toLong(), mUtc!!, "TCZazfEKrbUN8WMvl3j2qCEqsLI3")
                task.execute(builder)
            }

            override fun onError(throwable: Throwable) {
                Log.e("CardFormViewSample", "failure creating token", throwable)
            }
        })


        //Snackbar.make(findViewById(android.R.id.content), "test191206n01", Snackbar.LENGTH_SHORT).show()
    }
}
