package com.maheswara660.vidora.core.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val niaDispatcher: VidoraDispatchers)

enum class VidoraDispatchers {
    Default,
    IO,
}
