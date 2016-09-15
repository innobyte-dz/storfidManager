package wolfsoft.invincible.Fragments;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockContentResolver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.alexzh.circleimageview.CircleImageView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.ExecutionException;

import wolfsoft.invincible.R;
import wolfsoft.invincible.model.Field;
import wolfsoft.invincible.model.FieldList;
import wolfsoft.invincible.model.Product;
import wolfsoft.invincible.utils.AndroidUtility;
import wolfsoft.invincible.utils.storage.FileManager;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyProductRecyclerViewAdapter extends RecyclerView.Adapter<MyProductRecyclerViewAdapter.ViewHolder> {

    private final List<Product> mValues;
    private final ListProductFragment.OnListFragmentInteractionListener mListener;

    public MyProductRecyclerViewAdapter(List<Product> items, ListProductFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getName());
        holder.mIdView.setText(mValues.get(position).getTagId());
        try {
            holder.mAttribView.setText(Field.find(Field.class, "productId = ?", mValues.get(position).getTagId()).get(0).getFieldName());
             }catch (Exception s){
            holder.mAttribView.setText("TESTING");
        }
         holder.circle_image.setImageResource(R.drawable.icon_light_info);
       // holder.mIdView.setText(mValues.get(position).getTagId());
       //holder.mContentView.setText(mValues.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mAttribView;
        public final CircleImageView circle_image;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mAttribView = (TextView) view.findViewById(R.id.txtAttr);
            circle_image = (CircleImageView) view.findViewById(R.id.circle_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}
