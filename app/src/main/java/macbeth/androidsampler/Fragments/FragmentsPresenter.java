package macbeth.androidsampler.Fragments;

import java.io.Serializable;

public class FragmentsPresenter implements Serializable {

    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;

    public FragmentsPresenter() {
        fragment1 = null;
        fragment2 = null;
        fragment3 = null;
    }

    public void registerFragment1(Fragment1 fragment) {
        fragment1 = fragment;
        fragment1.displayMessage("Hello Fragment 1 ... We have connected!");
    }

    public void registerFragment2(Fragment2 fragment) {
        fragment2 = fragment;
        fragment2.displayMessage("Hello Fragment 2 ... We have connected!");
    }

    public void registerFragment3(Fragment3 fragment) {
        fragment3 = fragment;
        fragment3.displayMessage("Hello Fragment 3 ... We have connected!");
    }


}
