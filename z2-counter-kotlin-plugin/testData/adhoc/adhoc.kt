package foo.bar

import hu.simplexion.z2.counter.runtime.Counters

fun stuff() {
    // very sophisticated and very important code
}

fun box(): String {
    for (i in 0..123) {
        stuff()
    }
    println(Counters)
    return "OK"
}
