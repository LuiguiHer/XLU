package com.example.xlu.ui.sign_up.di

import com.example.xlu.ui.utils.Utils
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModuleFireStore {

    @Provides
    @Singleton
    fun provideFireStoreInstance() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideUserList(
        fireStore: FirebaseFirestore
    )= fireStore.collection(Utils.TABLE_NAME_FIRESTONE)
}