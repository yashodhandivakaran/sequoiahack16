package hack16.sequioa.com.seqhack2016.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hack16.sequioa.com.seqhack2016.R;
import hack16.sequioa.com.seqhack2016.entities.Patient;

/**
 * Created by Yashodhan on 11/09/16.
 */
public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {

    private ArrayList<hack16.sequioa.com.seqhack2016.entities.Patient> patientArrayList;
    private Context mContext;

    public PatientAdapter(ArrayList<hack16.sequioa.com.seqhack2016.entities.Patient> patientArrayList, Context mContext) {
        this.patientArrayList = patientArrayList;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView cost;
        public TextView description;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patients_list_row,parent,false);
        PatientAdapter.ViewHolder vh = new ViewHolder(v);
        vh.name =(TextView) v.findViewById(R.id.name);
        vh.cost = (TextView)v.findViewById(R.id.cost);
        vh.description = (TextView)v.findViewById(R.id.description);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Patient patient = patientArrayList.get(position);

        holder.name.setText(patient.getPatientName());
        holder.cost.setText(String.valueOf(patient.getCost()));
        holder.description.setText(patient.getDescription());
    }

    @Override
    public int getItemCount() {
        return patientArrayList.size();
    }
}
