import kotlinx.html.dom.append
import kotlinx.html.js.canvas
import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.draw.size
import pl.karol202.uranium.webcanvas.draw.startOnCanvas
import pl.karol202.uranium.webcanvas.values.Vector
import screens.game.gameScreen
import screens.menu.menuScreen
import kotlin.browser.document

private val canvas = document.body!!.append.canvas { }

fun main() = startOnCanvas(canvas, renderInterval = 20, physicsInterval = 20) { app(size = canvas.size) }

class App(props: Props) : WCAbstractComponent<App.Props>(props),
                          UStateful<App.State>
{
    data class Props(override val key: Any,
                     val size: Vector) : UProps

    data class State(val screen: Screen = Screen.GAME) : UState
    {
        enum class Screen
        {
            MENU, GAME
        }
    }

    override var state by state(State())

    override fun WCRenderBuilder.render()
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

fun WCRenderScope.app(key: Any = AutoKey,
                      size: Vector) = component(::App, App.Props(key, size))
