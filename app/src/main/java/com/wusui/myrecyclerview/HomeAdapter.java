package com.wusui.myrecyclerview;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fg on 2016/2/2.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
{
    private Context mContext;
    private List<String>mDatas;
    private List<Integer>heights;
    public HomeAdapter(Context context, List<String> mDatas){
       mContext = context;
        this.mDatas =mDatas;
        getRandomHeihgt(mDatas);
    }
    //我不知道为什么 Unable to start activity ComponentInfo
    @Override//视图的问题等下，我看下
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)//（在哪里创建，创建什么类型的view)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_home, parent,//把holder给new出来
                false));
        return holder;
    }/*Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
*This new ViewHolder should be constructed with a new View that can represent the items of the given type.
*You can either create a new View manually or inflate it from an XML layout file.
*The new ViewHolder will be used to display items of the adapter using onBindViewHolder(ViewHolder, int, List).
* Since it will be re-used to display different items in the data set,
* it is a good idea to cache references to sub views of the View to avoid unnecessary findViewById(int) calls.
*Parameters
*parent	The ViewGroup into which the new View will be added after it is bound to an adapter position.
*viewType	The view type of the new View.*/
    @Override//数据的问题
    public void onBindViewHolder( final MyViewHolder holder,final int position)//（绑定到哪里，绑什么——数据的实例）
    {
//MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home, parent,false));把布局实例化——》                                              把布局实例化——》
//tv = (TextView) view.findViewById(R.id.id_num);把控件实例化——》
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        // 你看，这里是想获得保存的高度，但第一次是没有高度被保存的
        params.height = heights.get(position);
        holder.itemView.setLayoutParams(params);
        holder.tv.setText(mDatas.get(position));//真正加载数据到布局里面的方法
        if (mOnItemClickListener !=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                    mOnItemClickListener.onItemClick(holder.itemView,pos);//把事件交给我们实现的接口那里处理
                }
            });
        }
    }
    /*
    Called by RecyclerView to display the data at the specified position.
    This method should update the contents of the itemView to reflect the item at the given position.
    Note that unlike ListView, RecyclerView will not call this method again if the position of the item changes
    in the data set unless the item itself is invalidated or the new position cannot be determined. For this reason, you should only use the position parameter while acquiring the related data item inside this method and should not keep a copy of it. If you need the position of an item later on (e.g. in a click listener), use getAdapterPosition() which will have the updated adapter position.
    Partial bind vs full bind:
    The payloads parameter is a merge list from notifyItemChanged(int, Object) or notifyItemRangeChanged(int, int, Object).
    If the payloads list is not empty, the ViewHolder is currently bound to old data and Adapter may run an efficient partial update using the payload info.
    If the payload is empty, Adapter must run a full bind.
     Adapter should not assume that the payload passed in notify methods will be received by onBindViewHolder().
    For example when the view is not attached to the screen, the payload in notifyItemChange() will be simply dropped.
    Parameters
    holder	The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
    position	The position of the item within the adapter's data set.
    payloads	A non-null list of merged payloads. Can be empty list if requires full update.*/
    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    class MyViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder
    {
        private CardView item_cardview;
        private TextView tv;

        public MyViewHolder(View view)
        {
            super(view);
            item_cardview = (CardView)view.findViewById(R.id.item_cardview);
            tv = (TextView) view.findViewById(R.id.id_num);
        }//= =呀，忘了ViewHolder的作用是用来放控件的。但是光把控件放在里面还是不得行的，还要为Item创建视图，还要将数据绑定到Item的视图上（这妈当的 = =）
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    // 我以为你是要保存高度，结果你是要随机一个高度……但你看，你这个方法根本没调用，list是null，所以空指针
    // 完全没必要啊……图片长宽比本来就不一样，干嘛要随机一个高度
    private void getRandomHeihgt(List<String>mDatas){
        heights = new ArrayList<>();
        for (int i = 0;i < mDatas.size();i++){
            heights.add((int)(200+Math.random()*400));
        }
    }
}

