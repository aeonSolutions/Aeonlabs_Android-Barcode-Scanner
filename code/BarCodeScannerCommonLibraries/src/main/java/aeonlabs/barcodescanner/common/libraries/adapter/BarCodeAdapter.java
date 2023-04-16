package aeonlabs.barcodescanner.common.libraries.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.material.snackbar.Snackbar;

import aeonlabs.barcodescanner.common.libraries.R;
import aeonlabs.barcodescanner.common.libraries.activity.WebViewActivity;
import aeonlabs.barcodescanner.common.libraries.model.BarCode;
import aeonlabs.barcodescanner.common.libraries.utils.ClipBoardManager;

import java.util.ArrayList;

public class BarCodeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Object> barCodeArrayList;
    private static final int BARCODE_ITEM_VIEW_TYPE = 0 ;
    private static final int AD_VIEW_TYPE = 1;

    public BarCodeAdapter(Context context, ArrayList<Object> barCodeArrayList) {
        this.context = context;
        this.barCodeArrayList = barCodeArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case BARCODE_ITEM_VIEW_TYPE :
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_item_container,parent,false);
                return new BarCodeViewHolder(view);
            case AD_VIEW_TYPE:
                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_native_express_add, parent , false);

                return  new NativeExpressAdViewHolder(nativeExpressLayoutView);
        }
    }

    //********************************************************************************
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case BARCODE_ITEM_VIEW_TYPE:
            default :
                BarCodeViewHolder productViewHolder = (BarCodeViewHolder)holder;
                setProductView(productViewHolder , position);
                break ;
            case AD_VIEW_TYPE:
                /** Now set the add view in the cardview of adViewHolder **/
                NativeExpressAdViewHolder nativeExpressAdViewHolder = (NativeExpressAdViewHolder) holder;
                NativeAdView adView = (NativeAdView)barCodeArrayList.get(position);
                ViewGroup adCardView = (ViewGroup)nativeExpressAdViewHolder.itemView;
                adCardView.removeAllViews();
                if(adView.getParent()!=null){
                    ((ViewGroup)adView.getParent()).removeView(adView);
                }
                adCardView.addView(adView);
                break ;
        }
    }

    private void setProductView(BarCodeViewHolder holder, final  int position) {
        final BarCode barCode = (BarCode)barCodeArrayList.get(position);
        holder.txtScanResult.setText(barCode.getBarcodeNumber());
        holder.txtScanTime.setText(barCode.getScanDate()+" "+barCode.getScanTime());
        holder.txtScanNo.setText(String.valueOf(position+1));

        String sentStatus="not sent";
        int imgRes= R.drawable.not_sent;
        if(barCode.getSent().equals("1")) {
            sentStatus = "sent";
            imgRes=R.drawable.sent;
        }
        holder.sent_status.setText(sentStatus);
        holder.sent_status_img.setImageResource(imgRes);

        if(position%2==0){
            holder.layoutRightButtons.setBackgroundColor(context.getResources().getColor(R.color.card_right_blue));
        }
        if(position%3==0){
            holder.layoutRightButtons.setBackgroundColor(context.getResources().getColor(R.color.card_right_purple));
        }


        holder.layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("product_id",barCode.getBarcodeNumber());
                context.startActivity(intent);
            }
        });
        holder.layoutCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipBoardManager clipBoardManager = new ClipBoardManager();
                clipBoardManager.copyToClipboard(context,barCode.getBarcodeNumber());
                Snackbar.make(v,"Copied To Clipboard",Snackbar.LENGTH_SHORT).show();
            }
        });
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShareDialog(barCode.getBarcodeNumber());
            }
        });
    }

    private void openShareDialog(String result) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        String appLink = "https://play.google.com/store/apps/details?id="+context.getPackageName();
        sharingIntent.setType("text/plain");
        String shareBodyText = "Scan Result: "+ result+"."+
                "\nCheck Out"+ context.getResources().getString(aeonlabs.common.libraries.R.string.app_name)+" \n " +
                "Link: "+appLink +" \n" +
                " #Barcode #Android";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,context.getResources().getString(aeonlabs.common.libraries.R.string.app_name));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        context.startActivity(Intent.createChooser(sharingIntent, "Share"));
    }

    @Override
    public int getItemViewType(int position) {
        /*
        if((position ==getItemCount()-1)){
            return  AD_VIEW_TYPE ;
        } else {
            return  BARCODE_ITEM_VIEW_TYPE;
        }
        */
        return  BARCODE_ITEM_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return barCodeArrayList.size();
    }

    public class BarCodeViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutRightButtons ;
        private RelativeLayout layoutCopy , layoutSearch ;
        private TextView txtScanResult , txtScanNo , txtScanTime, sent_status ;
        private Button btnShare ;
        private RelativeLayout backgroundContainer;
        private LinearLayout foregroundContainer;
        private ImageView sent_status_img;

        public BarCodeViewHolder(View itemView) {
            super(itemView);
            foregroundContainer = itemView.findViewById(R.id.swipe_foreground_container);
            backgroundContainer = itemView.findViewById(R.id.swipe_background_container);

            layoutRightButtons = itemView.findViewById(R.id.layout_right_buttons);
            layoutCopy = itemView.findViewById(R.id.layout_copy);
            layoutSearch = itemView.findViewById(R.id.layout_search);
            txtScanNo = itemView.findViewById(R.id.txt_scan_no);
            txtScanResult = itemView.findViewById(R.id.txt_scan_result);
            txtScanTime = itemView.findViewById(R.id.txt_date_time);
            btnShare = itemView.findViewById(R.id.btn_share);
            sent_status= itemView.findViewById(R.id.sent_status);
            sent_status_img= itemView.findViewById(R.id.sent_status_img);
        }

        public LinearLayout getForegroundContainer() {
            return foregroundContainer;
        }
    }

    public class NativeExpressAdViewHolder extends  RecyclerView.ViewHolder{
        public NativeExpressAdViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * Remove an item from the recyclerview at index *position*
     * @param position
     */
    public void removeSwipeItem(int position){
        barCodeArrayList.remove(position);
        this.notifyItemRemoved(position);
    }

    /**
     * Add a Contact *contact* into the recyclerview at index *position*
     * @param position
     * @param barCode
     */
    public void addSwipeItem(int position, BarCode barCode){
        barCodeArrayList.add(position,barCode);
        this.notifyItemInserted(position);
    }
}

