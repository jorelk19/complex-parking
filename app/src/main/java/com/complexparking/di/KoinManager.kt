package com.complexparking.di

import android.content.Context
import com.complexparking.data.repository.local.ParkingDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/**
 * Class used to manage the koin dependency injection that load different modules in the application
 * @author Edson Joel Nieto Ardila
 * @since 1.0.0
 * */
class KoinManager {
    companion object {
        fun initKoin(context: Context, db: ParkingDatabase) {
            val moduleList = arrayListOf(
                //RepositoryModule(apiKey).initModule(),
                /*networkModule,
                repositoryModule,*/
                preferenceModule(context),
                databaseModule(db),
                useCaseModule,
                viewModelModule,
                deviceModule(context)
            )

            startKoin {
                androidContext(context)
                modules(
                    moduleList
                )
            }
        }
    }
}