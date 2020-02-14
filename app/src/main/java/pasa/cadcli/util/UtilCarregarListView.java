package pasa.cadcli.util;

import android.widget.AbsListView;

public abstract class UtilCarregarListView implements AbsListView.OnScrollListener {
    private static final int DEFAULT_THRESHOLD = 10 ;

    private boolean mLoading = true  ;
    private int previousTotal = 0 ;
    private int threshold = DEFAULT_THRESHOLD ;

    public UtilCarregarListView() {}

    public UtilCarregarListView(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if(mLoading) {
            if(totalItemCount > previousTotal) {
                // the mLoading has finished
                mLoading = false ;
                previousTotal = totalItemCount ;
            }
        }

        // check if the List needs more data
        if(!mLoading && ((firstVisibleItem + visibleItemCount ) >= (totalItemCount - threshold))) {
            mLoading = true ;

            // List needs more data. Go fetch !!
            loadMore(view, firstVisibleItem,
                    visibleItemCount, totalItemCount);
        }
    }

    // Called when the user is nearing the end of the ListView
    // and the ListView is ready to add more items.
    public abstract void loadMore(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
}
