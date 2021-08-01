package com.svarks.myapplication.data

/**
 * @param localDataSource
 * Common Repository class to fetch the data
 */
class Repository(localDataSource: LocalDataSource) {
    val local = localDataSource
}
