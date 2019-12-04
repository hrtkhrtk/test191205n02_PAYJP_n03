package test.test191205n02_payjp_n03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
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






    }
}
