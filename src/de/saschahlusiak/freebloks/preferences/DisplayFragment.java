package de.saschahlusiak.freebloks.preferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import de.saschahlusiak.freebloks.R;

public class DisplayFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences_display);
    }

    @Override
    public void onResume() {
		SharedPreferences prefs = getPreferenceScreen().getSharedPreferences();
		onSharedPreferenceChanged(prefs, "theme");
		prefs.registerOnSharedPreferenceChangeListener(this);
		super.onResume();
    }

    @Override
    public void onPause() {
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    	super.onPause();
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		if (key.equals("theme")) {
			ListPreference pref = (ListPreference)findPreference(key);
			pref.setSummary(pref.getEntry());
		}
	}
}