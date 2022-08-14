package com.example.template.adbSets

import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
object TemplateWebSet {

    fun mainWebSet(listResult: List<Array<WebView>>, listBuls: List<Boolean>, listBulsSecondary: List<Boolean>, listNums: List<Int>, listStrokes: List<String>) {
        firstPartOfSet(listResult, listBuls)
        listResult[0][0].settings.saveFormData = listBuls[0]
        listResult[0][0].settings.databaseEnabled = listBuls[0]
        secondPartOfSet(listResult, listBuls, listBulsSecondary, listStrokes)
        val instance = CookieManager.getInstance()
        instance.setAcceptCookie(listBuls[0])
        listResult[0][0].isFocusable = listBuls[0]
        listResult[0][0].isFocusableInTouchMode = listBuls[0]
        listResult[0][0].isSaveEnabled = listBuls[0]
        CookieManager.getInstance().setAcceptThirdPartyCookies(listResult[0][0], listBuls[0])
        listResult[0][0].isSaveEnabled = listBuls[0]
        listResult[0][0].settings.cacheMode = WebSettings.LOAD_DEFAULT
        listResult[0][0].settings.mixedContentMode = listNums[0]
        thirdPartOfSet(listResult, listBuls)
    }

    private fun thirdPartOfSet(list: List<Array<WebView>>, YUMMY: List<Boolean>) {
        list[0][0].settings.allowFileAccess = YUMMY[0]
        list[0][0].settings.allowFileAccessFromFileURLs = YUMMY[0]
        list[0][0].settings.allowUniversalAccessFromFileURLs = YUMMY[0]
        list[0][0].settings.useWideViewPort = YUMMY[0]
        list[0][0].settings.loadWithOverviewMode = YUMMY[0]
    }

    private fun secondPartOfSet(
        list: List<Array<WebView>>,
        YUMMY: List<Boolean>,
        DUMMY: List<Boolean>,
        ShIT: List<String>
    ) {
        list[0][0].settings.setSupportZoom(DUMMY[0])
        list[0][0].settings.javaScriptCanOpenWindowsAutomatically = YUMMY[0]
        list[0][0].settings.loadsImagesAutomatically = YUMMY[0]
        list[0][0].settings.userAgentString = list[0][0].settings.userAgentString.replace(ShIT[0], "")
    }

    private fun firstPartOfSet(list: List<Array<WebView>>, YUMMY: List<Boolean>) {
        list[0][0].settings.pluginState = WebSettings.PluginState.ON
        list[0][0].settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        list[0][0].settings.allowContentAccess = YUMMY[0]
        list[0][0].settings.setEnableSmoothTransition(YUMMY[0])
        list[0][0].settings.setAppCacheEnabled(YUMMY[0])
        list[0][0].settings.domStorageEnabled = YUMMY[0]
        list[0][0].settings.javaScriptEnabled = YUMMY[0]
        list[0][0].settings.savePassword = YUMMY[0]
    }
}