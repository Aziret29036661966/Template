package com.example.template.adbSets

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.facebook.applinks.AppLinkData
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.*
import java.util.*

@Obfuscate
class TemplateDeepTools {
    private val job = SupervisorJob()
    private val ioScope by lazy { CoroutineScope(job + Dispatchers.Main) }

    private var _testLiveData = MutableLiveData<List<Array<ArrayList<List<String>>>>>()
    val test = _testLiveData

    private var myFin: String? = ""

    fun engine(ACTIVITY: Activity) {
        ioScope.launch {
            checkEngineParts(ACTIVITY, ::getApp, ::getNull, ::getTrue)
        }
    }

    private suspend fun checkEngineParts(act: Activity, myapp: () -> String, returnNull: () -> Any?, returnTrue: () -> Boolean) {
        AppLinkData.fetchDeferredAppLinkData(act) { paramAnonymousAppLinkData ->
            if (paramAnonymousAppLinkData != returnNull()) {
                try {
                    myFin = Objects.requireNonNull(paramAnonymousAppLinkData!!.targetUri)
                        .toString()
                        .replace(myapp(), "")
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
                if (!myFin.equals("", ignoreCase = returnTrue())) {
                    myFin?.let {
                        Log.d("testDeep", it)
                        ioScope.launch {
                            withContext(Dispatchers.Main) {
                                _testLiveData.value = listOf(arrayOf(arrayListOf(listOf(it))))
                                engine(act)
                            }
                        }

                    }
                }
            } else {
                if (AppLinkData.createFromActivity(act) != returnNull()) {
                    try {
                        myFin =
                            Objects.requireNonNull(AppLinkData.createFromActivity(act)!!.targetUri)
                                .toString()
                                .replace(myapp(), "")
                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                    }
                    if (!myFin.equals("", ignoreCase = returnTrue())) {
                        myFin?.let {
                            Log.d("testDeep", it)
                            ioScope.launch {
                                withContext(Dispatchers.Main) {
                                    _testLiveData.value = listOf(arrayOf(arrayListOf(listOf(it))))
                                    engine(act)
                                }
                            }
                        }
                    }
                } else {
                    funcNothing(act)
                }
            }
        }
    }

    private fun funcNothing(act: Activity) {
        Log.d("testDeep", "nothing")
        ioScope.launch {
            withContext(Dispatchers.Main) {
                _testLiveData.value = listOf(arrayOf(arrayListOf(listOf(""))))
                engine(act)
            }
        }
    }
}