import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderBuilder
import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.arkade.htmlcanvas.component.base.ArkadeAbstractComponent
import pl.karol202.uranium.arkade.htmlcanvas.startOnCanvas
import pl.karol202.uranium.arkade.htmlcanvas.values.Vector
import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import screens.game.gameScreen
import screens.menu.menuScreen

fun main() = startOnCanvas("canvas", renderInterval = 20, physicsInterval = 20) { size -> app(size = size) }

class App(props: Props) : ArkadeAbstractComponent<App.Props>(props),
                          UStateful<App.State>
{
    data class Props(override val key: Any,
                     val size: Vector) : UProps

    data class State(val screen: Screen = Screen.MENU) : UState
    {
        enum class Screen
        {
            MENU, GAME
        }
    }

    override var state by state(State())

    override fun ArkadeRenderBuilder.render()
    {
        + when(state.screen)
        {
            State.Screen.MENU -> menuScreen(size = props.size,
                                            onGameStart = ::startGame)
            State.Screen.GAME -> gameScreen(size = props.size,
                                            onQuit = ::quitGame)
        }
    }

    private fun startGame() = setState { copy(screen = State.Screen.GAME) }

    private fun quitGame() = setState { copy(screen = State.Screen.MENU) }
}

fun ArkadeRenderScope.app(key: Any = AutoKey,
                          size: Vector) = component(::App, App.Props(key, size))
