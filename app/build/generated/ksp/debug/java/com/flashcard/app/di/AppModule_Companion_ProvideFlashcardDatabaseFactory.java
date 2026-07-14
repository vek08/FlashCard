package com.flashcard.app.di;

import android.content.Context;
import com.flashcard.app.data.local.database.FlashcardDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class AppModule_Companion_ProvideFlashcardDatabaseFactory implements Factory<FlashcardDatabase> {
  private final Provider<Context> contextProvider;

  public AppModule_Companion_ProvideFlashcardDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public FlashcardDatabase get() {
    return provideFlashcardDatabase(contextProvider.get());
  }

  public static AppModule_Companion_ProvideFlashcardDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new AppModule_Companion_ProvideFlashcardDatabaseFactory(contextProvider);
  }

  public static FlashcardDatabase provideFlashcardDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.Companion.provideFlashcardDatabase(context));
  }
}
