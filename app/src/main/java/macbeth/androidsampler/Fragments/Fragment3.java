package macbeth.androidsampler.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import macbeth.androidsampler.R;

public class Fragment3 extends Fragment {

    FragmentsPresenter presenter = null;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_fragment3, container, false);

        presenter = ((FragmentsActivity) getActivity()).getPresenter();
        if (presenter != null) {
            presenter.registerFragment3(this);
        }
        // Get the system time from the bundle arguments
        Bundle args = getArguments();
        TextView tvTime = rootView.findViewById(R.id.textView19);
        tvTime.setText(args.getString("SystemTime"));
        return rootView;
    }

    public void displayMessage(String message) {
        TextView tvMessage = rootView.findViewById(R.id.editText8);
        tvMessage.setText(message);
    }
}
