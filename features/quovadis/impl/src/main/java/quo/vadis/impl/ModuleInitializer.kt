package quo.vadis.impl

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import quo.vadis.api.Constants
import quo.vadis.api.usecases.AssembleImageUrlUseCase
import quo.vadis.api.usecases.GetCatNameUseCase
import quo.vadis.impl.domain.AssembleImageUrlUseCaseImpl
import quo.vadis.impl.domain.GetCatNameUseCaseImpl
import quo.vadis.impl.ui.CatFragment

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                factory<GetCatNameUseCase> {
                    GetCatNameUseCaseImpl()
                }
                factory<AssembleImageUrlUseCase> {
                    AssembleImageUrlUseCaseImpl()
                }

                intoSetFactory(Constants.QUOVADIS_SCREEN) {
                    ScreenFragmentRouter(R.string.quovadis_title, CatFragment::class)
                }
            }
        )
    }

}