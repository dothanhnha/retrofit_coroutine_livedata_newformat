package com.example.retrofitcouroutinelivedatanewformat

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofitcouroutinelivedatanewformat.SearchRepositoriesRepos.Companion.NETWORK_PAGE_SIZE
import com.example.retrofitcouroutinelivedatanewformat.model.Repository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val GITHUB_STARTING_PAGE_INDEX = 0

class PagingRepos @Inject constructor(
        val searchRepositoriesApi: SearchRepositoriesApi
) : PagingSource<Int, Repository>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        val position = params.key ?: 0
        val apiQuery = "android"
        return try {
            val response = searchRepositoriesApi.getRepositories(SearchRepositoriesApi.SortType.STAR,
                    apiQuery, params.loadSize, position)
            val listItem = response.items
            val nextKey = if (listItem.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            Log.d("Nha_Check", nextKey.toString())
            LoadResult.Page(
                data = listItem,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}