package namofo.org.jiesehelper.adapter;

/**
 * create by zhengjiong
 * Date: 2015-07-08
 * Time: 22:01
 */
/*
 * Copyright (C) 2014 sebnapi
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

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by sebnapi on 08.11.14.
 * <p/>
 * This is a Plug-and-Play Approach for adding a Header or Footer to
 * a RecyclerView backed list
 * <p/>
 * Just wrap your regular adapter like this
 * <p/>
 * new HeaderRecyclerViewAdapterV1(new RegularAdapter())
 * <p/>
 * Let RegularAdapter implement HeaderRecyclerView, FooterRecyclerView or both
 * and you are ready to go.
 * <p/>
 * I'm absolutely not sure how this will behave with changes in the dataset.
 * You can always wrap a fresh adapter and make sure to not change the old one or
 * use my other approach.
 * <p/>
 * With the other approach you need to let your Adapter extend HeaderRecyclerViewAdapterV2
 * (and therefore change potentially more code) but possible omit these shortcomings.
 * <p/>
 * TOTALLY UNTESTED - USE WITH CARE - HAVE FUN :)
 */
public class HeaderRecyclerViewAdapter extends RecyclerView.Adapter {
    private boolean mShowFooter = false;
    private static final int TYPE_FOOTER = Integer.MIN_VALUE + 1;
    //private static final int TYPE_ADAPTEE_OFFSET = 2;

    private final RecyclerView.Adapter mAdaptee;


    public HeaderRecyclerViewAdapter(RecyclerView.Adapter adaptee) {
        mAdaptee = adaptee;
    }

    public RecyclerView.Adapter getAdaptee() {
        return mAdaptee;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER && mAdaptee instanceof FooterRecyclerView) {
            return ((FooterRecyclerView) mAdaptee).onCreateFooterViewHolder(parent, viewType);
        }
        return mAdaptee.onCreateViewHolder(parent, viewType/* - TYPE_ADAPTEE_OFFSET*/);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == mAdaptee.getItemCount() && holder.getItemViewType() == TYPE_FOOTER && useFooter()) {
            ((FooterRecyclerView) mAdaptee).onBindFooterView(holder, position);
        } else {
            mAdaptee.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = mAdaptee.getItemCount();
        if (useFooter()) {
            itemCount += 1;
        }
        return itemCount;
    }

    private boolean useFooter() {
       /* if (mAdaptee instanceof FooterRecyclerView) {
            return true;
        }*/
        return mShowFooter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mAdaptee.getItemCount() && useFooter()) {
            return TYPE_FOOTER;
        }
        /*if (mAdaptee.getItemCount() >= Integer.MAX_VALUE - TYPE_ADAPTEE_OFFSET) {
            new IllegalStateException("HeaderRecyclerViewAdapter offsets your BasicItemType by " + TYPE_ADAPTEE_OFFSET + ".");
        }*/
        return mAdaptee.getItemViewType(position)/* + TYPE_ADAPTEE_OFFSET*/;
    }

    public interface FooterRecyclerView {
        RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType);

        void onBindFooterView(RecyclerView.ViewHolder holder, int position);
    }

    public boolean isShowFooter() {
        return mShowFooter;
    }

    public void setShowFooter(boolean mShowFooter) {
        this.mShowFooter = mShowFooter;
        this.notifyDataSetChanged();
    }
}
