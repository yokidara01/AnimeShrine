package animeshrine.kortas.com.animeshrine;

/**
 * Created by Aladinne on 06/09/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class cstmAdptr extends ArrayAdapter<Anime>  implements Filterable {

    private List<Anime> itemList;
    private Context context;
    private Filter mFilter  = new ItemFilter();
    private List<String>filteredData = null;

    public cstmAdptr(List<Anime> itemList, Context ctx) {
        super(ctx, android.R.layout.simple_list_item_1, itemList);
        this.itemList = itemList;
        this.context = ctx;
    }



    public int getCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }

    public Anime getItem(int position) {
        if (itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_row, null);
        }

        Anime a = itemList.get(position);
        TextView text = (TextView) v.findViewById(R.id.item_text);
        text.setText(a.getName());
        ImageView buckysImage = (ImageView) v.findViewById(R.id.my_profile_image);
       // buckysImage.setImageResource(R.drawable.nrt04calendarcover);

buckysImage.setImageBitmap(null);
        return v;

    }

    public List<Anime> getItemList() {
        return itemList;
    }

    public void setItemList(List<Anime> itemList) {
        this.itemList = itemList;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Anime> list = itemList;

            int count = list.size();
            final ArrayList<String> nlist = new ArrayList<String>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getName();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }

    }



}