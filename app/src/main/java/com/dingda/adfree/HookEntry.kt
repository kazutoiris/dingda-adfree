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

                    "com.ziytek.webapi.bizom.v1.RetGetAPPAds".toClass().apply {
                        method {
                            name = "getAdChannel"
                        }.hook {
                            before { result = "" }
                        }

                        method {
                            name = "getMedia1"
                        }.hook {
                            before { result = "" }
                        }
                    }

                    "com.ziytek.webapi.mt.v1.retSysInfo".toClass().method {
                        name = "getAppver"
                    }.hook {
                        before {
                            result = "ios:9999.9999.9999:0;android:9999.9999.9999:0"
                        }
                    }

                    "com.dingda.app.ui.activity.MainActivity".toClass().apply {
                        method {
                            name = "f0"
                        }.hook {
                            before {
                                args(0).setFalse()
                            }
                        }

                        method {
                            name = "x0"
                        }.hook {
                            before {
                                args(0).setFalse()
                            }
                        }

                        method {
                            name = "E0"
                        }.hook {
                            replaceUnit { }
                        }

                        method {
                            name = "m0"
                        }.hook {
                            replaceUnit { }
                        }
                    }

                    "com.dingda.app.ui.fragment.homefragment.HomeFragment".toClass().apply {
                        method {
                            name = "showCardTimeOutBanner"
                        }.hook {
                            replaceUnit { }
                        }

                        method {
                            name = "f2"
                        }.hook {
                            replaceUnit { }
                        }

                        method {
                            name = "Q2"
                        }.hook {
                            replaceUnit { }
                        }

                        method {
                            name = "O2"
                        }.hook {
                            replaceUnit { }
                        }
                    }
                }
            }.onAllFailure {
                it.localizedMessage?.let { it1 -> YLog.error(it1) }
            }
        }
    }
}
