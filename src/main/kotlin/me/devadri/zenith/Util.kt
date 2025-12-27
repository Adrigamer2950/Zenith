package me.devadri.zenith

fun <T> runAndReturnError(call: () -> T): ValueWithProbableError<T> {
    return try {
        ValueWithProbableError(call(), null)
    } catch (e: Throwable) {
        ValueWithProbableError(null, e)
    }
}

class ValueWithProbableError<T>(val value: T?, val error: Throwable?)