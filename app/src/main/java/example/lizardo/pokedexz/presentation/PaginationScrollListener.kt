package example.lizardo.pokedexz.presentation

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.security.InvalidParameterException
abstract class PaginationScrollListener(private val layoutManager: RecyclerView.LayoutManager) :
    RecyclerView.OnScrollListener(), View.OnScrollChangeListener {


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        Log.d("loadMore", "onScrolled isLoading = ${isLoading()}")
        //Log.d("loadMore", "onScrolled isLastPage = ${isLastPage()}")
        if (isLoading()){
            return
        }

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = when (layoutManager){
            is LinearLayoutManager -> {
                layoutManager.findFirstVisibleItemPosition()
            }
            is StaggeredGridLayoutManager -> {
                layoutManager.findFirstVisibleItemPositions(null).min()
            }
            else -> throw InvalidParameterException()
        }

        if ((visibleItemCount + firstVisibleItemPosition ) >= totalItemCount && firstVisibleItemPosition >= 0) {
            loadMoreItems()
        }
    }
    protected abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
}