package com.base.library.data

import com.base.library.data.http.HttpDataSourceImpl
import com.base.library.data.local.LocalDataSourceImpl

open class BaseRepository {

    val mHttpData = HttpDataSourceImpl.getInstance
    val mLocalData = LocalDataSourceImpl.getInstance


}
