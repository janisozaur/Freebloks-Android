package de.saschahlusiak.freebloks.preferences;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;
import de.saschahlusiak.freebloks.Global;
import de.saschahlusiak.freebloks.R;

public class AboutFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences_about);

		findPreference("rate_review").setTitle(getString(R.string.prefs_rate_review, Global.IS_AMAZON ? "Amazon App Store" : "Google Play"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
		findPreference("rate_review").setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				String url;
				if (Global.IS_AMAZON)
					url = Global.getMarketURLString(getActivity().getPackageName());
				else
					url = "market://details?id=" + getActivity().getApplication().getPackageName();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);
				return true;
			}
		});
    	super.onActivityCreated(savedInstanceState);
    }
}