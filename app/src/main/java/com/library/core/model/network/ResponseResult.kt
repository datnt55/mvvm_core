/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.library.core.model.network

sealed class ResponseResult<out R> {

    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Error(val exception: String) : ResponseResult<Nothing>()
    object Loading : ResponseResult<Nothing>()

}


/**
 * `true` if [ResponseResult] is of type [Success] & holds non-null [Success.data].
 */
val ResponseResult<*>.succeeded
    get() = this is ResponseResult.Success && data != null
