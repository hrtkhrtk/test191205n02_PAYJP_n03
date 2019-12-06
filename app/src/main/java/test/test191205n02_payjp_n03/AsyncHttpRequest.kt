package test.test191205n02_payjp_n03

import android.app.Activity
import android.net.Uri
import android.os.AsyncTask
import android.os.AsyncTask.execute
import android.util.Log
import android.widget.TextView
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.OkHttpClient

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Request
//import java.util.*

import kotlinx.android.synthetic.main.activity_main.*

//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import java.util.*

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class AsyncHttpRequest(private val mainActivity: Activity, private val token: String, private val price: Long, private val purchaseTo: Long, private val currentUID: String)// 呼び出し元のアクティビティ
    : AsyncTask<Uri.Builder, Void, String>() {

    // このメソッドは必ずオーバーライドする必要があるよ
    // ここが非同期で処理される部分みたいたぶん。
    override fun doInBackground(vararg builder: Uri.Builder): String {
        // httpリクエスト投げる処理を書く。
        val client = OkHttpClient()
        val MIMEType = MediaType.parse("application/json; charset=utf-8")
        val dataToSend_Map = HashMap<String, String>()
        //dataToSend_Map["payjp-token"] = 123.toString()
        //dataToSend_Map["price"] = 456.toString()
        //dataToSend_Map["purchaseTo"] = 1576000000000.toString()
        //dataToSend_Map["currentUID"] = "TCZazfEKrbUN8WMvl3j2qCEqsLI3"
        dataToSend_Map["payjp-token"] = token
        dataToSend_Map["price"] = price.toString()
        dataToSend_Map["purchaseTo"] = purchaseTo.toString()
        dataToSend_Map["currentUID"] = currentUID

        val mapper = ObjectMapper() // 参考：https://qiita.com/opengl-8080/items/b613b9b3bc5d796c840c
        val dataToSend_json = mapper.writeValueAsString(dataToSend_Map)

        //Log.d("test191204n01", dataToSend_json)

        val url = "https://twitterclone-api-ver01.herokuapp.com/payment"

        val requestBody = RequestBody.create(MIMEType, dataToSend_json)
        val request = Request.Builder().url(url).post(requestBody).build()
        val response = client.newCall(request).execute()

        //return (response as String)
        //return ""
        return response.body()!!.string()
        //return response.body()!! as Map<String, String>
    }


    // このメソッドは非同期処理の終わった後に呼び出されます
    override fun onPostExecute(result: String) {
    //override fun onPostExecute(result: Map<String, String>) {
        // 取得した結果をテキストビューに入れちゃったり
        //TextView tv = (TextView) mainActivity.findViewById(R.id.name);
        //tv.setText(result)

        //Log.d("test191206n05", result)
        val mapper = jacksonObjectMapper()
        val result_Map = mapper.readValue<Map<String, String>>(result)

        Log.d("test191206n10", result_Map["status"])

        //val result_Map = result as Map<String, String>
        //val result_Map = result as Map<String, Any>
        //statusText.text = result_Map["status"]
        val statusText = mainActivity.findViewById<TextView>(R.id.statusText)
        statusText.text = result_Map["status"]
    }
}
