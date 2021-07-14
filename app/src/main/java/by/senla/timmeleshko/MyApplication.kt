package by.senla.timmeleshko

import android.app.Application
import android.content.Context
import android.util.Log
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context
) {
    @Provides // Методы в модуле, которые возвращают какие-то компоненты
    @Singleton // Один компонент на всё приложение
    fun provideContext(): Context {
        return context
    }
}

@Module(includes = [AppModule::class]) // Включить в этот модуль другой модуль
class NetworkModule {

    @Provides // Методы в модуле, которые возвращают какие-то компоненты
    @Singleton // Один компонент на всё приложение
    fun provideNetworkService(context: Context): NetworkService {
        return NetworkServiceImpl(context)
    }
}

@Component(modules = [AppModule::class, NetworkModule::class])
@Singleton // Чтобы даггер не создавал их по многу раз (чтобы не инжектились по многу раз)
interface ActivityComponent {
    fun inject(activity: MainActivity) // Конкретная активность, куда прокидывается NetworkService
}

class MyApplication : Application() {

    private lateinit var appComponent: ActivityComponent // Мост, через который зависимости могут перетекать в другие места

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerActivityComponent
            .builder()
            .appModule(AppModule(this))
            .build()
        Log.e("TAG", "Application: Created!")
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.e("TAG", "Application: Terminated!")
    }

    fun appComponent(): ActivityComponent {
        return appComponent
    }
}