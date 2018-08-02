/*
 * Copyright 2017 Vandolf Estrellado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tran.nam.core.di.module

import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

import java.util.Objects

import javax.inject.Named

import dagger.Module
import dagger.Provides
import tran.nam.core.di.inject.PerFragment

/**
 * Provides base fragment dependencies. This must be included in all fragment modules, which must
 * provide a concrete implementation of [Fragment] and named [.FRAGMENT].
 */
@Module
abstract class BaseFragmentModule {

    companion object {

        /**
         * See [BaseChildFragmentModule] class documentation regarding the need for this name.
         */
        const val FRAGMENT = "BaseFragmentModule.fragment"

        const val CHILD_FRAGMENT_MANAGER = "BaseFragmentModule.childFragmentManager"

    }

    @Provides
    @Named(CHILD_FRAGMENT_MANAGER)
    @PerFragment
    internal fun childFragmentManager(@Named(FRAGMENT) fragment: Fragment): FragmentManager {
        return fragment.childFragmentManager
    }
}