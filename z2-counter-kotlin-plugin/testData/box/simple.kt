package foo.bar

import hu.simplexion.z2.counter.runtime.Counters

var manualCounter = 0L

fun stuff() {
    manualCounter ++
}

fun box(): String {
    for (i in 0..123) {
        stuff()
    }

    println(Counters)

    return if (Counters.counters["simple.kt:stuff:6:0"] == manualCounter) "OK" else "Fail"
}