```
buildscript {
    repositories {
        maven { url 'https://maven.fabric.io/public' }
    }

    dependencies {
        classpath 'io.fabric.tools:gradle:1.+'
    }
}
apply plugin: 'com.android.application'
apply plugin: 'io.fabric'

repositories {
    maven { url 'https://maven.fabric.io/public' }
}

apply plugin: 'realm-android'
apply plugin: 'me.tatarka.retrolambda'
apply plugin: 'android-apt'

android {
    compileSdkVersion 24
    buildToolsVersion "23.0.3"

    defaultConfig {
        applicationId "co.companyname.theapp"
        minSdkVersion 15
        targetSdkVersion 24
        versionCode 1
        versionName "0.0.1"
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled true
    }

    /**
     * This is for jenkins only. Always comment out if not Jenkins
     * */
    lintOptions {
        abortOnError false
    }

    dataBinding {
        enabled = true
    }

    dexOptions {
        javaMaxHeapSize "4g"
        preDexLibraries = false
    }

    signingConfigs {
        debug {
            keyAlias keystoreProperties['keyAlias']
            keyPassword keystoreProperties['keyPassword']
            storeFile file(keystoreProperties['storeFile'])
            storePassword keystoreProperties['storePassword']
        }

        release {
            keyAlias keystoreProperties['keyAlias']
            keyPassword keystoreProperties['keyPassword']
            storeFile file(keystoreProperties['storeFile'])
            storePassword keystoreProperties['storePassword']
        }
    }

    buildTypes {
        release {
            resValue 'string', 'APP_NAME', '"APP"'
            multiDexEnabled true
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'

            signingConfig signingConfigs.release
            ext.betaDistributionReleaseNotes="Release Notes for this build."
            ext.betaDistributionGroupAliases="Production"
            ext.betaDistributionEmails="soso@soso.co"
            ext.betaDistributionNotifications=true
        }

        debug {
            applicationIdSuffix ".dev"
            versionNameSuffix "-dev"
            resValue 'string', 'APP_NAME', '"APP Dev"'
            multiDexEnabled true
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'

            signingConfig signingConfigs.debug
            ext.betaDistributionReleaseNotes="Bla bla bla"
            ext.betaDistributionGroupAliases="Bla"
            ext.betaDistributionNotifications=true
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude 'META-INF/services/javax.annotation.processing.Processor'
        exclude 'LICENSE'
        exclude 'LICENSE.txt'
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    /*Basic Needs The support libraries*/
    compile 'com.android.support:appcompat-v7:24.2.1'
    compile 'com.android.support:design:24.2.1'
    compile 'com.android.support:support-v4:24.2.1'
    compile 'com.android.support:cardview-v7:24.2.1'

    /**
     *Reactive Programming for both UI and making request using Reactive Programming
     * */
    compile 'io.reactivex:rxandroid:1.0.1'
    compile 'io.reactivex:rxjava:1.0.14'

    /**
     * Java 8 Stream support for pre Java8
     * */
    compile 'com.annimon:stream:1.0.9'

    /**
     *Retrofit and OKHTTP For making Restful API request
     * */
    compile 'com.squareup.retrofit2:retrofit:2.1.0'
    compile 'com.squareup.retrofit2:adapter-rxjava:2.1.0'
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'
    compile 'com.squareup.okhttp3:logging-interceptor:3.4.1'
    compile 'com.squareup.okhttp3:okhttp:3.4.1'

    /**
     * Stetho to debug app using Chrome Inspect
     * */
    compile 'com.facebook.stetho:stetho:1.3.1'
    compile 'com.facebook.stetho:stetho-okhttp3:1.3.1'

    /**
     * Afollestad Modified Material Dialog
     * */
    compile 'com.afollestad.material-dialogs:core:0.8.6.2'

    /**
     * Apache common is a collection of reusable components.
     * */
    compile ('org.apache.commons:commons-collections4:4.0') {
        transitive = false
    }
    compile ('org.apache.commons:commons-lang3:3.4') {
        transitive = false
    }
    compile ('commons-io:commons-io:2.4') {
        transitive = false
    }

    /**
     * Joda Time is for managing Time and Date and recent function present in Java 8 but
     * */
    compile 'net.danlew:android.joda:2.9.3.1'

    /**
     * Dependency Injection
    * */
    compile 'com.google.dagger:dagger:2.7'
    compile 'com.google.dagger:dagger-compiler:2.7'

    /**
     * Views invjection
     * */
    compile 'com.jakewharton:butterknife:8.4.0'
    apt 'com.jakewharton:butterknife-compiler:8.4.0'

    /**
     * Image Loading
     * */
    compile 'com.github.bumptech.glide:glide:3.7.0'

    /**
     * Permission helper
     * */
    compile 'com.github.k0shk0sh:PermissionHelper:1.0.9'

    compile 'net.cachapa.expandablelayout:expandablelayout:2.3'

    /**
     * Image Picker
     * */
    compile 'com.github.nguyenhoanglam:ImagePicker:1.1.2'

    /**
     * Pusher
     * */
    compile 'com.pusher:pusher-java-client:1.2.1'
    compile('com.crashlytics.sdk.android:crashlytics:2.6.5@aar') {
        transitive = true;
    }
}

retrolambda {
    javaVersion JavaVersion.VERSION_1_7
    defaultMethods false
}
```