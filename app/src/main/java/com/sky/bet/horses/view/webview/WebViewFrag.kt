
package com.sky.bet.horses.view.webview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.google.android.material.snackbar.Snackbar
import com.sky.bet.horses.R

class WebViewFrag : Fragment() {

    val URL_ARG = "UrlArg"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_view, container, false)

        val url = arguments?.getString(URL_ARG)
        if(url != null){
            val webView = view.findViewById<WebView>(R.id.webview)
            webView.loadUrl(url)
        }else{
            Snackbar.make(view,getString(R.string.sorry_no_website),Snackbar.LENGTH_LONG).show()
        }

        return view
    }

    fun createBundle(url: String) : Bundle{
        val bundle = Bundle()
        bundle.putString(URL_ARG, url)
        return bundle
    }

}