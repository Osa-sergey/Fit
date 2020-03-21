package Utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int offset;

    public GridItemDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        outRect.top = offset;
        if(layoutParams.getSpanIndex()%2 == 0){
            outRect.left = offset;
            outRect.left = offset/2;
        }else{
            outRect.right = offset;
            outRect.left = offset/2;
        }
    }
}
