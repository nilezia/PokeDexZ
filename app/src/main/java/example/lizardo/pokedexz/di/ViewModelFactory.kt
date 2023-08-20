package example.lizardo.pokedexz.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val provides: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var provide: Provider<out ViewModel>? = provides[modelClass]
        if (provide == null) {
            for ((key, value) in provides) {
                if (modelClass.isAssignableFrom((key))) {
                    provide = value
                    break
                }
            }
        }
        requireNotNull(provide) { "unknown viewModel $modelClass" }
        return try {
            provide.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

}