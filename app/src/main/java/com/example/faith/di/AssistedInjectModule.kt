package com.example.faith.di

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
/**
 * @author Remi Mestdagh
 */
@InstallIn(FragmentComponent::class)
@AssistedModule
@Module
// Needed until AssistedInject is incorporated into Dagger
interface AssistedInjectModule
