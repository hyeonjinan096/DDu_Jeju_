package com.lyj.direction.jeju_setplan2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


//해당 클래스를 생성할 때 RecyclerViewRowTouchHelperContract를 입력받아 멤버 인스턴스로 지정하고 ItemTouchHelper.Callback이 지원하는
//여러 메소드를 오버라이딩 하여 필요에 따라 사용한 후  결과값들을 반환
public class RecyclerRowMoveCallback extends ItemTouchHelper.Callback {

    private RecyclerViewRowTouchHelperContract touchHelperContract;

    public RecyclerRowMoveCallback(RecyclerViewRowTouchHelperContract touchHelperContract){
        this.touchHelperContract = touchHelperContract;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }


    //리사이클러뷰와 리사이클러뷰 뷰홀더를 입력받는다.
    //drag 위치와 swipe 위치를 ItemTouchHelper에서 받아 셋팅
    //makeMovementFlags() 메소드로 drag위치와 swipe 위치 int로 반환
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipe_flag = ItemTouchHelper.START|ItemTouchHelper.END;
        return makeMovementFlags(dragFlag,swipe_flag);
    }


    //리사이클러뷰,viewHolder, target(viewHolder 중 선택된 아이템)을 입력받아 움직임을 감지한다.
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        this.touchHelperContract.onRowMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return false;
    }

    //리사이클러뷰의 뷰홀더와 움직일 방향을 입력
    // ItemTouchHelperListner의 onItemSwipe메소드에 움직일 방향을 입력하여 swipe를 구현
    //swiped 추가해봄
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        this.touchHelperContract.onItemSwipe(viewHolder.getAdapterPosition());

    }
    //


    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE)
        {
            if(viewHolder instanceof RecyclerViewAdapter.MyViewModel){
                RecyclerViewAdapter.MyViewModel myViewHolder = (RecyclerViewAdapter.MyViewModel)viewHolder;
                touchHelperContract.onRowSelected(myViewHolder);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if(viewHolder instanceof RecyclerViewAdapter.MyViewModel){
            RecyclerViewAdapter.MyViewModel myViewHolder = (RecyclerViewAdapter.MyViewModel)viewHolder;
            touchHelperContract.onRowClear(myViewHolder);
        }
    }


    //1,인터페이스 구현
    public interface RecyclerViewRowTouchHelperContract{
        //swiped 추가
        void onItemSwipe(int position);
        //
        void onRowMoved(int from,int to); //from->to position
        void onRowSelected(RecyclerViewAdapter.MyViewModel myViewHolder);
        void onRowClear(RecyclerViewAdapter.MyViewModel myViewHolder);
    }
}