package quo.vadis.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import quo.vadis.api.Constants
import quo.vadis.api.usecases.AssembleImageUrlUseCase
import quo.vadis.api.usecases.GetCatNameUseCase
import quo.vadis.impl.data.CatRepositoryImpl
import quo.vadis.impl.domain.AssembleImageUrlUseCaseImpl
import quo.vadis.impl.domain.GetCatNameUseCaseImpl
import quo.vadis.impl.ui.CatFragment

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {

                single { CatRepositoryImpl() }

                factory<GetCatNameUseCase> { GetCatNameUseCaseImpl(get()) }
                factory<AssembleImageUrlUseCase> { AssembleImageUrlUseCaseImpl() }

                factory<Class<out Fragment>>(named(Constants.QUOVADIS_SCREEN)) { CatFragment::class.java }
            }
        )
    }

}