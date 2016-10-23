package animeshrine.kortas.com.animeshrine;

/**
 * Created by Aladinne on 05/09/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CustomAdapter extends ArrayAdapter<String>{
    public CustomAdapter(Context context, String[] animes) {
        super(context, R.layout.custom_row ,animes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // default -  return super.getView(position, convertView, parent);
        // add the layout
        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(R.layout.custom_row, parent, false);
        // get references.
        String singleFoodItem = getItem(position);
        TextView itemText = (TextView) customView.findViewById(R.id.item_text);
        ImageView buckysImage = (ImageView) customView.findViewById(R.id.my_profile_image);

        // dynamically update the text from the array
        itemText.setText(singleFoodItem);
        // using the same image every time
        buckysImage.setImageResource(R.drawable.ic_menu_gallery);
        // Now we can finally return our custom View or custom item
        return customView;
    }



}
