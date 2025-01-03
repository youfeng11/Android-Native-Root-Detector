-ignorewarnings
-obfuscationdictionary ""
-classobfuscationdictionary ""
-packageobfuscationdictionary ""

-keep class com.reveny.nativecheck.ui.fragment.HomeFragment{
    public void setSignature(boolean);
}

-keep class com.reveny.nativecheck.ui.fragment.DetectionData { *; }

-keepclassmembers class * extends androidx.fragment.app.Fragment {
    public ** requireContext(...);
    public ** *(...);
}

-keepclassmembers class * extends androidx.fragment.app.Fragment {
    void *(android.os.Bundle);
    void *(...);
}
