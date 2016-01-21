# Textview Typeface memory handling 



Having the typeface objects like this allows typeface to be reused rather than creating new typeface object everytime it is needed. 

Here's the code! :+1:

```java
public class FontFactory {
	/**
    * Important: the font files (.ttf) files must be inside /assets/fonts/ folder
    */
    public static final String FONT_HELVETICANEUE_Bold =   "HelveticaNeue_Bold";
    public static final String FONT_HELVETICANEUE_Medium =   "HelveticaNeue_Medium";
    public static final String FONT_HELVETICANEUE_Regular =   "HelveticaNeue_Regular";
    public static final String FONT_ROBOTO_Bold =   "Roboto_Bold";
    public static final String FONT_ROBOTO_Light =   "Roboto_Light";
    public static final String FONT_ROBOTO_Medium =   "Roboto_Medium";
    public static final String FONT_ROBOTO_Regular =   "Roboto_Regular";
    
    //This typeface supports most chinese characters
    public static final String FONT_DROIDSANS_Fallback =   "DroidSansFallback";

    private static String getPath(String str){
        return "fonts/"+str+".ttf";
    }

    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), getPath(name));
            }catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }

    public static void applyTypeFace(Context context,TextView tv, String name){
        tv.setTypeface(FontCache.get(name,context));
    }
}
```

Usage:
```java
TextView textView = (TextView)findViewById(R.id.textView);
//usage #1
textView.setTypeFace(FontFactory.get(FontFactory.FONT_HELVETICANEUE_Bold));

//usage #2
FontFactory.applyTypeFace(getActivity(), textView, FontFactory.FONT_HELVETICANEUE_Bold);
```


