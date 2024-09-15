package com.dingda.adfree

import android.content.Context
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.factory.field
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit


@InjectYukiHookWithXposed
object HookEntry : IYukiHookXposedInit {
    override fun onInit() = configs {
        isDebug = true
    }

    override fun onHook() = encase {
        loadApp("com.dingda.app") {
            "android.app.ActivityThread".toClass().method {
                name = "performLaunchActivity"
            }.hook {
                before {
                    val app = instanceClass?.field {
                        name = "mInitialApplication"
                    }?.get(instance)?.cast<Context>()

                    appClassLoader =
                        app?.javaClass?.method {
                            name = "getClassLoader"
                        }?.get(app)?.invoke() as ClassLoader?

                    "com.ziytek.webapi.bizom.v1.RetGetAPPAds".toClass().method {
                        name = "getAdChannel"
                    }.hook {
                        before { result = "" }
                    }

                    "com.ziytek.webapi.bizom.v1.RetGetAPPAds".toClass().method {
                        name = "getMedia1"
                    }.hook {
                        before { result = "" }
                    }

                    "com.dingda.app.ui.activity.MainActivity".toClass().method {
                        name = "D"
                    }.hook {
                        before {
                            args(0).setFalse()
                        }
                    }

                    "com.dingda.app.ui.activity.MainActivity".toClass().method {
                        name = "V"
                    }.hook {
                        before {
                            args(0).setFalse()
                        }
                    }

                    "com.dingda.app.ui.activity.MainActivity".toClass().method {
                        name = "H"
                    }.hook {
                        replaceUnit { }
                    }

                    "com.dingda.app.ui.fragment.homefragment.HomeFragment".toClass().method {
                        name = "b2"
                    }.hook {
                        replaceUnit { }
                    }

                    "com.dingda.app.ui.fragment.homefragment.HomeFragment".toClass().method {
                        name = "showCardTimeOutBanner"
                    }.hook {
                        replaceUnit { }
                    }

                    "com.dingda.app.ui.fragment.homefragment.HomeFragment".toClass().method {
                        name = "J2"
                    }.hook {
                        replaceUnit { }
                    }
                }
            }.onAllFailure {
                it.localizedMessage?.let { it1 -> YLog.error(it1) }
            }
        }
    }
}
