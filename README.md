Firebase Crashlytics Ndk Deobfuscation Bug Reproduction Project
===============================================================

Description
-----------
This project reproduces an issue with Firebase Crashlytics and the Android Gradle Plugin 4.0.x, where native crashes have missing stacktraces.

This project does not contain native code, but has a dependency on the the linphone sdk, which contains native code.
A task [extractNdkSymbols](app/crashlytics_ndk_symbols.gradle)  is configured to run before `uploadCrashlyticsSymbolFile<VariantType>`: it extracts the linphone stripped and unstripped `*.so` files to the configured `firebaseCrashlytics.strippedNativeLibsDir` and `firebaseCrashlytics.unstrippedNativeLibsDir` folders.


Configuration
-------------
Configure the project for your application:
* In `app/build.gradle`, specify the correct `applicationId`
* Place your debug and release `google-services.json` files in `app/src/debug` and `app/src/release`, respectively
    - Make sure the `applicationId`(s)  in the `app/build.gradle` file match those in the `google-services.json` files.
* Copy `signing.properties.template` to `signing.properties` and provide the required values for your signing certificate

Building
--------
Build a release app and upload debugging symbols with `./gradlew clean assembleRelease uploadCrashlyticsSymbolFileRelease`

Testing
-------
* Install the `app/build/outputs/apk/release/app-release.apk` to a device
* Launch the **CrashObfuscationTest** app
* Click on the button "FORCE CRASH"
* Relaunch the app
* Open your project in the Firebase Crashlytics console
* Verify that the crash appears:
    - Expected behavior: The crash appears with a deobfuscated stacktrace:
        ```
        Crashed: Thread: SIGSEGV  0x0000000000000000
               at (Missing)()
               at linphone_core_set_adaptive_rate_algorithm + 2020(linphonecore.c:2020)
               at Java_org_linphone_core_CoreImpl_setAdaptiveRateAlgorithm + 15215(linphone_jni.cc:15215)
        ```
    - Actual behavior: The crash appears with an empty stacktrace:
        ```
        Crashed: Thread: SIGSEGV  0x0000000000000000
               at (Missing)()
               at (Missing)()
               at (Missing)()
        ```


Workaround
----------
In the root `build.gradle`, specify `3.6.3` for the android gradle plugin and repeat the Building and Testing steps.  The expected deobfuscated stacktrace appears.
