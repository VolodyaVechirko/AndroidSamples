package com.example.webtest

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import java.io.BufferedInputStream

/**
 * Sample of WebView - Java code communication.
 * 1. Pass url params with [WebView.loadUrl] call.
 * 2. Pass data via [WebView.evaluateJavascript].
 * 3. Get web data via [WebView.evaluateJavascript].
 * 4. Add js functions, callback via [WebView.evaluateJavascript].
 * 5. Receive callbacks via [AndroidJsBridge].
 */
class WebViewActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = webView()
        setContentView(webView)

        // load url with params
        val localPath = "file:///android_asset/test.html?param1=ok&param2=encrypted"
        webView.loadUrl(localPath)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webView() = WebView(this).apply {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        webChromeClient = webChromeClient()
        webViewClient = webViewClient()

        addJavascriptInterface(JavaScriptInterface(context), "AndroidJsBridge")
    }

    private fun webChromeClient() = object : WebChromeClient() {
        override fun onJsAlert(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            return super.onJsAlert(view, url, message, result)
        }
    }

    private fun webViewClient() = object : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            log("onPageStarted $url")
        }

        override fun onPageFinished(view: WebView, url: String) {
            log("onPageFinished $url")
            // execute js function with params
            val param = 121
            view.loadUrl("javascript:setDataMsg('$param')")

            // execute js script with a result
            val js = "javascript:document.getElementById('initData').innerHTML"
            view.evaluateJavascript(js) { value: Any ->
                log("initData callback $value")
            }

            // execute js func with a result
            val p = 3
            view.evaluateJavascript("javascript:square('$p')") { value: Any ->
                log("square callback $value")
            }

            // button click event listener
            val script = "document.getElementById('b1').addEventListener('click', function() {" +
                    "document.getElementById('b1').innerHTML = 'Toast shown'; " +
                    "window.AndroidJsBridge.log('Button clicked'); " +
                    "});"
            view.evaluateJavascript(script) {

            }
        }
    }

    // provide js bridge to call android
    class JavaScriptInterface(private val context: Context) {
        @JavascriptInterface
        fun showToast(t: String?) {
            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
        }

        @JavascriptInterface
        fun log(t: String?) {
            Log.d("JsBridge", t.toString())
        }
    }

    private fun WebView.loadFile(filename: String) {
        val inputStream = assets.open(filename)
        val buffer = BufferedInputStream(inputStream)
        val bytes = buffer.readBytes()
        val content = String(bytes)
        buffer.close()
        loadData(content, "text/html", "utf-8")
    }

    private fun log(t: Any?) = Log.d("WebViewTest", t.toString())
}
