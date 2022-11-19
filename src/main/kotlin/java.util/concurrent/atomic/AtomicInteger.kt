package java.util.concurrent.atomic

class AtomicInteger(var value: Int = 0) {

    fun incrementAndGet(): Int {
        synchronized(this){
            return ++value
        }
    }

    fun getAndIncrement(): Int {
        synchronized(this){
            return value++
        }
    }

    fun get() = value

}