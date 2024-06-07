package com.example.letschat_hibro.domain.di

import com.example.letschat_hibro.data.repos.ChatRepo
import com.example.letschat_hibro.data.repos.SocketRepo
import com.example.letschat_hibro.domain.repoImpl.ChatRepoImpl
import com.example.letschat_hibro.domain.repoImpl.SocketsRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets){
                pingInterval = 15_000 // 15 seconds
                maxFrameSize = Long.MAX_VALUE
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    @Provides
    @Singleton
    fun providePostsRepo(client: HttpClient): ChatRepo {
        return ChatRepoImpl(client)
    }

    @Provides
    @Singleton
    fun provideSocketsRepo(client: HttpClient): SocketRepo {
        return SocketsRepoImpl(client)
    }

}