package com.example.cyclebooking.PreviousBooking;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cyclebooking.R;

import java.util.List;

public class PreviousBookingAdapter extends ArrayAdapter<PreviousHelperClass> {
    private Activity context;
    private List<PreviousHelperClass> previousHelperClasses;

    public PreviousBookingAdapter(Activity context,List<PreviousHelperClass> previousHelperClasses){
        super(context, R.layout.previous_booking_cardview,previousHelperClasses);
        this.context=context;
        this.previousHelperClasses=previousHelperClasses;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.previous_booking_cardview, null, true);

        TextView admission = (TextView) listViewItem.findViewById(R.id.admission_number);
        TextView pickUp = (TextView) listViewItem.findViewById(R.id.pick_up_zone);
        TextView travelTime = (TextView) listViewItem.findViewById(R.id.total_travel_time);
        TextView amount = (TextView) listViewItem.findViewById(R.id.amount_paid);
        TextView typeOfCycle = (TextView) listViewItem.findViewById(R.id.type_of_cycle);
        TextView dropZone = (TextView) listViewItem.findViewById(R.id.drop_zone);
        TextView bookingFinished = (TextView) listViewItem.findViewById(R.id.booking_finished_on);

        PreviousHelperClass previousHelperClass = previousHelperClasses.get(position);
        admission.setText(previousHelperClass.getAdmission());
        pickUp.setText(previousHelperClass.getPickUp());
        travelTime.setText(previousHelperClass.getTravelTime());
        amount.setText(previousHelperClass.getAmount());
        typeOfCycle.setText(previousHelperClass.getTypeOfCycle());
        dropZone.setText(previousHelperClass.getDropZone());
        bookingFinished.setText(previousHelperClass.getBookingFinished());
        return listViewItem;


    }
}
