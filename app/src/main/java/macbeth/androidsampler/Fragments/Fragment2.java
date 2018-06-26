package macbeth.androidsampler.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import macbeth.androidsampler.R;

public class Fragment2 extends Fragment {

    FragmentsPresenter presenter = null;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_fragment2, container, false);
        Bundle args = getArguments();
        presenter = (FragmentsPresenter) args.getSerializable("Presenter");
        if (presenter != null) {
            presenter.registerFragment2(this);
        }
        TextView tvTime = rootView.findViewById(R.id.textView16);
        tvTime.setText(args.getString("SystemTime"));
        return rootView;
    }

    public void displayMessage(String message) {
        TextView tvMessage = rootView.findViewById(R.id.editText7);
        tvMessage.setText(message);
    }
}
