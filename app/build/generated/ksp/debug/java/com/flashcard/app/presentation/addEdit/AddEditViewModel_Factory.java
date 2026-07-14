package com.flashcard.app.presentation.addEdit;

import androidx.lifecycle.SavedStateHandle;
import com.flashcard.app.domain.repository.FlashcardRepository;
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
public final class AddEditViewModel_Factory implements Factory<AddEditViewModel> {
  private final Provider<FlashcardRepository> repositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public AddEditViewModel_Factory(Provider<FlashcardRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public AddEditViewModel get() {
    return newInstance(repositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static AddEditViewModel_Factory create(Provider<FlashcardRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new AddEditViewModel_Factory(repositoryProvider, savedStateHandleProvider);
  }

  public static AddEditViewModel newInstance(FlashcardRepository repository,
      SavedStateHandle savedStateHandle) {
    return new AddEditViewModel(repository, savedStateHandle);
  }
}
