# Android Support

To add support for analyzing Android projects consider the following options.

Note that while the Android Development Tools for Eclipse are no longer directly maintained by Google, the update site is still active and will work for our purposes.

To install the Android Development Tools in Eclipse navigate to `Help` &gt; `Install New Software`. Enter the update site `https://dl-ssl.google.com/android/eclipse/` and press enter. Select all available plugins to be installed and restart Eclipse. Once Eclipse restarts you will need to provide the location of the Android SDK bundle for your OS (available online at: [https://developer.android.com/studio/index.html](https://developer.android.com/studio/index.html), see the section "Get just the command line tools"). Once linked against the SDK bundle the SDK manager can be used to download the appropriate SDK versions.

## Android Source Projects
In order to analyze an Android source project the Android project must be able to compile in the Eclipse workspace. To compile and an Android project in Eclipse the Android Development Tools must be installed along with the proper supporting API version for the given application to compile.

## Android Binary Projects (compiled APKs)
Atlas for Jimple includes support for analyzing Android APKs compiled to Dalvik. The Android bytecode is translated to Jimple (a three address code intermediate language) and mapped by Atlas to create the queryable program graph. To import an Android APK into the Eclipse workspace as an analyzable project navigate to `File` &gt; `Import` &gt; `Other...` &gt; `ABP` &gt; `Android Binary Project`, provide a project name, and then select the Android APK file. Note that in order to properly translate the APK bytecode to Jimple, the [Android Development Tools](https://dl-ssl.google.com/android/eclipse/) should be installed along with the proper Android SDKs that the APK was compiled against. For example if the APK was compiled against Android API 15, then the SDK manager should be used to download Android API 15 JAR libraries (the simulators are not required for Atlas). If there are missing APIs for the APK project that are preventing the APK from being translated properly then navigate to the `Eclipse` &gt; `Preferences` &gt; `ABP` menu and enable the "Phantom References" options.

## Android Essentials Toolbox
In order to more effectively analyze and Android project, one may wish to also install the Android Essentials Toolbox for Android domain specific analysis capabilities. Additional tutorials for the tutorial are provided at the link below.

The Android Essentials Toolbox is a collection program analysis tools specifically developed for analyzing Android applications. The toolbox aids in mapping Android Permission Groups and Protection Levels to Permissions as well as Permissions to the respective permission protected Android APIs. These relationships are encoded in the tool and seamlessly applied to a program graph by leveraging the Atlas framework. A user friendly interface is provided for browsing permission usage.

Android Essentials Toolbox: [https://ensoftcorp.github.io/android-essentials-toolbox](https://ensoftcorp.github.io/android-essentials-toolbox)