package com.example.webtest

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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

class WebViewActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.getWindowInsetsController(window.decorView)?.let {
            it.isAppearanceLightNavigationBars = true
            it.isAppearanceLightStatusBars = true
        }

        val insets = ViewCompat.getRootWindowInsets(window.decorView) ?: return
        val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
        val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

        setContentView(webView())
    }

    private fun webView() = WebView(this).apply {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        webChromeClient = WebChromeClient()
        webViewClient = webViewClient()

        addJavascriptInterface(JavaScriptInterface(context), "AndroidJsBridge")

        // load url with params
        loadUrl("file:///android_asset/test.html?param1=ok&param2=encrypted")
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
                    "document.getElementById('b1').innerHTML = 'Hacker'; " +
                    "window.AndroidJsBridge.log('Button clicked'); " +
                    "});"
            view.evaluateJavascript(script) {

            }
        }
    }

    // provide js bridge to call android
    class JavaScriptInterface(val context: Context) {
        @JavascriptInterface
        fun showToast(t: String?) {
            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
        }

        @JavascriptInterface
        fun log(t: String?) {
            Log.d("JsBridge", t.toString())
        }
    }

    private fun log(t: Any?) = Log.d("WebViewTest", t.toString())
}
