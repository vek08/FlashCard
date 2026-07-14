package com.flashcard.app.di;

import com.flashcard.app.data.local.dao.FlashcardDao;
import com.flashcard.app.data.local.database.FlashcardDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class AppModule_Companion_ProvideFlashcardDaoFactory implements Factory<FlashcardDao> {
  private final Provider<FlashcardDatabase> databaseProvider;

  public AppModule_Companion_ProvideFlashcardDaoFactory(
      Provider<FlashcardDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public FlashcardDao get() {
    return provideFlashcardDao(databaseProvider.get());
  }

  public static AppModule_Companion_ProvideFlashcardDaoFactory create(
      Provider<FlashcardDatabase> databaseProvider) {
    return new AppModule_Companion_ProvideFlashcardDaoFactory(databaseProvider);
  }

  public static FlashcardDao provideFlashcardDao(FlashcardDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.Companion.provideFlashcardDao(database));
  }
}
