import hu.simplexion.z2.counter.runtime.Counters

fun main() {
    box()
}

fun stuff() {
    // very sophisticated and very important code
}

fun box() {
    for (i in 0..123) {
        stuff()
        stuff2()
    }
    println(Counters)
}