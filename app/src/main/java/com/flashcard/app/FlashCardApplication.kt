package com.flashcard.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for the FlashCard app.
 *
 * Annotated with [HiltAndroidApp] to trigger Hilt's code generation
 * and serve as the application-level dependency container.
 * This must be registered in the AndroidManifest.xml as the
 * `android:name` attribute of the `<application>` tag.
 */
@HiltAndroidApp
class FlashCardApplication : Application()
