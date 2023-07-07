package com.cooder.project.biz_search.fragment.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.library.library.cache.CoStorage
import com.cooder.library.library.executor.CoExecutor
import com.cooder.library.library.restful.CoResult

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/7 15:44
 *
 * 介绍：HistorySearchViewModel
 */
class HistorySearchViewModel : ViewModel() {
	
	private var histories = mutableListOf<String>()
	
	private companion object {
		private const val TYPE_SEARCH_HISTORY = "search"
		private const val KEY_SEARCH_HISTORY = "history"
		private const val MAX_HISTORY_SIZE = 20
	}
	
	/**
	 * 保存历史记录
	 */
	fun saveSearchHistory(history: String) {
		CoExecutor.execute {
			if (history in histories) {
				histories.remove(history)
			}
			histories.add(0, history)
			if (histories.size > MAX_HISTORY_SIZE) {
				histories.removeLast()
			}
			CoStorage.saveCache(TYPE_SEARCH_HISTORY, KEY_SEARCH_HISTORY, histories)
		}
	}
	
	/**
	 * 查询历史记录
	 */
	fun queryHistorySearch(): LiveData<CoResult<List<String>>> {
		val liveData = MutableLiveData<CoResult<List<String>>>()
		CoExecutor.execute {
			this.histories = CoStorage.getCache(TYPE_SEARCH_HISTORY, KEY_SEARCH_HISTORY, mutableListOf())!!
			liveData.postValue(CoResult.success(histories))
		}
		return liveData
	}
	
	fun clearHistorySearch() {
		CoExecutor.execute {
			CoStorage.deleteCache(TYPE_SEARCH_HISTORY, KEY_SEARCH_HISTORY)
		}
	}
}