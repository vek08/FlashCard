package com.flashcard.app.data.repository;

import com.flashcard.app.data.local.dao.FlashcardDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class FlashcardRepositoryImpl_Factory implements Factory<FlashcardRepositoryImpl> {
  private final Provider<FlashcardDao> daoProvider;

  public FlashcardRepositoryImpl_Factory(Provider<FlashcardDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public FlashcardRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static FlashcardRepositoryImpl_Factory create(Provider<FlashcardDao> daoProvider) {
    return new FlashcardRepositoryImpl_Factory(daoProvider);
  }

  public static FlashcardRepositoryImpl newInstance(FlashcardDao dao) {
    return new FlashcardRepositoryImpl(dao);
  }
}
