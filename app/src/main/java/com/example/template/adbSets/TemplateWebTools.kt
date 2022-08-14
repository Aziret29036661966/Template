package com.example.template.adbSets

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.webkit.*
import com.example.template.MainActivity
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Obfuscate
object TemplateWebTools {
    suspend fun webInitStart(activity: Activity): WebViewClient = withContext(Dispatchers.Main) {
        val myTestClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                request?.url?.let { its ->
                    if (its.host != null) {
                        if ("localhost" == its.host!! &&
                            (its.path == null || its.path!!.length < 2)
                        ) {
                            activity.startActivity(
                                Intent(activity, MainActivity::class.java)
                            )
                            activity.finish()
                        }
                    }
                }
                return super.shouldInterceptRequest(view, request)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                if (url?.startsWith("https://t.me/joinchat") == true) {
                    activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
                return if (url == null ||
                    url.startsWith("http://") ||
                    url.startsWith("https://")
                )
                    false
                else
                    try {
                        if (url.startsWith("mailto")) {
                            activity.startActivity(
                                Intent.createChooser(
                                    Intent(Intent.ACTION_SEND).apply {
                                        type = "plain/text"
                                        putExtra(
                                            Intent.EXTRA_EMAIL,
                                            url.replace("mailto:", "")
                                        )
                                    },
                                    "Mail"
                                )
                            )
                        } else if (url.startsWith("tel:")) {
                            activity.startActivity(
                                Intent.createChooser(
                                    Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse(url)
                                    },
                                    "Call"
                                )
                            )
                        }
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        view!!.context.startActivity(intent)
                        true
                    } catch (e: Exception) {
                        true
                    }
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                CookieManager.getInstance().flush()
            }
        }

        return@withContext myTestClient
    }
}