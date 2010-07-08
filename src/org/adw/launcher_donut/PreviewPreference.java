package org.adw.launcher_donut;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.preference.Preference;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PreviewPreference extends Preference {
	private CharSequence themeName;
	private CharSequence themePackageName;
	private CharSequence themeDescription;
	private Drawable themeIcon;
	private Drawable themePreview;
	public PreviewPreference(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public PreviewPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public PreviewPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onBindView(View view) {
		// TODO Auto-generated method stub
		super.onBindView(view);
		android.util.Log.d("PreferencePreview","onBindView:"+themePackageName);
		if(themePackageName!=null && themePackageName.toString().length()>0){
			TextView vThemeTitle= (TextView) view.findViewById(R.id.ThemeTitle);
			vThemeTitle.setText(themeName);
			TextView vThemeDescription= (TextView) view.findViewById(R.id.ThemeDescription);
			vThemeDescription.setMovementMethod(LinkMovementMethod.getInstance());
			vThemeDescription.setText(Html.fromHtml(themeDescription.toString()));
			Button applyButton= (Button) view.findViewById(R.id.ThemeApply);
			applyButton.setEnabled(true);
		}else{
			Button applyButton= (Button) view.findViewById(R.id.ThemeApply);
			applyButton.setEnabled(false);
		}
	}
	public void setTheme(CharSequence packageName){
		android.util.Log.d("PreferencePreview","setThemeName:"+packageName);
		themePackageName=packageName;
		themeName=null;
		themeDescription=null;
        if(!packageName.equals(Launcher.THEME_DEFAULT)){
        	Resources themeResources=null;
        	try {
    			themeResources=getContext().getPackageManager().getResourcesForApplication(packageName.toString());
    		} catch (NameNotFoundException e) {
    			//e.printStackTrace();
    		}
    		if(themeResources!=null){
    			int themeNameId=themeResources.getIdentifier("theme_title", "string", packageName.toString());
    			if(themeNameId!=0){
    				themeName=themeResources.getString(themeNameId);
    			}
    			int themeDescriptionId=themeResources.getIdentifier("theme_description", "string", packageName.toString());
    			if(themeDescriptionId!=0){
    				themeDescription=themeResources.getString(themeDescriptionId);
    			}
    		}
        }
        if(themeName==null)themeName=getContext().getResources().getString(R.string.pref_title_theme_preview);
    	if(themeDescription==null)themeDescription=getContext().getResources().getString(R.string.pref_summary_theme_preview);

		notifyChanged();
	}
	public CharSequence getValue(){
		return themePackageName;
	}
}
