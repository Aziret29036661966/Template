package com.example.template.adbSets

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.gson.Gson
import eu.amirs.JSON
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.*
import java.util.ArrayList
import kotlin.coroutines.CoroutineContext

@Obfuscate
class TemplateAf(private val act: Activity) : AppsFlyerConversionListener, CoroutineScope {
    private val job = SupervisorJob()
    private val ioScope by lazy { CoroutineScope(job + Dispatchers.Main) }

    private var _testLiveData = MutableLiveData<List<Array<ArrayList<List<String>>>>>()
    val test = _testLiveData

    private var _adId = MutableLiveData<List<Array<ArrayList<List<String>>>>>()
    val adId = _adId

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onConversionDataSuccess(mapResult: MutableMap<String, Any>?) {
        if (ASMR.isEmpty()) {
            ASMR = "ASMR"
            ioScope.launch {
                coroutineScope {
                    withContext(Dispatchers.IO) {
                        var AIC: AdvertisingIdClient.Info? = null
                        AIC = AdvertisingIdClient.getAdvertisingIdInfo(act)
                        val ad = AIC.id.toString()
                        val mrDev = AppsFlyerLib.getInstance().getAppsFlyerUID(act)

                        Log.d("testAf", mapResult.toString())
                        Log.d("testAf", ad)
                        withContext(Dispatchers.Main) {
                            try {
                                _adId.value = listOf(arrayOf(arrayListOf(listOf(ad, mrDev))))
                                val gson = kwdmawkdmakwdm()
                                val miniJsonForm = gson.toJson(mapResult)
                                val miniApps: JSON = JSON(miniJsonForm)
                                val firstResult = if(miniApps.jsonObject.has("campaign")) miniApps.jsonObject["campaign"].toString() else ""
                                val secondResult = if(miniApps.jsonObject.has("media_source")) miniApps.jsonObject["media_source"].toString() else ""
                                if (firstResult.isEmpty()){
                                    _testLiveData.value = listOf(arrayOf(arrayListOf(listOf("", ""))))
                                } else {
                                    _testLiveData.value = listOf(
                                        arrayOf(
                                            arrayListOf(
                                                listOf(
                                                    firstResult,
                                                    secondResult
                                                )
                                            )
                                        )
                                    )
                                }

                            } catch (e: Exception) {
                                Log.d("testOlolo", e.message.toString())
                                e.printStackTrace()
                            }
                        }

                    }
                }
            }
        }
    }

    override fun onConversionDataFail(p0: String?) {
        if (ASMRSECOND.isEmpty()) {
            ASMRSECOND = "ASMRFAIL"
            ioScope.launch {
                coroutineScope {
                    withContext(Dispatchers.IO) {
                        var missAdd: AdvertisingIdClient.Info? = null
                        kotlin.runCatching {
                            missAdd = AdvertisingIdClient.getAdvertisingIdInfo(act)
                        }
                        val myId = missAdd?.id.toString()
                        val aflb = AppsFlyerLib.getInstance().getAppsFlyerUID(act)
                        try {
                            _adId.value = listOf(arrayOf(arrayListOf(listOf(myId, aflb))))
                            _testLiveData.value = listOf(arrayOf(arrayListOf(listOf(""))))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

    }

    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
    override fun onAttributionFailure(p0: String?) {}

    private fun kwdmawkdmakwdm() : Gson = Gson()


    companion object {
        private var ASMR = ""
        private var ASMRSECOND = ""
    }
}