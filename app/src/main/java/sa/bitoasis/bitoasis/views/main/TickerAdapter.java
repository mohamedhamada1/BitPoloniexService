package sa.bitoasis.bitoasis.views.main;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sa.bitoasis.bitoasis.R;
import sa.bitoasis.bitoasis.databinding.Ticker2SingleItemBinding;
import sa.bitoasis.bitoasis.databinding.TickerSingleItemBinding;
import sa.bitoasis.bitoasis.models.Ticker;
import sa.bitoasis.bitoasis.utils.DataBoundListAdapter;
import sa.bitoasis.bitoasis.utils.DataBoundViewHolder;


public class TickerAdapter extends DataBoundListAdapter<Ticker, ViewDataBinding> {

    private TickerAdapterCallback callback;
    public final int CELL_VIEW_1 = 1;
    public final int CELL_VIEW_2 = 2;
    private int selectedCellView = CELL_VIEW_1;

    public TickerAdapter(TickerAdapterCallback callback) {
        this.callback = callback;
    }

    public void changeDesign() {
        this.selectedCellView = this.selectedCellView == CELL_VIEW_1 ? CELL_VIEW_2 : CELL_VIEW_1;
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent) {
        ViewDataBinding binding;
        if (selectedCellView == CELL_VIEW_1) {
            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.ticker_single_item,
                    parent,
                    false);
        } else {
            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.ticker2_single_item,
                    parent,
                    false);
        }
        return binding;
    }

    public void replaceAll(List<Ticker> tickers) {
        items = tickers;
        notifyDataSetChanged();
    }


    @Override
    protected void bind(DataBoundViewHolder<ViewDataBinding> holder, ViewDataBinding binding, Ticker item) {
        TextView TVCurrencyPair;
        if (binding instanceof TickerSingleItemBinding) {
            ((TickerSingleItemBinding) binding).setTicker(item);
            TVCurrencyPair = ((TickerSingleItemBinding) binding).TVCurrencyPair;
        } else {
            ((Ticker2SingleItemBinding) binding).setTicker(item);
            TVCurrencyPair = ((Ticker2SingleItemBinding) binding).TVCurrencyPair;
        }
        TVCurrencyPair.setText(callback == null ? item.getCurrencyPairId() + "" : callback.getPairCurrencyName(item.getCurrencyPairId()));
    }


    @Override
    protected boolean areItemsTheSame(Ticker oldItem, Ticker newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(Ticker oldItem, Ticker newItem) {
        return false;


    }

    interface TickerAdapterCallback {
        String getPairCurrencyName(int pairId);
    }

}
